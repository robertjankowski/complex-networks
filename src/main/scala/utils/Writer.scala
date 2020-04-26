package utils

import scala.util.Try
import java.io.{File => JFile, FileWriter => JFileWriter}

object Writer {

  def writeToFile(content: String, filename: String, errorMessage: String): Try[Unit] =
    Try(new JFileWriter(new JFile(filename))).map { writer =>
      writer.write(content)
      writer.close()
    }.recover {
      case ex: Exception => println(s"$errorMessage\n${ex.getMessage}")
    }


  def customiseFilename(content: String, filename: String): String = {
    val filenameWithExtension = filename.split('.')
    filenameWithExtension(0) + content + '.' + filenameWithExtension(1)
  }
}
