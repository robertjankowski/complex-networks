package graph

import utils.Timer

import scala.collection.mutable.ListBuffer

object BarabasiAlbertSimulation {

  def degreeDistributionExperiment(size: List[Int], ms: List[Int], filename: String): Unit = {
    for {
      n <- size
      m <- ms
    } Timer.timer {
      BarabasiAlbertGraph.generate(n, m, m)
        .map(GraphUtils.saveDegrees(_, s"${filename}_n=${n}_m=$m.txt"))
    }
  }

  def clusteringCoefficientExperiment(size: List[Int], filename: String, m: Int = 3): Unit = {
    val clusteringCoefficients = ListBuffer.empty[(Double, Int)]
    for {
      n <- size
    } Timer.timer {
      BarabasiAlbertGraph
        .generate(n, m, m)
        .map(g => clusteringCoefficients += ((g.clusteringCoefficient(), n)))
    }
    GraphUtils.saveClusteringCoefficients(clusteringCoefficients.toList, filename)
  }

}
