package path

import graph.SimpleGraph

import scala.collection.mutable

object BreathFirstSearch {

  // https://eddmann.com/posts/depth-first-search-and-breadth-first-search-in-python/
  def shortestPath[T, G <: SimpleGraph[T]](g: G, from: T, to: T): Option[List[T]] = {
    if (!(g.hasNode(from) && g.hasNode(to)))
      None
    else {
      val queue = mutable.Queue.empty[(T, mutable.ArrayBuffer[T])]
      queue += ((from, mutable.ArrayBuffer(from)))
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

  // https://networkx.github.io/documentation/networkx-1.9/_modules/networkx/algorithms/shortest_paths/unweighted.html#single_source_shortest_path_length
  def singleSourceShortestPathLength[T, G <: SimpleGraph[T]](g: G, from: T): Option[List[Int]] = {
    if (!g.hasNode(from))
      None
    else {
      val seen = mutable.Map.empty[T, Int]
      var level = 0
      val nextLevel = mutable.Map.empty[T, Int]
      nextLevel += (from -> 1)
      while (nextLevel.nonEmpty) {
        val thisLevel = nextLevel.toMap
        nextLevel.clear()
        for ((v, _) <- thisLevel) {
          if (!seen.contains(v)) {
            seen(v) = level
            g.neighbours(v) match {
              case Left(_) =>
              case Right(neighbours) => nextLevel.addAll(neighbours.zipWithIndex)
            }
          }
        }
        level += 1
      }
      Some(seen.values.toList)
    }
  }

}