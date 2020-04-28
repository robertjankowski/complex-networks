package graph

import graph.SimpleGraph.AdjacencyMatrix

import scala.util.Random

object ErdosRenyiGraph {

  def randomlyFillAdjacencyMatrix(n: Int, p: Double)(implicit r: Random): SimpleGraph[Int] = {
    val adjacencyMatrix: AdjacencyMatrix = Array.ofDim[Int](n, n)
    for {
      i <- 0 until n
      j <- 0 until i
      if i != j && r.nextDouble() < p
    } adjacencyMatrix(i)(j) = 1
    SimpleGraph.fromAdjacencyMatrix(new UndirectedSimpleGraph[Int], adjacencyMatrix)
  }

  def monteCarloSimulation(n: Int, p: Double)(implicit r: Random): Option[SimpleGraph[Int]] = {
    if (p < 0.5)
      None
    else {
      val adjacencyMatrix: AdjacencyMatrix = Array.ofDim[Int](n, n)
      val theta = math.log(p / (1 - p))
      val acceptanceProbability = math.exp(-theta)
      val theoreticalEdgesNumber = math.ceil(n * (n - 1) * p / 2)
      var edgeCounter = 0
      while (edgeCounter / 2 != theoreticalEdgesNumber) {
        val x = r.nextInt(n)
        val y = r.nextInt(n)
        if (x != y) {
          if (adjacencyMatrix(x)(y) == 0) {
            adjacencyMatrix(x)(y) = 1
            edgeCounter += 1
          } else if (adjacencyMatrix(x)(y) == 1) {
            if (r.nextDouble() < acceptanceProbability) {
              adjacencyMatrix(x)(y) = 0
              edgeCounter -= 1
            }
          }
        }
      }
      Some(SimpleGraph.fromAdjacencyMatrix(new DirectedSimpleGraph[Int], adjacencyMatrix))
    }
  }
}
