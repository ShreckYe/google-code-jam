fun main() {
    val T = readLine()!!.toInt()
    repeat(T) { t ->
        val (N, K) = readLine()!!.splitToSequence(' ').map(String::toInt).toList()

        val answerMatrix = latinMatrices(N).find { it.trace() == K }

        println("Case #${t + 1}: ${if (answerMatrix == null) "IMPOSSIBLE" else "POSSIBLE\n${answerMatrix}"}")
    }
}

class SquareMatrix private constructor(val n: Int, val content: IntArray) {
    companion object {
        fun zeroMatrix(n: Int) = SquareMatrix(n, IntArray(n * n))
    }

    operator fun get(i: Int, j: Int): Int = content[n * i + j]
    fun copyWithChange(i: Int, j: Int, value: Int) =
        SquareMatrix(n, content.copyOf().apply { this[n * i + j] = value })

    override fun toString(): String {
        val indices = 0 until n
        return indices.asSequence().map { i ->
            val ni = n * i
            indices.map { j -> content[ni + j] }.joinToString(" ")
        }.joinToString("\n")
    }
}

data class MatrixIndex(val i: Int, val j: Int)

fun latinMatrices(n: Int): Sequence<SquareMatrix> =
    latinMatricesFrom(n, SquareMatrix.zeroMatrix(n), 0, 0, (1..n).toList())

fun latinMatricesFrom(
    n: Int, squareMatrix: SquareMatrix, fromI: Int, fromJ: Int, remainingRowChoices: List<Int>
): Sequence<SquareMatrix> =
    if (fromI == n) sequenceOf(squareMatrix)
    else if (fromJ == n) latinMatricesFrom(n, squareMatrix, fromI + 1, 0, (1..n).toList())
    else remainingRowChoices.asSequence()
        .filterNot { value -> (0 until fromI).any { squareMatrix[it, fromJ] == value } }
        .flatMap { value ->
            latinMatricesFrom(
                n, squareMatrix.copyWithChange(fromI, fromJ, value),
                fromI, fromJ + 1, remainingRowChoices.filter { it != value }
            )
        }

fun SquareMatrix.trace() =
    (0 until n).map { this[it, it] }.sum()