import scala.io.StdIn

object DPSolution {
  def main(args: Array[String]): Unit = {
    val T = StdIn.readInt()
    for (i <- 0 until T) {
      val N = StdIn.readInt()
      val P = StdIn.readLine()

      val pPositions = P.scanLeft((0, 0)) { case ((i, j), move) => if (move == 'S') (i + 1, j) else (i, j + 1) }

      // paths are stored reversely
      var prevRowRPaths: Array[List[Char]] = null
      var curRowRPaths: Array[List[Char]] = null

      for (i <- 0 until N) {
        curRowRPaths = Array.fill[List[Char]](N)(null)
        for (j <- 0 until N) {
          curRowRPaths(j) = if (i == 0 && j == 0) List.empty
          else {
            val index = i + j
            val pPosition = pPositions(index)
            val pLastMove = P(index - 1)

            def pathFrom(lastIRowRPaths: Array[List[Char]], lastJ: Int, lastMove: Char) = {
              if ((i, j) == pPosition && lastMove == pLastMove) null
              else {
                val lastPath = lastIRowRPaths(lastJ)
                if (lastPath == null) null else lastMove :: lastPath
              }
            }

            val pathFromNorth = if (i > 0) pathFrom(prevRowRPaths, j, 'S') else null

            def pathFromWest = if (j > 0) pathFrom(curRowRPaths, j - 1, 'E') else null

            if (pathFromNorth != null) pathFromNorth else pathFromWest
          }
        }
        prevRowRPaths = curRowRPaths
      }

      val y = prevRowRPaths(N - 1).view.reverse.mkString
      println(s"Case #${i + 1}: $y")
    }
  }
}