package tushare

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object implicits {

  implicit val configuration:ConfContext = new ConfContext()

  private val sparkConf = new SparkConf()
  sparkConf.setMaster("local[*]")
  sparkConf.setAppName("aloha")
  sparkConf
    .set("spark.default.parallelism", "1")
    .set("spark.sql.shuffle.partitions", "1")
    .set("spark.sql.codegen.wholeStage", "false")
  implicit val spark: SparkSession = SparkSession.builder.master("local[*]").appName("Tushare-App").config(sparkConf).getOrCreate()



}
