package graph

import java.io.{File => JFile, FileWriter => JFileWriter}

import scala.util.Try

object GraphUtils {

  def saveDegrees[G <: Graph[_]](g: G, filename: String): Try[Unit] =
    Try(new JFileWriter(new JFile(filename))).map { writer =>
      writer.write(g.degree().mkString("\n"))
      writer.close()
    }.recover {
      case ex: Exception =>
        println(s"Error in saving graph's degrees to file [$filename]\n${ex.getMessage}")
    }
}

sealed class GraphException(val message: String) extends Exception(message)

case object GraphNotFoundNodeException extends GraphException("No found node in graph")