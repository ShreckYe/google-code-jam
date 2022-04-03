package bfs

fun main() {
    val T = readLine()!!.toInt()
    repeat(T) { t ->
        val (X, Y) = readLine()!!.splitToSequence(' ').map(String::toInt).toList()
        val zero = Point(0, 0)
        val goal = Point(X, Y)
        val bfsPoints = bfsPointsWithin(sequenceOf(zero to emptySequence()), 0, 9)
        val jumps = bfsPoints.firstOrNull { it.first == goal && it.second.any() }?.run { second.joinToString("") }

        println("Case #${t + 1}: ${jumps ?: "IMPOSSIBLE"}")
    }
}

data class Point(val x: Int, val y: Int)
typealias History = Sequence<Char>
typealias PointWithHis = Pair<Point, History>

fun bfsPointsWithin(from: Sequence<PointWithHis>, i: Int, nSteps: Int): Sequence<PointWithHis> =
    if (nSteps == 0) from
    else bfsPointsWithin(from + from.flatMap {
        nextPointsFrom(
            it.first,
            i,
            it.second
        )
    }, i + 1, nSteps - 1)

// i means i + 1 here
fun nextPointsFrom(from: Point, i: Int, history: Sequence<Char>): Sequence<PointWithHis> {
    val (x, y) = from
    val jump = 1 shl i
    return directions.map {
        when (it) {
            'N' -> Point(x, y + jump)
            'S' -> Point(x, y - jump)
            'E' -> Point(x + jump, y)
            'W' -> Point(x - jump, y)
            else -> throw AssertionError()
        } to history + it
    }
}

val directions = sequenceOf('N', 'S', 'E', 'W')