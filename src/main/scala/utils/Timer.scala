package utils

object Timer {

  def timer[A](code: => A)(message: String): A = {
    val t0 = System.currentTimeMillis()
    val result = code
    val t1 = System.currentTimeMillis()
    println(s"$message\t--\tElapsed: ${t1 - t0} ms")
    result
  }
}
