package old

fun main() {
    val tt = readln().toInt()
    repeat(tt, ::testCase)
}

class DpItem(val minNum: Int, val maxCoveredMeters: Int)

fun testCase(tti: Int) {
    val (mm, rr, nn) = readln().splitToSequence(' ').map { it.toInt() }.toList()
    val xxs = readln().splitToSequence(' ').map { it.toInt() }.toList()

    val dp = arrayOfNulls<DpItem>(mm)
    val xi = 0
    for (i in 0 until mm) {
        val previous = dp[i - 1] // TODO: null
        if (previous!!.maxCoveredMeters >= i) {
            dp[i] = previous
            break
        }
        val x = xxs[xi]
        val first = if (i >= x - rr && i + 1 <= x + rr) DpItem(TODO(), TODO()) else TODO()
    }

    val y = TODO()
    println("Case #${tti + 1}: ${if (y) "YES" else "NO"}")
}
