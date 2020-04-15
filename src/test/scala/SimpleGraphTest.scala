import org.scalatest._

class SimpleGraphTest extends FunSuite with Matchers {

  test("should return node degrees") {
    val graph = new UndirectedSimpleGraph[Int]()
    graph.addEdge(1, 2)
    graph.addEdge(1, 3)
    graph.addEdge(2, 3)
    graph.degree(1) shouldBe 2
    graph.degree(2) shouldBe 2
    graph.degree(3) shouldBe 2
  }

  test("should create complete graphs") {
    val testDegree = 10
    val g = SimpleGraph.complete(new UndirectedSimpleGraph[Int], testDegree)
    for {
      i <- 0 until testDegree
    } g.degree(i) shouldBe testDegree - 1

    val gd = SimpleGraph.complete(new DirectedSimpleGraph[Int], testDegree)
    for {
      i <- 0 until testDegree
    } gd.degree(i) shouldBe testDegree - 1
  }

  test("should return graph nodes") {
    val g = SimpleGraph.complete(new UndirectedSimpleGraph[Int], 3)
    g.nodes shouldEqual List(0, 1, 2)
  }

  test("should return graph edges") {
    val g = new DirectedSimpleGraph[String]()
    g.addEdge("a", "c")
    g.addEdge("b", "d")
    g.edges shouldEqual List(("a", "c"), ("b", "d"))
  }

  test("should check existence of edge") {
    val g = new DirectedSimpleGraph[String]()
    g.addEdge("a", "c")
    g.addEdge("b", "d")
    assert(g.hasEdge("a", "c"))
    assert(g.hasEdge("b", "d"))
    assert(!g.hasEdge("a", "b"))
    assert(!g.hasEdge("c", "f"))
  }

  test("should convert directed to undirected graph") {
    val g = new DirectedSimpleGraph[String]()
    g.addEdge("a", "c")
    g.addEdge("b", "d")
    val ud = g.toUndirected
    ud.edges should contain theSameElementsAs List(("a", "c"), ("c", "a"), ("b", "d"), ("d", "b"))
  }
}
