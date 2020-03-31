import scala.io.StdIn

object RecursiveSolution {
  def main(args: Array[String]): Unit = {
    val T = StdIn.readInt()
    for (i <- 0 until T) {
      val N = StdIn.readInt()
      val P = StdIn.readLine()

      val pPositions = P.scanLeft((0, 0)) { case ((i, j), move) => if (move == 'S') (i + 1, j) else (i, j + 1) }
      val cachedRPaths = Array.fill[List[Char]](N, N)(null)

      def aReversedValidPathTo(i: Int, j: Int): List[Char] = {
        val cachedPath = cachedRPaths(i)(j)
        if (cachedPath != null) cachedPath
        else {
          val searchedPath = if (i == 0 && j == 0) List.empty
          else {
            val index = i + j
            val pPosition = pPositions(index)
            val pLastMove = P(index - 1)

            def pathFrom(lastRow: Int, lastColumn: Int, lastMove: Char) = {
              if ((i, j) == pPosition && lastMove == pLastMove) null
              else {
                val lastPath = aReversedValidPathTo(lastRow, lastColumn)
                if (lastPath == null) null else lastMove :: lastPath
              }
            }

            val pathFromNorth = if (i > 0) pathFrom(i - 1, j, 'S') else null

            def pathFromWest = if (j > 0) pathFrom(i, j - 1, 'E') else null

            if (pathFromNorth != null) pathFromNorth else pathFromWest
          }
          cachedRPaths(i)(j) = searchedPath
          searchedPath
        }
      }

      val y = aReversedValidPathTo(N - 1, N - 1).view.reverse.mkString
      println(s"Case #${i + 1}: $y")
    }
  }
}