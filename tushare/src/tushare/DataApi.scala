package tushare

import org.apache.spark.sql.{DataFrame, SparkSession}
import upickle.default._

import scala.util.matching.Regex

class DataApi(token:String = "",timeout:Int = 10)(implicit val confContext:ConfContext,implicit val spark:SparkSession) {
  assert(!(EMPTEY_STRING.equals(token) && EMPTEY_STRING.equals(confContext.storedToken)),"Tushare Token must be specified!")
  import spark.implicits._
  import org.apache.spark.sql.functions._
  val pattern:Regex = """(\:|\,)(\ +)(\d+\.?\d*)""".r

  val  httpUrl:String = "http://api.tushare.pro"

  val tokenGotten:String = if(EMPTEY_STRING.equals(token)) confContext.storedToken else token


  def queryToText(apiName:String,queryfields:String="",queryParams:Map[String,String] = Map()):String={
    val reqParams = write(ujson.Obj("api_name"->apiName,
          "token"->tokenGotten,
      "fields"->queryfields,
      "params" ->queryParams
    ))
    val response = requests.post(httpUrl,headers = Map("Content-Type"->"application/json"),data=reqParams,connectTimeout=timeout*1000)
    val text = response.text()
    pattern.replaceAllIn(text,"$1$2\"$3\"")
  }

  def queryToDF(apiName:String,queryfields:String="",queryParams:Map[String,String] = Map()):DataFrame={
    val data = queryToText(apiName,queryfields,queryParams)
    val core = ujson.read(data)("data")
    val items = core("items")
    val fields = read[Seq[String]](core("fields"))
    read[Seq[Seq[String]]](items.toString).toDF("_tmp").select( col("_tmp") +: (0 until fields.size).map(i => col("_tmp")(i).alias(s"col$i")): _*).drop("_tmp").toDF(fields:_*)
  }


  override def toString: String = {
    write(Map(
      "http_url" -> httpUrl,
      "token" -> (tokenGotten.substring(0,4) + "*")
    ))
  }

}
