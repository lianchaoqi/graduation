package com.jack.graduation.common

import java.io.InputStreamReader
import java.util.Properties


object SparkConstans {
  val prop = new Properties();
  prop.load(new InputStreamReader(Thread.currentThread().getContextClassLoader.getResourceAsStream("application.properties"), "UTF-8"))
  val JDBC_URL: String = prop.getProperty("jdbc.url")
  val JDBC_USER: String = prop.getProperty("jdbc.user")
  val JDBC_PASSWORD: String = prop.getProperty("jdbc.password")
  val JDBC_DRIVER: String = prop.getProperty("jdbc.driver")
}

