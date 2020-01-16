import mill._, scalalib._

object tushare extends ScalaModule {
    def scalaVersion = "2.12.10"
    def ivyDeps = Agg(
       ivy"com.lihaoyi::requests:0.5.0",
       ivy"com.lihaoyi::upickle:0.9.5",
      ivy"org.apache.spark::spark-core:2.4.4",
      ivy"org.apache.spark::spark-sql:2.4.4"
    )

}
