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
    val g = SimpleGraph.complete(new UndirectedSimpleGraph[Int], testDegree + 1)
    for {
      i <- 0 until testDegree
    } g.degree(i) shouldBe Right(testDegree)

    val gd = SimpleGraph.complete(new DirectedSimpleGraph[Int], testDegree + 1)
    for {
      i <- 0 until testDegree
    } gd.degree(i) shouldBe Right(testDegree)
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
    ud.edges() should contain theSameElementsAs List(("a", "c"), ("b", "d"))
    ud.edges("a") should contain theSameElementsAs List("c")
    ud.edges("c") should contain theSameElementsAs List("a")
    ud.edges("b") should contain theSameElementsAs List("d")
    ud.edges("d") should contain theSameElementsAs List("b")
  }

  test("should check number of edges in complete graph") {
    val N = 10
    val g = SimpleGraph.complete(new UndirectedSimpleGraph[Int], N)
    g.edges().length shouldEqual N * (N - 1) / 2
  }

  test("should return neighbours of a given node") {
    val g = new UndirectedSimpleGraph[String]
    g.addEdge("a", "b")
    g.addEdge("a", "c")
    g.neighbours("a") should contain theSameElementsAs List("b", "c")
  }

  test("should return no neighbours") {
    val g = new UndirectedSimpleGraph[String]
    g.neighbours("a") shouldEqual List.empty
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

  test("should complete graph has C = 1") {
    val g = SimpleGraph.complete(new UndirectedSimpleGraph[Int], 10)
    g.clusteringCoefficient() shouldEqual 1.0
  }

  test("should return edges for given node") {
    val g = new UndirectedSimpleGraph[Int]
    g.addEdge(1, 2)
    g.addEdge(1, 3)
    g.edges(1) should contain theSameElementsAs List(2, 3)
    g.edges(2) should contain theSameElementsAs List(1)
    g.edges(10) shouldEqual List.empty
  }

  test("should calculate average shortest path") {
    val g = SimpleGraph.complete(new UndirectedSimpleGraph[Int], 10)
    g.averageShortestPath() shouldEqual 1.0
  }

  test("should create directed graph from adjacency matrix") {
    val matrix = Array.ofDim[Int](3, 3)
    matrix(0)(1) = 1
    matrix(0)(2) = 1
    matrix(2)(1) = 1
    val g = SimpleGraph.fromAdjacencyMatrix(new DirectedSimpleGraph[Int], matrix)
    g.edges().length shouldEqual 3
    g.nodes should contain theSameElementsAs List(0, 1, 2)
    g.edges() should contain theSameElementsAs List((0, 1), (0, 2), (2, 1))
  }

  test("should create undirected graph from adjacency matrix") {
    val matrix = Array.ofDim[Int](3, 3)
    matrix(0)(1) = 1
    matrix(0)(2) = 1
    matrix(1)(2) = 1
    matrix(2)(1) = 1
    val g = SimpleGraph.fromAdjacencyMatrix(new UndirectedSimpleGraph[Int], matrix)
    g.edges().length shouldEqual 3
    g.nodes should contain theSameElementsAs List(0, 1, 2)
  }

}
