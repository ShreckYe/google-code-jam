fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    val cartridges = List(3) {
        readLine()!!.splitToSequence(' ').map { it.toInt() }.toList()
    }

    val minColors = List(4) { ci -> cartridges.map { it[ci] }.minOrNull()!! }
    val maxSum = minColors.sum()
    if (maxSum >= 1000000) {
        val remainingColors = minColors.toMutableList()
        var remainingSum = maxSum
        for (ci in 0 until 4) {
            val d = remainingSum - 1000000
            if (remainingColors[ci] >= d) {
                remainingColors[ci] -= d
                break
            } else {
                remainingSum -= remainingColors[ci]
                remainingColors[ci] = 0
            }
        }

        println("Case #${ti + 1}: ${remainingColors.joinToString(" ")}")
    } else
        println("Case #${ti + 1}: IMPOSSIBLE")
}
