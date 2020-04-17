package path

import graph.SimpleGraph

trait Path {
  def shortestPath[T, G <: SimpleGraph[T]](g: G, from: T, to: T): Option[List[T]]
}
