fun main() {
    val T = readLine()!!.toInt()
    repeat(T) { t ->
        val (N, K) = readLine()!!.splitToSequence(' ').map(String::toInt).toList()

        val answerMatrix = latinMatrices(N).find { it.trace() == K }

        println("Case #${t + 1}: ${if (answerMatrix == null) "IMPOSSIBLE" else "POSSIBLE\n${answerMatrix}"}")
    }
}

class Matrix private constructor(val n: Int, val content: IntArray) {
    companion object {
        fun zeroMatrix(n: Int) = Matrix(n, IntArray(n * n))
    }

    operator fun get(i: Int, j: Int): Int = content[n * i + j]
    fun copyWithChange(i: Int, j: Int, value: Int) =
        Matrix(n, content.copyOf().apply { this[n * i + j] = value })

    override fun toString(): String {
        val indices = 0 until n
        return indices.asSequence().map { i ->
            val ni = n * i
            indices.map { j -> content[ni + j] }.joinToString(" ")
        }.joinToString("\n")
    }
}

data class MatrixIndex(val i: Int, val j: Int)

fun latinMatrices(n: Int): Sequence<Matrix> =
    latinMatricesFrom(n, Matrix.zeroMatrix(n), 0, 0, (1..n).toList())

fun latinMatricesFrom(
    n: Int, matrix: Matrix, fromI: Int, fromJ: Int, remainingRowChoices: List<Int>
): Sequence<Matrix> =
    if (fromI == n) sequenceOf(matrix)
    else if (fromJ == n) latinMatricesFrom(n, matrix, fromI + 1, 0, (1..n).toList())
    else remainingRowChoices.asSequence()
        .filterNot { value -> (0 until fromI).any { matrix[it, fromJ] == value } }
        .flatMap { value ->
            latinMatricesFrom(
                n, matrix.copyWithChange(fromI, fromJ, value),
                fromI, fromJ + 1, remainingRowChoices.filter { it != value }
            )
        }

fun Matrix.trace() =
    (0 until n).map { this[it, it] }.sum()