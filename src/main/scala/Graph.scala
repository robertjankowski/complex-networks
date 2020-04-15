trait Graph[T] {

  def nodes: List[T]

  def edges: List[(T, T)]

  def addVertex(vertex: T): Unit

  def addEdge(from: T, to: T): Unit

  def degree(node: T): Int

  def hasEdge(from: T, to: T): Boolean
}

