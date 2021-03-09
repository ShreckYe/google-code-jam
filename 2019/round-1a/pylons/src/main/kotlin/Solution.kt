import java.util.*

fun main() {
    val T = readLine()!!.toInt()
    repeat(T) { t ->
        val (R, C) = readLine()!!.splitToSequence(' ').map(String::toInt).toList()
        val answer = case(R, C)
        println(
            "Case #${t + 1}: ${
            if (answer !== null) "POSSIBLE\n${answer.joinToString("\n") { (r, c) -> "${r + 1} ${c + 1}" }}"
            else "IMPOSSIBLE"
            }"
        )
    }
}
data class Cell(val r: Int, val c: Int)
typealias VisitList = List<Cell>
typealias VisitPath = VisitList
typealias LazyVisitPath = Sequence<Cell>
fun case(R: Int, C: Int): VisitList? {

}
class TwoDBitSet private constructor(val n: Int, val content:BitSet) {
    companion object {
        fun empty(n: Int) = TwoDBitSet(n, BitSet(n * n))
    }

    operator fun get(i: Int, j: Int): Boolean= content[n * i + j]
    operator fun set(i: Int, j: Int, value: Boolean) {
        content[n * i + j] = value
    }
    fun copy() = TwoDBitSet(n, (content.clone() as BitSet))
}
typealias Galaxy = TwoDBitSet
fun allVisitPaths(
    nlastVisited: Cell, unvisited: VisitList, visited: LazyVisitPath, visitedGalaxy: Galaxy, n :Int
) : Sequence<LazyVisitPath> =
    if (unvisited.isEmpty()) sequenceOf(visited)
    else {
        val validDestinations = Galaxy.empty(n)
    }
