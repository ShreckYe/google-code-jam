/*
import kotlin.math.floor
import kotlin.math.log10

fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    val y = readLine()!!

    val ansY = ans(y)

    println("Case #${ti + 1}: $ansY")
}

fun ans(y: String): Long {
    val yLong = y.toLong()
    return (1..y.length / 2).flatMap {
        val start = y.take(it).toLong()
        val yLength = y.length
        val min10PowGtStart = min10PowGt(yLong)
        (listOfNotNull(
            roaring(start, yLength)?.takeIf { it.toLong() > yLong },
            roaring(start + 1, yLength)
        ) +


                listOfNotNull(
                    roaring(min10PowGt(yLong), yLength + 1)
                )).map { it.toLong() }
    }.minOrNull()!!
}

*/
/*fun min10PowGt(n: Long) =
    10L powInt (floor(log10(n.toDouble())).toInt() + 1)*//*


fun roaring(start: Long, length: Int): String? =
    possibleRoaring(start, length)?.run { if (second >= 2) first else null }

fun possibleRoaring(start: Long, length: Int): Pair<String, Int>? =
    if (length == 0) "" to 0
    else {
        val startS = start.toString()
        val startLength = startS.length
        if (startLength > length)
            null
        else possibleRoaring(start + 1, length - startLength)?.run {
            startS + first to second + 1
        }
    }


fun Long.squared(): Long =
    this * this

infix fun Long.powInt(that: Int): Long =
    when {
        that == 0 -> 1L
        that > 0 -> powInt(that / 2).squared()
            .let { if (that % 2 == 0) it else it * this }
        else -> throw IllegalArgumentException("exponent less than 0")
    }
*/
