import org.scalatest._


class BarabasiAlbertGraphTest extends FunSuite with Matchers {

  test("cannot generate BA network when m > m0") {
    val ba = BarabasiAlbertGraph.generate(10, 10, 3)
    ba should be(None)
  }

  test("should generate graph with N nodes") {
    val N = 1000
    val ba = BarabasiAlbertGraph.generate(N, 3, 3).get
    ba.nodes.length shouldEqual N
  }

  test("should generate graph with E ~ m*N") {
    val N = 1000
    val ba = BarabasiAlbertGraph.generate(N, 3, 3).get
    ba.edges.length should (be <= N * 3 and be >= N * 3 - 10)
  }

  test("should generate graph with <k> ~ 2m") {
    val N = 100000
    val ba = BarabasiAlbertGraph.generate(N, 3, 3).get
    val degrees = ba.degree()
    val averageDegree = degrees.sum.doubleValue / degrees.length
    averageDegree shouldBe 6.0 +- 0.1
  }
}
