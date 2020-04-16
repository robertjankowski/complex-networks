package graph

import utils.Writer

import scala.util.Try

object GraphUtils {

  def saveDegrees[G <: Graph[_]](g: G, filename: String): Try[Unit] =
    Writer.writeToFile(
      g.degree().mkString("\n"),
      filename,
      s"Error in saving graph's degrees to file [$filename]"
    )

  def saveClusteringCoefficients(clusteringCoefficientsWithSizes: List[(Double, Int)], filename: String): Try[Unit] = {
    val clusteringCoefficientsWithSizesToFile = clusteringCoefficientsWithSizes
      .map { case (size, c) => s"$size,$c" }
      .mkString("\n")
    Writer.writeToFile(
      clusteringCoefficientsWithSizesToFile,
      filename,
      s"Error in saving graphs clustering coefficients to file [$filename]"
    )
  }
}

sealed class GraphException(val message: String) extends Exception(message)

case object GraphNotFoundNodeException extends GraphException("No found node in graph")