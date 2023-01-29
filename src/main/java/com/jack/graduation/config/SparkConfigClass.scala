package com.jack.graduation.config

import java.io.InputStreamReader
import java.util.Properties

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.springframework.context.annotation.{Bean, Configuration}


@Configuration
class SparkConfigClass {

  private val properties: Properties = load("application.properties")
  private val appName: String = properties.getProperty("spark.appName")
  private val master: String = properties.getProperty("spark.master")
  private val hostName: String = properties.getProperty("spark.hostName")
  private val hostNameCongig: String = properties.getProperty("spark.hostNameCongig")
  private val hiveWarehouseDir: String = properties.getProperty("hive.warehouse.dir")
  private val hiveWarehouseDirPath: String = properties.getProperty("hive.warehouse.dir.path")
  private val hiveMetastoreUris: String = properties.getProperty("hive.metastore.uris")
  private val hiveMetastoreUrisPath: String = properties.getProperty("hive.metastore.uris.path")



  System.getProperty("HADOOP_USER_NAME", "jack")
  System.getProperty("user.name", "jack")

  @Bean
  def getSparkConfig: SparkConf = {
    System.getProperty("HADOOP_USER_NAME", "jack")
    System.getProperty("user.name", "jack")
    val sparkConfig: SparkConf = new SparkConf()
      .setMaster(master)
      .setAppName(appName)
      .set(hostNameCongig, hostName)
      .set(hiveWarehouseDir, hiveWarehouseDirPath)
    sparkConfig
  }

  @Bean
  def getSparkSession: SparkSession = {
    //设置操作hdfs的用户名
    System.getProperty("HADOOP_USER_NAME", "jack")
    System.getProperty("user.name", "jack")
    val sparkSession: SparkSession = SparkSession
      .builder()
      .config(getSparkConfig)
      .enableHiveSupport()
      .config(hiveMetastoreUris, hiveMetastoreUrisPath)
      .getOrCreate()

    sparkSession
  }

  @Bean
  def getSparkContext: SparkContext = {
    System.getProperty("HADOOP_USER_NAME", "jack")
    //返回sparkContext对象
    getSparkSession.sparkContext
  }

  def load(propertieName: String): Properties = {
    val prop = new Properties();
    prop.load(new InputStreamReader(Thread.currentThread().getContextClassLoader.getResourceAsStream(propertieName), "UTF-8"))
    prop
  }
}
