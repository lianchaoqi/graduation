package com.jack.graduation.service

import com.jack.graduation.bean.FileInfo
import com.jack.graduation.common.Result
import org.springframework.stereotype.Service


trait SparkFileService {
  //文件清洗方法
  def fileEtl(filePath: String, etlFilePath: String, fileinfo: FileInfo, etlTime: String): Boolean

  //删除文件
  def deletePartition(fileinfo: FileInfo): Boolean

  //宽表聚合
  def albumDwdToDws(dt: String, filename: String): Boolean

  //数据分析
  def dwsAlbum(dt: String, filename: String, createTime: String): Result

  //流派，年份维度汇总表
  def adsAlbumGnereYear(dt: String, filename: String): Result

  //国家城市年份维度汇总表
  def adsAlbumCountryGnereYear(dt: String, filename: String): Result

  //结果集删除
  def delRes(filename:String):Result

}
