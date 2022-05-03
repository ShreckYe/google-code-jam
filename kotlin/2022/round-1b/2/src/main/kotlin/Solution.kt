import kotlin.math.abs
import kotlin.math.min

fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    val (nn, pp) = readLine()!!.splitToSequence(' ').map { it.toInt() }.toList()
    val xx = List(nn) { readLine()!!.splitToSequence(' ').map { it.toInt() }.toList() }

    val n = nn + 1
    val minMaxXs = listOf(intArrayOf(0, 0)) + xx.map { intArrayOf(it.minOrNull()!!, it.maxOrNull()!!) }
    //println(minMaxXs.map { it.contentToString() })

    val dp = Array(n) { Array(2) { LongArray(2) } }
    dp[0] = Array(2) { LongArray(2) { 0L } }
    for (i in 0 until n - 1)
        for (start in 0 until 2)
            for (end in 0 until 2)
                // swap ends
                dp[i + 1][start][end] = min(
                    dp[i][start][1] + abs(minMaxXs[i + 1][end] - minMaxXs[i][0])/*.also { println("$i -> ${i + 1}, start $start, end 0 -> $end: + $it") }*/,
                    dp[i][start][0] + abs(minMaxXs[i + 1][end] - minMaxXs[i][1])/*.also { println("$i -> ${i + 1}, start $start, end 1 -> $end: + $it") }*/
                )


    val y = dp[n - 1].minOf { it.minOrNull()!! } + minMaxXs.map { (it[1] - it[0]).toLong() }.sum()
    println("Case #${ti + 1}: $y")
}
