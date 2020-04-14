import org.scalatest.FunSuite

class GraphTest extends FunSuite {

  test("Graph degree test") {
    val graph = new UndirectedGraph[Int]()
    graph.addEdge(1, 2)
    graph.addEdge(1, 3)
    graph.addEdge(2, 3)
    assert(graph.degree(1) == 2)
    assert(graph.degree(2) == 2)
    assert(graph.degree(3) == 2)
  }
}
