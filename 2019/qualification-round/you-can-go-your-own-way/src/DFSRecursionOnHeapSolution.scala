import scala.collection.mutable
import scala.io.StdIn

object DFSRecursionOnHeapSolution {
  def main(args: Array[String]): Unit = {
    val T = StdIn.readInt()
    for (i <- 0 until T) {
      val N = StdIn.readInt()

      val P = StdIn.readLine()
      //def P(i: Int) = if (i < N) 'S' else 'E'

      val end = N - 1
      val dead = new mutable.BitSet(N * N)

      type PathSolution = Option[List[Char]]
      type PathSolutionOrFun = Either[PathSolution, AnyRecursiveFunAndTransformation]
      type AnyRecursiveFunAndTransformation = ( /*RecursiveFun*/ () => Any, /*Transformation*/ PathSolution => Any)
      type RecursiveFun = () => PathSolutionOrFun
      type Transformation = PathSolution => PathSolutionOrFun

      def aValidPathFrom(i: Int, j: Int, pi: Int, pj: Int): PathSolutionOrFun = {
        if (i == end && j == end) Left(Some(Nil))
        else {
          val cacheIndex = N * i + j
          if (dead(cacheIndex)) Left(None)
          else {
            val pMove = P(i + j)

            def afterMove(i: Int, j: Int, move: Char) = if (move == 'S') (i + 1, j) else (i, j + 1)

            def pathAfterMove(move: Char): PathSolutionOrFun = {
              val (ni, nj) = afterMove(i, j, move)

              if (ni < N && !(i == pi && j == pj && move == pMove)) {
                val (npi, npj) = afterMove(pi, pj, pMove)
                Right((() => aValidPathFrom(ni, nj, npi, npj), path => Left(path.map(move :: _))))
              } else Left(None)
            }

            Right((() => pathAfterMove('S'), sPath => {
              Right[PathSolution, AnyRecursiveFunAndTransformation](() => sPath match {
                case some: Some[List[Char]] => Left(some)
                case None => pathAfterMove('E')
              }, path => {
                if (path.isEmpty) dead(cacheIndex) = true
                Left(path)
              })
            }))
          }
        }
      }

      type ParameterFun = Transformation

      def evaluateOnHeap(fun: () => PathSolutionOrFun): PathSolution = {
        val funStack = mutable.Stack.empty[ParameterFun]
        funStack.push(_ => fun())
        var current: PathSolution = null

        while (funStack.nonEmpty) {
          val method = funStack.pop()
          method(current) match {
            case Left(pathSolution) =>
              current = pathSolution
            case Right((recursiveFun: RecursiveFun, transformation: Transformation)) =>
              funStack.push(transformation)
              funStack.push(_ => recursiveFun())
              current = null
          }
        }

        current
      }

      val y = evaluateOnHeap(() => aValidPathFrom(0, 0, 0, 0)).get.mkString
      println(s"Case #${i + 1}: $y")
    }
  }
}