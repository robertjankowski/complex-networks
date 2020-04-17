package path

import graph.SimpleGraph

object Dijkstra extends Path {
  override def shortestPath[T, G <: SimpleGraph[T]](g: G, from: T, to: T): Option[List[T]] = {
    if (!(g.hasNode(from) && g.hasNode(to)))
      None
    else {
      // TODO:
      // https://medium.com/se-notes-by-alexey-novakov/algorithms-in-scala-dijkstra-shortest-path-78c4291dd8ab

      None
    }
  }
}
