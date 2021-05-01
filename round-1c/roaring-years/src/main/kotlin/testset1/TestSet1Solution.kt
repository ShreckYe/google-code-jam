package testset1

fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

const val upperBound = 1_999_999_999_999_999_999L
fun testCase(ti: Int) {
    val y = readLine()!!
    val ansY = ans(y)
    println("Case #${ti + 1}: $ansY")
}

fun ans(y: String): Long {
    val yL = y.toLong()
    return ((yL + 1)..upperBound).first { isRoaring(it) }
}

fun isRoaring(y: Long) =
    isRoaring(y.toString())

fun isRoaring(y: String) =
    (1..y.length / 2).any {
        val remaining = y.drop(it)
        remaining.isNotEmpty() && isRoaringFrom(remaining, y.take(it).toLong() + 1)
    }

fun isRoaringFrom(y: String, start: Long): Boolean =
    if (y.isEmpty()) true
    else {
        val nS = start.toString()
        y.startsWith(nS) && isRoaringFrom(y.drop(nS.length), start + 1)
    }
