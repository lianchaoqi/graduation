package com.jack.graduation.service

import com.jack.graduation.bean.FileInfo


trait SparkFileService {
  //文件清洗方法
  def fileEtl(filePath: String, etlFilePath: String, fileinfo: FileInfo, etlTime: String): Boolean

  //删除文件
  def deletePartition(fileinfo: FileInfo):Boolean
}
