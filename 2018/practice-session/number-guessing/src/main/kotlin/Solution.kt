fun main() {
    val t = readLine()!!.toInt()
    repeat(t) { testCase() }
}

fun testCase() {
    val (a, b) = readLine()!!.splitToSequence(' ').map { it.toInt() }.toList()
    val n = readLine()!!.toInt()

    var l = a + 1
    var r = b
    while (/*l < r*/true) {
        val q = (l + r) / 2
        println(q)
        val word = readLine()!!
        when (word) {
            "CORRECT" -> break
            "TOO_SMALL" -> l = q + 1
            "TOO_BIG" -> r = q - 1
            "WRONG_ANSWER" -> throw IllegalArgumentException()
            else -> throw IllegalArgumentException()
        }
    }
}