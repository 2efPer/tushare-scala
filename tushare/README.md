# Tushare Scala Client

## Usage

1. Get your [tushare-API-token](https://tushare.pro/user/token) and paste it to:  
```~/.token.tushare```  
    **This file MUST ONLY contain your token!**

2. Start a scala REPL.In this project we use mill.  
```$> mill -i tushare.console```   
  If you don't have mill installed, you can run :  
```$> ./mill -i tushare.console```  
`./mill` will install automatically for you.  

3. Example Code  
```scala
import tushare._
import tushare.implicits._
val api = new DataApi()
//-----------fetch data as pure json string-----------
val data1 = api.queryToText(apiName = "stock_basic")
//-----------or you can fetch data as a Spark DataFrame-----------
val data = api.queryToDF(apiName = "stock_basic")
data.show()
```

```
+---------+------+--------+----+--------+------+---------+
|  ts_code|symbol|    name|area|industry|market|list_date|
+---------+------+--------+----+--------+------+---------+
|000001.SZ|000001|平安银行|深圳|    银行|  主板| 19910403|
|000002.SZ|000002|   万科A|深圳|全国地产|  主板| 19910129|
|000004.SZ|000004|国农科技|深圳|生物制药|  主板| 19910114|
|000005.SZ|000005|世纪星源|深圳|环境保护|  主板| 19901210|
|000006.SZ|000006| 深振业A|深圳|区域地产|  主板| 19920427|
|000007.SZ|000007|  全新好|深圳|酒店餐饮|  主板| 19920413|
|000008.SZ|000008|神州高铁|北京|运输设备|  主板| 19920507|
|000009.SZ|000009|中国宝安|深圳|  综合类|  主板| 19910625|
|000010.SZ|000010| *ST美丽|深圳|建筑工程|  主板| 19951027|
|000011.SZ|000011| 深物业A|深圳|区域地产|  主板| 19920330|
|000012.SZ|000012|   南玻A|深圳|    玻璃|  主板| 19920228|
|000014.SZ|000014|沙河股份|深圳|全国地产|  主板| 19920602|
|000016.SZ|000016| 深康佳A|深圳|家用电器|  主板| 19920327|
|000017.SZ|000017| 深中华A|深圳|文教休闲|  主板| 19920331|
|000019.SZ|000019|深粮控股|深圳|其他商业|  主板| 19921012|
|000020.SZ|000020| 深华发A|深圳|  元器件|  主板| 19920428|
|000021.SZ|000021|  深科技|深圳|  IT设备|  主板| 19940202|
|000023.SZ|000023| 深天地A|深圳|其他建材|  主板| 19930429|
|000025.SZ|000025|   特力A|深圳|汽车服务|  主板| 19930621|
|000026.SZ|000026|  飞亚达|深圳|其他商业|  主板| 19930603|
+---------+------+--------+----+--------+------+---------+
```


```scala
//-----------fetch data with params-----------
val compInfo = api.queryToDF("stock_company",queryParams = Map("exchange"->"SZSE"))
compInfo.show(2)
```

```
+---------+--------+--------+-------+---------+-----------+----------+--------+------+--------------------+--------------------+---------+
|  ts_code|exchange|chairman|manager|secretary|reg_capital|setup_date|province|  city|             website|               email|employees|
+---------+--------+--------+-------+---------+-----------+----------+--------+------+--------------------+--------------------+---------+
|300327.SZ|    SZSE|  傅启明| 宋永皓|   潘一德| 25405.8596|  19940713|    上海|上海市|  www.sinowealth.com|ir@sinowealth.com...|      334|
|300691.SZ|    SZSE|  龚俊强| 李成斌|   瞿宗金| 22506.4704|  20050818|    广东|中山市|www.union-optech.com|service@union-opt...|     1177|
+---------+--------+--------+-------+---------+-----------+----------+--------+------+--------------------+--------------------+---------+
```