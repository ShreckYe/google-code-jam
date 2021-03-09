fun main() {
    val T = readLine()!!.toInt()
    repeat(T) { t ->
        val (X, Y) = readLine()!!.splitToSequence(' ').map(String::toInt).toList()
        val jumpsStr = jumps(X, Y)?.run { joinToString("") }

        println("Case #${t + 1}: ${jumpsStr ?: "IMPOSSIBLE"}")
    }
}

fun jumps(x: Int, y: Int): Sequence<Char>? =
    if (x == 0 && y == 0) emptySequence()
    else {
        println("$x $y")
        fun recCall(odd: Int, even: Int, xIsOdd: Boolean): Sequence<Char>? {
            val smallerAbsOddDiv2: Int = odd / 2
            val biggerAbsOddDiv2: Int = if (odd > 0) (odd + 1) / 2 else (odd - 1) / 2
            val xDirs = if (odd > 0) 'E' to 'W' else 'W' to 'E'
            val yDirs = if (odd > 0) 'N' to 'S' else 'S' to 'N'
            return if (xIsOdd)
                xDirs.first + jumps(smallerAbsOddDiv2, even)
                    ?: xDirs.second + jumps(biggerAbsOddDiv2, even)
            else
                yDirs.first + jumps(even, smallerAbsOddDiv2)
                    ?: yDirs.second + jumps(even, biggerAbsOddDiv2)
        }

        val xOdd = x and 1 != 0
        val yOdd = y and 1 != 0
        println("$xOdd $yOdd")
        if (xOdd && !yOdd) recCall(x, y, true)
        else if (!xOdd && yOdd) recCall(y, x, false)
        else null
    }

infix operator fun Char.plus(seq: Sequence<Char>?) = seq?.let { sequenceOf(this) + it }