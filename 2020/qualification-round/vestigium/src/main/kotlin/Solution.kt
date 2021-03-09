import java.util.*

fun main() {
    val T = readLine()!!.toInt()
    repeat(T) { t ->
        val N = readLine()!!.toInt()
        val M = Array(N) { IntArray(N) }
        repeat(N) { i ->
            val line = readLine()!!
            val row = M[i]
            line.splitToSequence(' ').map(String::toInt).forEachIndexed { j, m -> row[j] = m }
        }

        val indices = 0 until N
        val k = indices.asSequence().map { M[it][it] }.sum()
        val r = M.count { containsRepeated1toN(it, N) }
        val c = indices.count { j -> containsRepeated1toN(indices.map { i -> M[i][j] }.toIntArray(), N) }

        println("Case #${t + 1}: $k $r $c")
    }
}

fun containsRepeated1toN(sequence: IntArray, N: Int): Boolean {
    val exists = BitSet(N + 1)
    for (num in sequence)
        if (exists[num]) return true
        else exists[num] = true
    return false
}