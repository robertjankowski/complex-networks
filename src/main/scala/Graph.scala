import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

abstract class Graph[T]() {

  protected val adjacencyList: mutable.Map[T, ArrayBuffer[T]] = mutable.Map()

  def addVertex(vertex: T): Unit =
    if (!adjacencyList.contains(vertex))
      adjacencyList.addOne(vertex, ArrayBuffer.empty[T])

  def addEdge(from: T, to: T): Unit

  def degree(node: T): Int = adjacencyList(node).length

  def hasEdge(from: T, to: T): Boolean = ??? // if true do not need to addEdge!

  override def toString: String = {
    (for {
      (keys, nodes) <- adjacencyList
    } yield keys + "-> " + nodes.mkString(","))
      .mkString("\n")
  }
}

object Graph {
  // TODO: implement only one complete graph method
}

class DirectedGraph[T]() extends Graph[T] {
  override def addEdge(from: T, to: T): Unit = {
    addVertex(from)
    adjacencyList.get(from).map(nodes => nodes += to)
  }
}

object DirectedGraph {
  def complete(n: Int): DirectedGraph[Int] = {
    val directedGraph = new DirectedGraph[Int]
    for {
      i <- 0 to n
      j <- 0 to n
    } directedGraph.addEdge(i, j)
    directedGraph
  }
}

class UndirectedGraph[T]() extends Graph[T] {
  override def addEdge(from: T, to: T): Unit = {
    addVertex(from)
    addVertex(to)
    adjacencyList.get(from).foreach(nodes => nodes += to)
    adjacencyList.get(to).foreach(nodes => nodes += from)
  }
}
