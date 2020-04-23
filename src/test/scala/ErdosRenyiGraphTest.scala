import graph.ErdosRenyiGraph
import org.scalatest.{FunSuite, Matchers}

import scala.util.Random

class ErdosRenyiGraphTest  extends FunSuite with Matchers {

  test("should <k> = p * N") {
    implicit val r = Random
    val p = 0.02
    val N = 1000
    val g = ErdosRenyiGraph.randomlyFillAdjacencyMatrix(N, p)
    g.degree().sum.toDouble / g.degree().length shouldEqual (N * p) +- 0.5
  }

}
