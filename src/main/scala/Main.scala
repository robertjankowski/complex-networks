import graph.BarabasiAlbertSimulation

object Main extends App {
  val sizes = List(100, 1000, 10000, 100000, 1000000)
  val ms = List(1, 2, 5)
  // BarabasiAlbertSimulation.degreeDistributionExperiment(sizes, ms, "output/ba_degree")

  val sizes2 = List(50, 100, 200, 500, 1000, 2000, 5000)
  BarabasiAlbertSimulation.clusteringCoefficientExperiment(sizes2, s"output/ba_clustering_coefficient.txt")
}
