import graph.ErdosRenyiSimulation

object Main extends App {
  ErdosRenyiSimulation
    .compareTraditionalMethodWithMonteCarloSimulation(1000, 0.55, "output/p_55_n_1000.txt")
}