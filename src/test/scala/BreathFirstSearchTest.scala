import graph.UndirectedSimpleGraph
import org.scalatest.{FunSuite, Matchers}
import path.BreathFirstSearch

class BreathFirstSearchTest extends FunSuite with Matchers {

  test("should not calculate path when node does not exist") {
    val g = new UndirectedSimpleGraph[Int]
    g.addEdge(1, 2)
    g.addEdge(2, 3)
    BreathFirstSearch.shortestPath(g, 10, 2) shouldEqual None
    BreathFirstSearch.shortestPath(g, 1, 10) shouldEqual None
  }

  test("should return shortest path") {
    val g = new UndirectedSimpleGraph[Int]
    g.addEdge(1, 2)
    g.addEdge(2, 3)
    g.addEdge(2, 5)
    g.addEdge(2, 6)
    g.addEdge(3, 4)
    g.addEdge(5, 6)
    BreathFirstSearch.shortestPath(g, 1, 6).get should contain theSameElementsAs List(1, 2, 6)
    BreathFirstSearch.shortestPath(g, 1, 3).get.length shouldEqual 3
    BreathFirstSearch.shortestPath(g, 6, 3).get should contain theSameElementsAs List(6, 2, 3)
  }

  test("should return length of shortest paths") {
    val g = new UndirectedSimpleGraph[Int]
    g.addEdge(1, 2)
    g.addEdge(2, 3)
    g.addEdge(2, 5)
    g.addEdge(2, 6)
    g.addEdge(3, 4)
    g.addEdge(4, 5)
    g.addEdge(5, 6)
    BreathFirstSearch.singleSourceShortestPathLength(g, 1).get shouldEqual List(0, 1, 2, 3, 2, 2)
    BreathFirstSearch.singleSourceShortestPathLength(g, 2).get shouldEqual List(1, 0, 1, 2, 1, 1)
    BreathFirstSearch.singleSourceShortestPathLength(g, 3).get shouldEqual List(2, 1, 0, 1, 2, 2)
    BreathFirstSearch.singleSourceShortestPathLength(g, 4).get shouldEqual List(3, 2, 1, 0, 1, 2)
    BreathFirstSearch.singleSourceShortestPathLength(g, 5).get shouldEqual List(2, 1, 2, 1, 0, 1)
    BreathFirstSearch.singleSourceShortestPathLength(g, 6).get shouldEqual List(2, 1, 2, 2, 1, 0)
  }

}
