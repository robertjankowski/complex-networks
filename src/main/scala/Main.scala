import graph.ErdosRenyiSimulation

object Main extends App {
  ErdosRenyiSimulation
    .compareTraditionalMethodWithMonteCarloSimulation(100, 0.80, "output/p_80_n_100.txt")
}