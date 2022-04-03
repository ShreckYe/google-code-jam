import kotlin.math.max

fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    val (n, k) = readLine()!!.splitToSequence(' ').map { it.toInt() }.toList()
    val ps = readLine()!!.splitToSequence(' ').map { it.toInt() }.toList()

    val sortedPs = ps.sorted().distinct()
    val sortedGaps = sortedPs.asSequence().zipWithNext()
        .map { it.second - it.first - 1 }
        .filter { it > 0 }
        .sortedDescending().toList()
    val startAndEndGaps = listOf(sortedPs.first() - 1, k - sortedPs.last()).filter { it > 0 }
    val maxGap = sortedGaps.getOrNull(0)
    val secondMaxGap = sortedGaps.getOrNull(1)

    val maxNumbersTaken = max(
        (listOfNotNull(maxGap, secondMaxGap).map(::numbersTakenWithSingle) + startAndEndGaps)
            .sortedDescending().run { if (size >= 2) take(2).sum() else 0 },
        (listOfNotNull(maxGap) + startAndEndGaps).maxOrNull() ?: 0
    )


    val y = maxNumbersTaken.toDouble() / k
    println("Case #${ti + 1}: $y")
}

fun numbersTakenWithSingle(gap: Int): Int =
    (gap - 1) / 2 + 1