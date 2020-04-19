package graph

trait Graph[T] {

  def nodes: List[T]

  def edges(): List[(T, T)]

  def edges(node: T): List[T]

  def addNode(node: T): Unit

  def addEdge(from: T, to: T): Unit

  def addEdgesFrom(edges: List[(T, T)]): Unit

  def degree(): List[Int]

  def degree(node: T): Either[GraphException, Int]

  def hasEdge(from: T, to: T): Boolean

  def hasNode(node: T): Boolean

  def neighbours(node: T): List[T]
}
