fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    val lineInputs = readLine()!!.split(' ')
    val x = lineInputs[0].toInt()
    val y = lineInputs[1].toInt()
    val s = lineInputs[2]

    var lastExistingPattern = ' '
    var minCost = 0
    for (i in s.indices) {
        val pattern = s[i]

        // We could also first filter out all "?"s and then compute functionally, which would make this solution shorter.
        if (pattern == '?') continue

        if (pattern != lastExistingPattern && lastExistingPattern != ' ') {
            when (pattern) {
                // CJ
                'J' -> minCost += x
                // JC
                'C' -> minCost += y
                else -> throw IllegalArgumentException()
            }
        }
        lastExistingPattern = pattern
    }

    println("Case #${ti + 1}: $minCost")
}