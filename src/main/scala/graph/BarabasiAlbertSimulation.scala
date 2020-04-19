package graph

import utils.Timer

import scala.collection.mutable.ListBuffer

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

  def clusteringCoefficientWithAverageLengthPathExperiment(sizes: List[Int], filename: String, m: Int = 3): Unit = {
    val clusteringCoefficients = ListBuffer.empty[(Double, Int)]
    val averageLengthPaths = ListBuffer.empty[(Double, Int)]
    for {
      n <- sizes
    } Timer.timer {
      BarabasiAlbertGraph
        .generate(n, m, m)
        .foreach(g => {
          clusteringCoefficients += ((g.clusteringCoefficient(), n))
          averageLengthPaths += ((g.averageShortestPath(), n))
        })
    }(s"Running for size = $n")
    GraphUtils.saveClusteringCoefficients(clusteringCoefficients.toList, customiseFilename("_clustering", filename))
    GraphUtils.saveAverageLengthPath(averageLengthPaths.toList, customiseFilename("_avg_path", filename))
  }

  private def customiseFilename(content: String, filename: String): String = {
    val filenameWithExtension = filename.split('.')
    filenameWithExtension(0) + content + '.' + filenameWithExtension(1)
  }

}
