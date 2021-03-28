fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    val n = readLine()!!.toInt()
    val ls = readLine()!!.splitToSequence(' ').map { it.toInt() }.toList()
    val lsArray = ls.toIntArray()

    var cost = 0
    for (i in 0 until ls.size - 1) {
        val currentLs = lsArray.asList()
        val (jMinusI, min) = currentLs.subList(i, ls.size).withIndex().minByOrNull { it.value }!!
        val j = i + jMinusI
        val reversed = currentLs.subList(i, j + 1).reversed() // Don't use asReversed here!
        for ((k, v) in reversed.withIndex())
            lsArray[i + k] = v
        cost += jMinusI + 1
    }

    println("Case #${ti + 1}: $cost")
}