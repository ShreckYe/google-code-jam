fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    val lineInputs = readLine()!!.split(' ')
    val x = lineInputs[0].toInt()
    val y = lineInputs[1].toInt()
    val s = lineInputs[2]

    val minCost = s.asSequence().filterNot { it == '?' }.zipWithNext().map {
        val window = it.toList().joinToString("")
        when (window) {
            "CJ" -> x
            "JC" -> y
            else -> if (window[0] == window[1]) 0
            else throw IllegalArgumentException()
        }
    }.sum()

    println("Case #${ti + 1}: $minCost")
}