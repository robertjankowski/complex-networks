package graph

import path.BreathFirstSearch
import scala.collection.parallel.CollectionConverters._

import scala.collection.mutable

/**
 * Simple graph without loops and multiple edges
 *
 * @tparam T type of node
 */
abstract class SimpleGraph[T] extends Graph[T] with GraphMetrics[T] {

  protected val adjacencyList: mutable.Map[T, mutable.Set[T]] = mutable.Map()

  override def nodes: List[T] = adjacencyList.keys.toList

  override def edges(): List[(T, T)] = {
    val allEdges = adjacencyList.map {
      case (from, nodes) => nodes.toList.map(to => (from, to))
    }.toList.flatten
    val edges = mutable.Set.empty[(T, T)]
    allEdges.filter { case node@(from, to) =>
      if (edges(node)) false
      else {
        edges += node
        edges += ((to, from))
        true
      }
    }
  }

  override def edges(node: T): List[T] = adjacencyList.getOrElse(node, List.empty).toList

  override def addEdgesFrom(edges: List[(T, T)]): Unit =
    edges.foreach { case (from, to) => addEdge(from, to) }

  override def addNode(node: T): Unit =
    if (!adjacencyList.contains(node))
      adjacencyList.addOne(node, mutable.Set.empty[T])

  override def degree(node: T): Either[GraphException, Int] =
    adjacencyList
      .get(node)
      .map(_.size)
      .toRight(GraphNotFoundNodeException)

  override def degree(): List[Int] = adjacencyList.values.map(_.size).toList

  override def hasEdge(from: T, to: T): Boolean =
    adjacencyList
      .get(from)
      .exists(_.contains(to))

  override def hasNode(node: T): Boolean = adjacencyList.contains(node)

  override def neighbours(node: T): List[T] =
    adjacencyList
      .get(node)
      .map(_.toList)
      .getOrElse(List.empty)

  override def clusteringCoefficient(node: T): Double = {
    val nodeNeighbours = neighbours(node)
    if (nodeNeighbours.isEmpty) {
      0.0
    } else {
      val neighboursEdges = nodeNeighbours
        .flatMap(i => nodeNeighbours.filter(j => hasEdge(i, j) && i != j))
        .length.toDouble
      val nodeDegree = degree(node).getOrElse(1)
      neighboursEdges / (nodeDegree * (nodeDegree - 1))
    }
  }

  override def clusteringCoefficient(): Double = {
    val totalClusteringCoefficient = nodes.par.map(clusteringCoefficient).sum
    totalClusteringCoefficient / nodes.length
  }

  override def averageShortestPath(): Double = {
    val shortestPathsDistances = nodes
      .par
      .flatMap(BreathFirstSearch.singleSourceShortestPathLength(this, _).getOrElse(List.empty))
      .sum
    val N = nodes.length
    shortestPathsDistances.toDouble / (N * (N - 1))
  }

  override def toString: String =
    (for {
      (keys, nodes) <- adjacencyList
    } yield keys.toString + "-> " + nodes.mkString(","))
      .mkString("\n")
}

object SimpleGraph {
  type AdjacencyMatrix = Array[Array[Int]]

  def fromAdjacencyMatrix[G <: Graph[Int]](g: G, matrix: AdjacencyMatrix): G = {
    for {
      i <- matrix.indices
      j <- matrix.indices
      if matrix(i)(j) == 1
    } g.addEdge(i, j)
    g
  }

  def complete[G <: Graph[Int]](g: G, n: Int): G = {
    for {
      i <- 0 until n
      j <- 0 until n
      if i != j
    } g.addEdge(i, j)
    g
  }
}

class DirectedSimpleGraph[T] extends SimpleGraph[T] {
  override def addEdge(from: T, to: T): Unit = {
    addNode(from)
    addNode(to)
    adjacencyList.get(from).foreach(nodes => nodes += to)
  }

  def toUndirected: UndirectedSimpleGraph[T] = {
    val g = new UndirectedSimpleGraph[T]
    adjacencyList.foreach {
      case (from, nodes) =>
        nodes.foreach(to => {
          g.addEdge(from, to)
          g.addEdge(to, from)
        })
    }
    g
  }
}

class UndirectedSimpleGraph[T] extends SimpleGraph[T] {
  override def addEdge(from: T, to: T): Unit = {
    addNode(from)
    addNode(to)
    adjacencyList.get(from).foreach(nodes => nodes += to)

    adjacencyList.get(to).foreach(nodes => nodes += from)
  }
}