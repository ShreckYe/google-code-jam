package biginteger

import java.math.BigInteger

fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    val (nn, kk) = readLine()!!.splitToSequence(' ').map { it.toInt() }.toList()
    val ees = readLine()!!.splitToSequence(' ').map { it.toBigInteger() }.toList()

    val c = (ees.sumOf { it.squared() } - ees.sumOf { it }.squared()) / BigInteger.TWO
    val a = ees.sumOf { it }

    val y =
        if (a == BigInteger.ZERO)
            if (c == BigInteger.ZERO) BigInteger.ONE else null
        else c / a
    println("Case #${ti + 1}: ${y ?: "IMPOSSIBLE"}")
}

fun BigInteger.squared() =
    this * this