fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    val n = readLine()!!.toInt()
    val ss = readLine()!!.splitToSequence(' ').map { it.toInt() }.toList()

    val sortedSs = ss.sorted()

    var currentStraightN = 0
    for (s in sortedSs)
        if (currentStraightN < s)
            currentStraightN++

    println("Case #${ti + 1}: $currentStraightN")
}
