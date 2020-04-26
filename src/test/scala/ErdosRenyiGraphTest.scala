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
    g.degree().sum.toDouble / g.degree().length shouldEqual ((N - 1) * p) +- 0.1
  }

  test("MC simulation does not run when p < 0.5") {
    ErdosRenyiGraph.monteCarloSimulation(10, 0.1) shouldEqual None
  }

  test("MC simulation return graph with <k> = p * N") {
    val p = 0.55
    val N = 1000
    val g = ErdosRenyiGraph.monteCarloSimulation(N, p).get
    val meanDegree = g.degree().sum.toDouble / g.degree().length
    meanDegree shouldEqual ((N - 1) * p) +- 0.1
  }

}
