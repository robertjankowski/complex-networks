package graph

trait GraphMetrics[T] {

  def clusteringCoefficient(node: T): Double

  def clusteringCoefficient(): Double

  def averageShortestPath(): Double
}
