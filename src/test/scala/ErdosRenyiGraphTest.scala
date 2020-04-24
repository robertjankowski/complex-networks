import graph.ErdosRenyiGraph
import org.scalatest.{FunSuite, Matchers}

import scala.util.Random

class ErdosRenyiGraphTest extends FunSuite with Matchers {

  private implicit val r = Random
  r.setSeed(141)

  test("mean degree <k> = p * N") {
    val p = 0.02
    val N = 1000
    val g = ErdosRenyiGraph.randomlyFillAdjacencyMatrix(N, p)
    g.degree().sum.toDouble / g.degree().length shouldEqual (N * p) +- 1
  }

  test("MC simulation does not run when p < 0.5") {
    ErdosRenyiGraph.monteCarloSimulation(10, 0.1, 10) shouldEqual None
  }

  test("MC simulation return graph with <k> = p * N") {
    val p = 0.8
    val N = 100
    val g = ErdosRenyiGraph.monteCarloSimulation(N, p, 100000).get
    g.degree().sum.toDouble / g.degree().length shouldEqual (N * p) +- 1
  }

}
