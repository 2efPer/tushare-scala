package tushare

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.log4j.Logger
import org.apache.log4j.Level

object implicits {

  implicit val configuration:ConfContext = new ConfContext()

  private val sparkConf = new SparkConf()
  sparkConf.setMaster("local[*]")
  sparkConf.setAppName("Tushare-Spark")
  sparkConf
    .set("spark.default.parallelism", "1")
    .set("spark.sql.shuffle.partitions", "1")
    .set("spark.sql.codegen.wholeStage", "false")
  implicit val spark: SparkSession = SparkSession.builder.config(sparkConf).getOrCreate()


  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)

  implicit class EnhanceDataFrame(df:DataFrame){
    def writeToDatabase(tableName:String = EMPTEY_STRING):Unit={
      df.write.
      format("jdbc").
        option("url", "jdbc:postgresql://0.0.0.0:5432/postgres").
        option("dbtable",
          if (tableName.equals(EMPTEY_STRING)) {s"""${df.columns.mkString("_")}"""} else tableName).
        option("user", "postgres").
        option("password", "postgres").
        save()
    }
  }


}
