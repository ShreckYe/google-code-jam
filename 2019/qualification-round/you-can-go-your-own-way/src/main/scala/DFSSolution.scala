import scala.collection.mutable
import scala.io.StdIn

object DFSSolution {
  def main(args: Array[String]): Unit = {
    val T = StdIn.readInt()
    for (i <- 0 until T) {
      val N = StdIn.readInt()

      val P = StdIn.readLine()
      //def P(i: Int) = if (i < N) 'S' else 'E'

      val end = N - 1
      val dead = new mutable.BitSet(N * N)

      def aValidPathFrom(i: Int, j: Int, pi: Int, pj: Int): Option[List[Char]] = {
        if (i == end && j == end) Some(Nil)
        else {
          val cacheIndex = N * i + j
          if (dead(cacheIndex)) None
          else {
            val pMove = P(i + j)

            def afterMove(i: Int, j: Int, move: Char) = if (move == 'S') (i + 1, j) else (i, j + 1)

            def pathAfterMove(move: Char) = {
              val (ni, nj) = afterMove(i, j, move)

              if (ni < N && !(i == pi && j == pj && move == pMove)) {
                val (npi, npj) = afterMove(pi, pj, pMove)
                aValidPathFrom(ni, nj, npi, npj).map(move :: _)
              } else None
            }

            val path = pathAfterMove('S').orElse(pathAfterMove('E'))
            if (path.isEmpty) dead(cacheIndex) = true
            path
          }
        }
      }

      val y = aValidPathFrom(0, 0, 0, 0).get.mkString
      println(s"Case #${i + 1}: $y")
    }
  }
}
