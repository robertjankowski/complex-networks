package graph

import utils.{Timer, Writer}

import scala.util.Try

object BarabasiAlbertSimulation {

  def degreeDistributionExperiment(sizes: List[Int], ms: List[Int], filename: String): Unit = {
    for {
      n <- sizes
      m <- ms
    } Timer.timer {
      BarabasiAlbertGraph.generate(n, m, m)
        .foreach(GraphUtils.saveDegrees(_, s"${filename}_n=${n}_m=$m.txt"))
    }(s"Running for size = $n, m = $m")
  }

  def clusteringCoefficientWithAverageLengthPathExperiment(sizes: List[Int], filename: String, m: Int = 3): Try[Unit] = {
    val (clusteringCoefficients, averageLengthPaths) = (for {
      n <- sizes
    } yield Timer.timer {
      BarabasiAlbertGraph
        .generate(n, m, m)
        .map(g => (g.clusteringCoefficient(), g.averageShortestPath()))
        .getOrElse((0.0, 0.0))
    }(s"Running for size = $n")).unzip
    GraphUtils.saveClusteringCoefficients(
      clusteringCoefficients.zip(sizes),
      Writer.customiseFilename("_clustering", filename)
    )
    GraphUtils.saveAverageLengthPath(
      averageLengthPaths.zip(sizes),
      Writer.customiseFilename("_avg_path", filename)
    )
  }

}
