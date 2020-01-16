package tushare

import java.io.File
import java.nio.file.{Files, Paths}

import scala.io.{BufferedSource, Source}

class ConfContext {



  private val tokenFilePath:String = System.getProperty("user.home") + File.separator +".token.tushare"

  def storedToken:String = if(Files.isRegularFile(Paths.get(tokenFilePath))) {
    val src:BufferedSource = Source.fromFile(tokenFilePath)
    val token:String = src.getLines().next()
    src.close()
    token
  } else EMPTEY_STRING


}
