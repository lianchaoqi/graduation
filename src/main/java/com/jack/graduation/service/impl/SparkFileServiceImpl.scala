package com.jack.graduation.service.impl

import java.util.Date

import com.jack.graduation.bean.FileInfo
import com.jack.graduation.service.SparkFileService
import org.apache.spark.SparkContext
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat

import com.jack.graduation.common.Constants
import com.jack.graduation.exception.ServiceException

@Service
class SparkFileServiceImpl @Autowired()(sparkSession: SparkSession, sc: SparkContext) extends SparkFileService {
  override def fileEtl(filePath: String, etlFilePath: String, fileinfo: FileInfo, etlTime: String): Boolean = {
    System.getProperty("HADOOP_USER_NAME", "jack")
    System.getProperty("user.name", "jack")
    val loadFrame: DataFrame = sparkSession.sql(
      s"""
         |load data inpath '$filePath${fileinfo.getUuid}.csv' overwrite into table
         |`graduation`.`ods_albums`
         |partition(dt='${etlTime.substring(0, 10)}',filename='${fileinfo.getUuid}')
         |""".stripMargin)
    if (loadFrame.isEmpty) {
      try {
        val odsToDwdFrame: DataFrame = sparkSession.sql(
          s"""
             |insert overwrite table `graduation`.`dwd_albums` partition (dt = '${etlTime.substring(0, 10)}', filename = '${fileinfo.getUuid}')
             |select id,
             |       artist_id,
             |       album_title,
             |       genre,
             |       year_of_pub,
             |       num_of_tracks,
             |       num_of_sales,
             |       rolling_stone_critic,
             |       mtv_critic,
             |       music_maniac_critic,
             |       '$etlTime'
             |from `graduation`.`ods_albums`
             |where (artist_id != '' or artist_id is not null)
             |  and (genre != '' or genre is not null)
             |  and (id != '' or id is not null)
             |  and (num_of_sales != '' or num_of_sales is not null)
             |  and  dt='${etlTime.substring(0, 10)}'
             |  and filename='${fileinfo.getUuid}'
             |""".stripMargin)
        odsToDwdFrame.isEmpty
      } catch {
        case e: Any => throw new ServiceException(Constants.CODE_500, e.getMessage)
      }
    } else {
      throw new ServiceException(Constants.CODE_500, "load 数据错误！")
    }
  }

  override def deletePartition(fileinfo: FileInfo): Boolean = {
    val filename: String = fileinfo.getUuid
    val time: Date = fileinfo.getEtlTime
    val dateFormat = new SimpleDateFormat("yyyy-MM-dd")
    val etlTime: String = dateFormat.format(time).substring(0, 10)
    //删除hvie分区数据（hdfs数据）
    var frame: DataFrame = null
    try {
      frame = sparkSession.sql(
        s"""
           |alter table `graduation`.`dwd_albums`
           |drop partition (dt='$etlTime',filename='$filename')
           |""".stripMargin)
    } catch {
      case e: Any => throw new ServiceException(Constants.CODE_500, e.getMessage)
    }
    frame.isEmpty
  }
}
