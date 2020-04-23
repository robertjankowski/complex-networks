package graph

import scala.util.Random

object ErdosRenyiGraph {

  def randomlyFillAdjacencyMatrix(n: Int, p: Double)(implicit r: Random): UndirectedSimpleGraph[Int] = {
    val adjacencyMatrix = Array.ofDim[Int](n, n)
    for {
      i <- 0 until n
      j <- 0 until i
      if i != j && r.nextDouble() < p
    } adjacencyMatrix(i)(j) = 1
    SimpleGraph.fromAdjacencyMatrix(new UndirectedSimpleGraph[Int], adjacencyMatrix)
  }

}
