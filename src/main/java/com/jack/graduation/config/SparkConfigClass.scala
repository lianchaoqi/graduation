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
//    .setMaster("spark://hadoop101:7077")
//    .setJars(List("spark-core/target/spark-core-1.0-SNAPSHOT.jar"))
//    .set("spark.driver.host", "myword")
  System.getProperty("HADOOP_USER_NAME", "jack")
  System.getProperty("user.name", "jack")

  @Bean
  def getSparkConfig: SparkConf = {
    System.getProperty("HADOOP_USER_NAME", "jack")
    System.getProperty("user.name", "jack")
    val sparkConfig: SparkConf = new SparkConf()
      .setMaster(master)
      .setAppName(appName)
      .setJars(List("target/graduation-0.0.1-SNAPSHOT.jar"))
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
