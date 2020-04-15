object Main extends App {
  val ba = BarabasiAlbertGraph.generate(100000, 3, 3)
  ba.map(g => GraphUtils.saveDegrees(g, "ba_n=100000.txt"))
}
