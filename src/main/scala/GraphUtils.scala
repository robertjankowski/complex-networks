import java.io.{File => JFile, FileWriter => JFileWriter}

import scala.util.Try

object GraphUtils {

  def saveDegrees[G <: Graph[_]](g: G, filename: String): Try[Unit] =
    Try(new JFileWriter(new JFile(filename))).map { writer =>
      val degrees = g.degree().mkString("\n")
      writer.write(degrees)
    }.recover {
      case ex: Exception =>
        println(s"Error in saving graph's degrees to file [$filename]\n${ex.getMessage}")
    }
}
