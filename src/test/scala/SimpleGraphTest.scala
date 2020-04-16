import graph.{DirectedSimpleGraph, GraphNotFoundNodeException, SimpleGraph, UndirectedSimpleGraph}
import org.scalatest._

class SimpleGraphTest extends FunSuite with Matchers {

  test("should return node degrees") {
    val graph = new UndirectedSimpleGraph[Int]
    graph.addEdge(1, 2)
    graph.addEdge(1, 3)
    graph.addEdge(2, 3)
    graph.degree(1) shouldBe Right(2)
    graph.degree(2) shouldBe Right(2)
    graph.degree(3) shouldBe Right(2)
  }

  test("should return no degree when node does not exist") {
    val g = new UndirectedSimpleGraph[Int]
    g.degree(1) shouldBe Left(GraphNotFoundNodeException)
  }

  test("should create complete graphs") {
    val testDegree = 10
    val g = SimpleGraph.complete(new UndirectedSimpleGraph[Int], testDegree)
    for {
      i <- 0 until testDegree
    } g.degree(i) shouldBe Right(testDegree - 1)

    val gd = SimpleGraph.complete(new DirectedSimpleGraph[Int], testDegree)
    for {
      i <- 0 until testDegree
    } gd.degree(i) shouldBe Right(testDegree - 1)
  }

  test("should return graph nodes") {
    val g = SimpleGraph.complete(new UndirectedSimpleGraph[Int], 3)
    g.nodes shouldEqual List(0, 1, 2)
  }

  test("should return graph edges") {
    val g = new DirectedSimpleGraph[String]
    g.addEdge("a", "c")
    g.addEdge("b", "d")
    g.edges shouldEqual List(("a", "c"), ("b", "d"))
  }

  test("should check existence of edge") {
    val g = new DirectedSimpleGraph[String]
    g.addEdge("a", "c")
    g.addEdge("b", "d")
    assert(g.hasEdge("a", "c"))
    assert(g.hasEdge("b", "d"))
    assert(!g.hasEdge("a", "b"))
    assert(!g.hasEdge("c", "f"))
  }

  test("should convert directed to undirected graph") {
    val g = new DirectedSimpleGraph[String]
    g.addEdge("a", "c")
    g.addEdge("b", "d")
    val ud = g.toUndirected
    ud.edges should contain theSameElementsAs List(("a", "c"), ("b", "d"))
  }

  test("should check number of edges in complete graph") {
    val N = 10
    val g = SimpleGraph.complete(new UndirectedSimpleGraph[Int], N)
    g.edges.length shouldEqual N * (N - 1) / 2
  }

  test("should return neighbours of a given node") {
    val g = new UndirectedSimpleGraph[String]
    g.addEdge("a", "b")
    g.addEdge("a", "c")
    g.neighbours("a") match {
      case Left(_) =>
      case Right(neighbours) => neighbours should contain theSameElementsAs List("b", "c")
    }
  }

  test("should return no neighbours") {
    val g = new UndirectedSimpleGraph[String]
    g.neighbours("a") match {
      case Left(ex) => ex.message shouldEqual "No found node in graph"
      case Right(_) =>
    }
  }

  test("should correctly calculate C") {
    val g = new UndirectedSimpleGraph[Int]
    g.addEdge(1, 2)
    g.addEdge(1, 3)
    g.addEdge(1, 4)
    g.addEdge(1, 5)
    g.addEdge(3, 4)
    g.clusteringCoefficient(1) shouldEqual (1.0 / 6)
  }

  test("should complete graph has zero C") {
    val g = SimpleGraph.complete(new UndirectedSimpleGraph[Int], 10)
    g.clusteringCoefficient() shouldEqual 1.0
  }

}
