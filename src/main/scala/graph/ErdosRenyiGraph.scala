package graph

import graph.SimpleGraph.AdjacencyMatrix

import scala.collection.mutable.ListBuffer
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

  def monteCarloSimulation(n: Int, p: Double, nIterations: Int = 10000)
                          (implicit r: Random): Option[SimpleGraph[Int]] = {
    if (p < 0.5)
      None
    else {
      val adjacencyMatrix: AdjacencyMatrix = Array.ofDim[Int](n, n)
      val theta = math.log(p / (1 - p))
      val acceptanceProbability = math.exp(-theta)
      var edgeCounter = 0
      val nEdges = ListBuffer.empty[Int]
      val cutoffLength = 30
      for (_ <- 0 until nIterations) {
        val x = r.nextInt(n)
        val y = r.nextInt(n)
        nEdges += edgeCounter
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
        if (nEdges.length > cutoffLength) {
          // TODO: break look if <E> = const.
        }
      }
      println(countEdges(adjacencyMatrix))
      println(edgeCounter / 2)
      Some(SimpleGraph.fromAdjacencyMatrix(new DirectedSimpleGraph[Int], adjacencyMatrix))
    }
  }

  private def countEdges(adjacencyMatrix: AdjacencyMatrix): Int = adjacencyMatrix.map(_.sum).sum / 2
}
