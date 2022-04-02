fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    TODO("read input")
    //val _ = readLine()!!.toInt()/.toLong()
    val (n, k) = readLine()!!.splitToSequence(' ').map { it.toInt() }.toList()
    repeat(k) {
        val (r, p) = readLine()!!.splitToSequence(' ').map { it.toInt() }.toList()

        println("W")
        println("T 1")
    }
    println("E 123")

    TODO("compute")

    val y = TODO()
    println("Case #${ti + 1}: $y")
}