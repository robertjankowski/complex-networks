package graph

import scala.collection.mutable

/**
 * Simple graph without loops
 *
 * @tparam T type of vertex
 */
abstract class SimpleGraph[T]() extends Graph[T] with GraphMetrics[T] {

  protected val adjacencyList: mutable.Map[T, mutable.Set[T]] = mutable.Map()

  override def nodes: List[T] = adjacencyList.keys.toList

  override def edges: List[(T, T)] = {
    val edges = mutable.Set[(T, T)]()
    val allEdges = adjacencyList.map {
      case (from, nodes) =>
        nodes.toList.map(to => (from, to))
    }.toList.flatten
    allEdges.filter { case node@(from, to) =>
      if (edges(node)) false
      else {
        edges += node
        edges += ((to, from))
        true
      }
    }
  }

  override def addEdgesFrom(edges: List[(T, T)]): Unit = {
    edges.foreach { case (from, to) => addEdge(from, to) }
  }

  override def addVertex(vertex: T): Unit =
    if (!adjacencyList.contains(vertex))
      adjacencyList.addOne(vertex, mutable.Set.empty[T])

  override def degree(node: T): Either[GraphException, Int] =
    adjacencyList
      .get(node)
      .map(_.size)
      .toRight(GraphNotFoundNodeException)

  override def degree(): List[Int] = adjacencyList.map {
    case (_, nodes) => nodes.size
  }.toList

  override def hasEdge(from: T, to: T): Boolean =
    adjacencyList
      .get(from)
      .exists(nodes => nodes.contains(to))

  override def neighbours(node: T): Either[GraphException, List[T]] =
    adjacencyList
      .get(node)
      .map(_.toList)
      .toRight(GraphNotFoundNodeException)

  override def clusteringCoefficient(node: T): Double = {
    val nodeNeighbours = neighbours(node).getOrElse(List.empty)
    var neighboursEdges = 0.0
    for {
      i <- nodeNeighbours
      j <- nodeNeighbours
      if hasEdge(i, j) && i != j
    } neighboursEdges += 1
    if (neighboursEdges > 0) {
      val nodeDegree = degree(node).getOrElse(1)
      neighboursEdges / (nodeDegree * (nodeDegree - 1))
    }
    else
      0.0
  }

  override def clusteringCoefficient(): Double = {
    var totalClusteringCoefficient = 0.0
    nodes.foreach(totalClusteringCoefficient += clusteringCoefficient(_))
    totalClusteringCoefficient / nodes.length
  }

  override def toString: String =
    (for {
      (keys, nodes) <- adjacencyList
    } yield keys + "-> " + nodes.mkString(","))
      .mkString("\n")
}

object SimpleGraph {
  def complete[G <: Graph[Int]](g: G, n: Int): G = {
    for {
      i <- 0 until n
      j <- 0 until n
      if i != j
    } g.addEdge(i, j)
    g
  }
}

class DirectedSimpleGraph[T]() extends SimpleGraph[T] {
  override def addEdge(from: T, to: T): Unit = {
    addVertex(from)
    addVertex(to)
    adjacencyList.get(from).map(nodes => nodes += to)
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

class UndirectedSimpleGraph[T]() extends SimpleGraph[T] {
  override def addEdge(from: T, to: T): Unit = {
    addVertex(from)
    addVertex(to)
    adjacencyList.get(from).foreach(nodes => nodes += to)
    adjacencyList.get(to).foreach(nodes => nodes += from)
  }
}
