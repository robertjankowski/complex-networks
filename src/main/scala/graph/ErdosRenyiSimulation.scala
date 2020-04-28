package graph

import scala.util.Random
import ErdosRenyiGraph._
import utils.Writer

object ErdosRenyiSimulation {

  private implicit val r = Random

  def compareTraditionalMethodWithMonteCarloSimulation(n: Int, p: Double, filename: String): Unit =
    monteCarloSimulation(n, p) match {
      case Some(mcGraph) =>
        GraphUtils.saveDegrees(randomlyFillAdjacencyMatrix(n, p), Writer.customiseFilename("er_traditional_network", filename))
        GraphUtils.saveDegrees(mcGraph, Writer.customiseFilename("er_mc_network", filename))
        ()
      case None => println("p must be greater than 0.5")
    }

}
