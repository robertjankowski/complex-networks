package utils

object Timer {

  def timer[R](code: => R): R = {
    val t0 = System.currentTimeMillis()
    val result = code
    val t1 = System.currentTimeMillis()
    println(s"Elapsed: ${t1 - t0} ms")
    result
  }
}
