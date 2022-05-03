fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    val (nn, kk) = readLine()!!.splitToSequence(' ').map { it.toInt() }.toList()
    val ees = readLine()!!.splitToSequence(' ').map { it.toInt() }.toList()

    val longEes = ees.map { it.toLong() }

    val c = (longEes.sumOf { it.squared() } - longEes.sum().squared()) / 2
    val a = longEes.sum()

    val y =
        if (a == 0L)
            if (c == 0L) 1 else null
        else
            if (c % a == 0L) c / a else null
    println("Case #${ti + 1}: ${y ?: "IMPOSSIBLE"}")
}

fun Long.squared() =
    this * this