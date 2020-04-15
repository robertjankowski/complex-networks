object Main extends App {
  BarabasiAlbertGraph.generate(100000, 3, 3)
    .map(GraphUtils.saveDegrees(_, "ba_n=100000.txt"))
}
