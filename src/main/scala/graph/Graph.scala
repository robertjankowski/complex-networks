package graph

trait Graph[T] {

  def nodes: List[T]

  def edges: List[(T, T)]

  def addVertex(vertex: T): Unit

  def addEdge(from: T, to: T): Unit

  def addEdgesFrom(edges: List[(T, T)]): Unit

  def degree(): List[Int]

  def degree(node: T): Either[GraphException, Int]

  def hasEdge(from: T, to: T): Boolean

  def neighbours(node: T): Either[GraphException, List[T]]
}
