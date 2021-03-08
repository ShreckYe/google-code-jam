import java.util.*

fun main() {
    val T = readLine()!!.toInt()
    repeat(T) { t ->
        val N = readLine()!!.toInt()
        println("Case #${t + 1}:\n${case(N).joinToString("\n")}}")
    }
}

data class Position(val r: Int, val k: Int) {
    fun isValid() = r >= 0 && k >= 0 && k <= r
}

fun case(N: Int): List<Position> {
    val cache = IntArray(CAPACITY)
    for (r in 0 until MAX_R)
        for (k in 0..r)
            cache[index(r, k)] = cache.getOrElse(index(r - 1, k - 1)) { 0 } + cache.getOrElse(index(r - 1, k)) { 0 }

    fun value(r: Int, k: Int): Int {
        val index = index(r, k)
        return if (index < CAPACITY) cache[index]
        else value(r - 1, k - 1) + value(r - 1, k)
    }

    fun value(position: Position) = value(position.r, position.k)

    fun allWalksFrom(
        fromWalk: Sequence<Position>, fromCurrent: Position, walked: BitSet
    ): Sequence<Sequence<Position>> {
        println(fromCurrent)
        val (ri, ki) = fromCurrent
        return sequenceOf(
            Position(ri - 1, ki - 1), Position(ri - 1, ki),
            Position(ri, ki - 1), Position(ri, ki + 1),
            Position(ri + 1, ki), Position(ri + 1, ki + 1)
        )
            .filter { it.isValid() && !walked[index(it)] }
            .flatMap {newPosition ->
                allWalksFrom(fromWalk + newPosition, newPosition, walked.run {
                    val newWalked = clone() as BitSet
                    newWalked[index(newPosition)] = true
                    newWalked
                }).map { fromWalk + newPosition + it  }
            }
    }

    val allWalks = allWalksFrom(emptySequence(), Position(0, 0), BitSet(CAPACITY))

    return allWalks.first { it.sumBy(::value) == N }.toList()
}

const val MAX_R = 30
val CAPACITY = index(MAX_R, 0)

fun index(position: Position): Int = index(position.r, position.k)
fun index(r: Int, k: Int): Int =
    r * (r + 1) / 2 + k