import java.util.*
import kotlin.math.ceil

fun main() {
    val T = readLine()!!.toInt()
    repeat(T) { testCase() }
}

fun testCase() {
    val A = readLine()!!.toInt()

    // Build a rectangle of 3 * some length.
    val rectangleLength = ceil(A.toFloat() / 3).toInt().coerceAtLeast(3)
    val preparedRectangle = BitSet(rectangleLength * 3)
    fun BitSet.rectangleSet(x: Int, y: Int) = set(x * 3 + y)
    fun BitSet.rectangleGet(x: Int, y: Int) = get(x * 3 + y)

    for (numExpectedEmptyCells in 9 downTo 1) {
        for (cx in 1 until rectangleLength - 1) {
            fun BitSet.countPrepared(cx: Int): Int {
                var count = 0
                for (x in cx - 1..cx + 1) for (y in 0..2)
                    if (rectangleGet(x, y))
                        count++
                return count
            }

            val numEmptyCells = 9 - preparedRectangle.countPrepared(cx)
            if (numEmptyCells > numExpectedEmptyCells) throw IllegalStateException()
            else if (numEmptyCells == numExpectedEmptyCells) {
                val I = cx + 1
                val J = 1 + 1
                while (true) {
                    println("$I $J")

                    val (Ip, Jp) = readLine()!!.splitToSequence(' ').map { it.toInt() }.toList()
                    if (Ip == -1 && Jp == -1) throw IllegalStateException()
                    if (Ip == 0 && Jp == 0) return
                    val x = Ip - 1
                    val y = Jp - 1
                    if (!preparedRectangle.rectangleGet(x, y)) {
                        preparedRectangle.rectangleSet(x, y)
                        break
                    }
                }
            }
        }
    }
    println("2 2")
    val (Ip, Jp) = readLine()!!.splitToSequence(' ').map { it.toInt() }.toList()
    if (!(Ip == 0 && Jp == 0)) throw IllegalStateException()
}