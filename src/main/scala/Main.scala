import graph.{ErdosRenyiGraph, GraphUtils}

import scala.util.Random

object Main extends App {
  implicit val r = Random
  GraphUtils.saveGraph(
    ErdosRenyiGraph.randomlyFillAdjacencyMatrix(100, 0.06),
    "output/er_network.edgelist"
  )
}