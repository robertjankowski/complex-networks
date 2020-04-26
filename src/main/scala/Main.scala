import graph.ErdosRenyiSimulation

object Main extends App {
  ErdosRenyiSimulation
    .compareTraditionalMethodWithMonteCarloSimulation(100, 0.55, "output/p_55_n_100.txt")
}