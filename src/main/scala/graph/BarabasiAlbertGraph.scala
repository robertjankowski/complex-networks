package graph

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

object BarabasiAlbertGraph {

  private def randomSubset(repeatedNodes: ArrayBuffer[Int],
                           m: Int)
                          (implicit random: Random): List[Int] = {
    val targets = mutable.Set.empty[Int]
    while (targets.size < m) {
      val x = random.nextInt(repeatedNodes.length)
      targets += repeatedNodes(x)
    }
    targets.toList
  }

  def generate(n: Int, m: Int, m0: Int): Option[UndirectedSimpleGraph[Int]] = {
    if (m > m0 || m < 1)
      None
    else {
      implicit val r = Random
      val g = SimpleGraph.complete(new UndirectedSimpleGraph[Int], m0)
      var targets = (0 to m).toList
      var source = m
      val repeatedNodes = ArrayBuffer.empty[Int]
      for (_ <- m0 until n) {
        val sources = Array.fill(m)(source).toList
        g.addEdgesFrom(sources.zip(targets))
        repeatedNodes.addAll(targets)
        repeatedNodes.addAll(sources)
        targets = randomSubset(repeatedNodes, m)
        source += 1
      }
      Some(g)
    }
  }

}
