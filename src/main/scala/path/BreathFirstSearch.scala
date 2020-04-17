package path

import graph.SimpleGraph

import scala.collection.mutable

object BreathFirstSearch extends Path {

  // https://eddmann.com/posts/depth-first-search-and-breadth-first-search-in-python/
  override def shortestPath[T, G <: SimpleGraph[T]](g: G, from: T, to: T): Option[List[T]] = {
    if (!(g.hasNode(from) && g.hasNode(to)))
      None
    else {
      val queue = mutable.Queue.empty[(T, mutable.ListBuffer[T])]
      queue += ((from, mutable.ListBuffer(from)))
      while (queue.nonEmpty) {
        val (node, path) = queue.dequeue()
        for (next <- g.edges(node).diff(path.distinct)) {
          if (next == to)
            return Some((path :+ next).toList)
          else
            queue += ((next, path :+ next))
        }
      }
      None
    }
  }
}
