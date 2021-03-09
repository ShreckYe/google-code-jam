import kotlin.math.abs

fun main() {
    val T = readLine()!!.toInt()
    repeat(T) { t ->
        val line = readLine()!!.split(' ')
        val X = line[0].toInt()
        val Y = line[1].toInt()
        val M = line[2]

        val y = case(X, Y, M)

        println("Case #${t + 1}: ${if (y == -1) "IMPOSSIBLE" else y.toString()}")
    }
}

fun case(X: Int, Y: Int, M: String): Int {
    val length = M.length
    var x = X
    var y = Y
    var t = 0
    do {
        val d = abs(x) + abs(y)
        if (d <= t) return t

        when (M[t]) {
            'N' -> y++
            'E' -> x++
            'S' -> y--
            'W' -> x--
            else -> throw IllegalArgumentException()
        }
        t++
    } while (t < length)
    val d = abs(x) + abs(y)
    if (d <= t) return t
    return -1
}