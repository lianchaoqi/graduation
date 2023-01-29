package com.jack.graduation.service.impl

import java.text.SimpleDateFormat
import java.util.Date

import com.jack.graduation.bean.FileInfo
import com.jack.graduation.common.{Constants, Result, SparkConstans}
import com.jack.graduation.exception.ServiceException
import com.jack.graduation.model.DwsAlbum
import com.jack.graduation.service.SparkFileService
import org.apache.spark.SparkContext
import org.apache.spark.sql.{DataFrame, Dataset, Row, SaveMode, SparkSession}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SparkFileServiceImpl @Autowired()(sparkSession: SparkSession, sc: SparkContext) extends SparkFileService {
  //转换规则

  import sparkSession.implicits._

  //TODO:数据清洗
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
             |where (artist_id != '' and artist_id is not null)
             |  and (genre != '' and genre is not null)
             |  and (id != '' and id is not null)
             |  and (num_of_sales != '' and num_of_sales is not null)
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

  //TODO:数据删除
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

  //dws宽表
  override def albumDwdToDws(dt: String, filename: String): Boolean = {
    var dwdFrame: DataFrame = null
    try {
      dwdFrame = sparkSession.sql(
        s"""
           |insert overwrite table `graduation`.`dws_album_sale_detail` partition (dt = '${dt}', filename = '${filename}')
           |select artist_id,
           |       real_name,
           |       art_name,
           |       role,
           |       year_of_birth,
           |       email,
           |       country,
           |       city,
           |       zip_code,
           |       t1.id,
           |       album_title,
           |       t1.genre,
           |       operation_id,
           |       t2.record_time,
           |       t1.year_of_pub,
           |       num_of_tracks,
           |       num_of_sales,
           |       rolling_stone_critic,
           |       mtv_critic,
           |       music_maniac_critic
           |from (select id,
           |             artist_id,
           |             album_title,
           |             genre,
           |             year_of_pub,
           |             num_of_tracks,
           |             num_of_sales,
           |             rolling_stone_critic,
           |             mtv_critic,
           |             music_maniac_critic,
           |             record_time,
           |             dt,
           |             filename
           |      from (
           |               --去重
           |               select id,
           |                      artist_id,
           |                      album_title,
           |                      genre,
           |                      year_of_pub,
           |                      num_of_tracks,
           |                      num_of_sales,
           |                      rolling_stone_critic,
           |                      mtv_critic,
           |                      music_maniac_critic,
           |                      record_time,
           |                      dt,
           |                      filename,
           |                      row_number() over (partition by id order by record_time desc ) rn
           |               from `graduation`.`dwd_albums`
           |               where dt = '${dt}'
           |                 and filename = '${filename}') temp
           |      where temp.rn = 1
           |     ) t1
           |         left join (
           |    --关联流派信息
           |    select id,
           |           genre,
           |           operation_id,
           |           record_time,
           |           dt
           |    from `graduation`.`dim_genre`
           |    where dt = '2023-01-12') t2
           |                   on t1.genre = t2.genre
           |         left join (
           |    --关联艺术家信息
           |    select id,
           |           real_name,
           |           art_name,
           |           role,
           |           year_of_birth,
           |           email,
           |           country,
           |           city,
           |           zip_code,
           |           dt
           |    from `graduation`.`dim_artist`
           |    where dt = '2023-01-12') t3
           |                   on t1.artist_id = t3.id
           |""".stripMargin)
    } catch {
      case e: Any => throw new ServiceException(Constants.CODE_500, e.getMessage)
    }
    dwdFrame.isEmpty
  }

  //dws_alnum
  override def dwsAlbum(dt: String, filename: String, createTime: String): Result = {
    //写入数据
    try {
      val resDS: Dataset[DwsAlbum] =
        sparkSession.sql(
          s"""
             |select row_number() over (order by album_id) as rn,
             |       artist_id,
             |       real_name,
             |       art_name,
             |       role,
             |       year_of_birth,
             |       email,
             |       country,
             |       city,
             |       zip_code,
             |       album_id,
             |       album_title,
             |       genre,
             |       operation_id,
             |       operation_time,
             |       album_year_of_pub,
             |       num_of_tracks,
             |       num_of_sales,
             |       rolling_stone_critic,
             |       mtv_critic,
             |       music_maniac_critic,
             |       dt,
             |       filename
             |from `graduation`.`dws_album_sale_detail`
             |where dt = '${dt}'
             |  and filename = '${filename}'
             |""".stripMargin)
          .map((row: Row) => {
            DwsAlbum(
              row.getInt(0),
              row.getInt(1),
              row.getString(2),
              row.getString(3),
              row.getString(4),
              row.getString(5),
              row.getString(6),
              row.getString(7),
              row.getString(8),
              row.getString(9),
              row.getString(10),
              row.getString(11),
              row.getString(12),
              row.getString(13),
              row.getString(14),
              row.getString(15),
              row.getInt(16),
              row.getInt(17),
              row.getDouble(18),
              row.getDouble(19),
              row.getDouble(20),
              "2023-01-14",
              row.getString(21),
              row.getString(22))
          })
      resDS.write
        .format("jdbc")
        .option("url", SparkConstans.JDBC_URL)
        .option("user", SparkConstans.JDBC_USER)
        .option("password", SparkConstans.JDBC_PASSWORD)
        .option("dbtable", "album_detail")
        .mode(SaveMode.Overwrite)
        .save()
    } catch {
      //跑出错误信息
      case e: Any => return Result.error(Constants.CODE_500, e.getMessage)
    }
    Result.success("true")
  }

  //adsAlbumYear 流派维度聚合表
  override def adsAlbumGnereYear(dt: String, filename: String): Result = {
    try {
      val resDF: DataFrame = sparkSession.sql(
        s"""
           |select row_number() over (order by t.num_of_sales) as id,
           |       genre,
           |       album_year_of_pub,
           |       num_of_tracks,
           |       num_of_sales,
           |       dt,
           |       filename
           |from (select genre,
           |             album_year_of_pub,
           |             sum(num_of_tracks) as num_of_tracks,
           |             sum(num_of_sales)  as num_of_sales,
           |             dt,
           |             filename
           |     from `graduation`.`dws_album_sale_detail`
           |      where dt = '${dt}'
           |        and filename = '${filename}'
           |      group by genre, album_year_of_pub, dt, filename) t
           |""".stripMargin)
      //创建临时表
      resDF.createOrReplaceTempView("temp_ads_album_gnere_year_nd")
      sparkSession.sql("insert overwrite table `graduation`.`ads_album_gnere_year_nd`  select * from temp_ads_album_gnere_year_nd")
      resDF.write
        .format("jdbc")
        .option("url", SparkConstans.JDBC_URL)
        .option("user", SparkConstans.JDBC_USER)
        .option("password", SparkConstans.JDBC_PASSWORD)
        .option("dbtable", "ads_album_gnere_year_nd")
        .mode(SaveMode.Overwrite)
        .save()
    } catch {
      case e: Any => return Result.error(Constants.CODE_500, e.getMessage)
    }
    Result.success("true")
  }

  //流派城市年份维度
  override def adsAlbumCountryGnereYear(dt: String, filename: String): Result = {
    try {
      val resDF: DataFrame = sparkSession.sql(
        s"""
           |select row_number() over (order by t.num_of_sales) as id,
           |       country,
           |       genre,
           |       album_year_of_pub,
           |       num_of_tracks,
           |       num_of_sales,
           |       dt,
           |       filename
           |from (select country,
           |             album_year_of_pub,
           |             genre,
           |             sum(num_of_tracks) as num_of_tracks,
           |             sum(num_of_sales)  as num_of_sales,
           |             dt,
           |             filename
           |      from `graduation`.`dws_album_sale_detail`
           |      where dt = '${dt}'
           |        and filename = '${filename}'
           |      group by country,genre,album_year_of_pub,dt,filename)t
           |""".stripMargin)
      //创建临时表
      resDF.createOrReplaceTempView("temp_ads_album_country_gnere_year_nd")
      sparkSession.sql("insert overwrite table `graduation`.`ads_album_country_gnere_year_nd` select * from temp_ads_album_country_gnere_year_nd")
      resDF.write
        .format("jdbc")
        .option("url", SparkConstans.JDBC_URL)
        .option("user", SparkConstans.JDBC_USER)
        .option("password", SparkConstans.JDBC_PASSWORD)
        .option("dbtable", "ads_album_country_gnere_year_nd")
        .mode(SaveMode.Overwrite)
        .save()

    } catch {
      case e: Any => return Result.error(Constants.CODE_500, e.getMessage)
    }
    Result.success("true")
  }

  override def delRes(filename: String): Result = {
    try {
      //删除数据
      sparkSession.sql(
        s"""
           |insert overwrite table `graduation`.`ads_album_gnere_year_nd`
           |select id,
           |       genre,
           |       album_year_of_pub,
           |       num_of_tracks,
           |       num_of_sales,
           |       dt,
           |       filename
           |from `graduation`.`ads_album_gnere_year_nd`
           |where filename != '${filename}'
           |""".stripMargin)
    } catch {
      case e: Any => return Result.error(Constants.CODE_500, e.getMessage)
    }
    Result.success("true")
  }
}
