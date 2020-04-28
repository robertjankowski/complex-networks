package graph

import utils.Writer

import scala.util.Try

object GraphUtils {

  type MetricsWithSizes = List[(Double, Int)]

  def saveDegrees[G <: Graph[_]](g: G, filename: String): Try[Unit] =
    Writer.writeToFile(
      g.degree().mkString("\n"),
      filename,
      s"Error in saving graph's degrees to file [$filename]"
    )

  def saveClusteringCoefficients(clusteringCoefficientsWithSizes: MetricsWithSizes, filename: String): Try[Unit] = {
    saveGraphMetrics(
      clusteringCoefficientsWithSizes,
      filename,
      s"Error in saving graphs clustering coefficients to file [$filename]"
    )
  }

  def saveAverageLengthPath(averagePathLengthsWithSizes: MetricsWithSizes, filename: String): Try[Unit] = {
    saveGraphMetrics(
      averagePathLengthsWithSizes,
      filename,
      s"Error in saving graphs average length path to file [$filename]"
    )
  }

  def saveGraph[T](g: Graph[T], filename: String): Try[Unit] = {
    val edgeList = g.edges().map {
      case (from, to) => s"$from\t$to"
    }.mkString("\n")
    Writer.writeToFile(
      edgeList,
      filename,
      s"Error in saving edge list to file [$filename]"
    )
  }

  private def saveGraphMetrics(metricsWithSizes: MetricsWithSizes, filename: String, errorMessage: String): Try[Unit] = {
    val metricsWithSizesToFile = metricsWithSizes
      .map { case (size, metric) => s"$size,$metric" }
      .mkString("\n")
    Writer.writeToFile(
      metricsWithSizesToFile,
      filename,
      errorMessage
    )
  }
}

sealed class GraphException(val message: String) extends Exception(message)

case object GraphNotFoundNodeException extends GraphException("No found node in graph")