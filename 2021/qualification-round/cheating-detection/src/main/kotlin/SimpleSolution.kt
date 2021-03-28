fun main() {
    val t = readLine()!!.toInt()
    val p = readLine()!!.toInt()

    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    val results = List(100) {
        readLine()!!.map { it - '0' }
    }

    // simple estimation by averaging
    val playerCorrectPs = results.map { it.average() }

    val y = playerCorrectPs.withIndex().maxByOrNull { it.value }!!.index

    println("Case #${ti + 1}: ${y + 1}")
}