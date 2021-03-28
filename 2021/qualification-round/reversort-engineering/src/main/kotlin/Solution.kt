fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    val (n, c) = readLine()!!.splitToSequence(' ').map { it.toInt() }.toList()

    val y = getReversortList(n, c, 1)?.run { joinToString(" ") } ?: "IMPOSSIBLE"
    println("Case #${ti + 1}: $y")
}

fun getReversortList(n: Int, c: Int, from: Int): List<Int>? =
    // n - 1 <= c <= n + ... + 2
    if (c < n - 1) null
    else if (n == 1 && c > 0) null
    else if (c == n - 1) (from until from + n).toList()
    else {
        val nextN = n - 1
        val minNextC = c - n
        if (minNextC >= nextN - 1)
            getReversortList(nextN, minNextC, from + 1)?.let { it.reversed() + from }
        else {
            val nextC = nextN - 1
            val currentRoundCost = c - nextC

            getReversortList(nextN, nextC, from + 1)?.let {
                it.subList(0, currentRoundCost - 1).asReversed() + from + it.subList(currentRoundCost - 1, it.size)
            }
        }
    }
