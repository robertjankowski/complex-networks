import graph.{BarabasiAlbertGraph, GraphUtils}
import utils.Timer

object Main extends App {
  val m = 3
  for {
    n <- List(100, 1000, 10000, 100000, 1000000)
  } Timer.timer {
    BarabasiAlbertGraph.generate(n, m, m)
      .map(GraphUtils.saveDegrees(_, s"ba_n=${n}_m=$m.txt"))
  }
}
