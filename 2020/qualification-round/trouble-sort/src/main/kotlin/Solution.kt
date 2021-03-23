fun main() {
    val T = readLine()!!.toInt()
    repeat(T, ::case)
}

fun case(t: Int) {
    val N = readLine()!!.toInt()
    val Vs = readLine()!!.splitToSequence(' ').map { it.toInt() }.toList()

    val (evenIndexNumsWI, oddIndexNumsWI) = Vs.withIndex().partition { it.index % 2 == 0 }
    val evenIndexArray = evenIndexNumsWI.map { it.value }.toIntArray()
    val oddIndexArray = oddIndexNumsWI.map { it.value }.toIntArray()

    evenIndexArray.sort()
    oddIndexArray.sort()

    val troubleSortedArray = IntArray(N) { i -> if (i % 2 == 0) evenIndexArray[i / 2] else oddIndexArray[i / 2] }

    val firstErrorIndex = troubleSortedArray.asSequence().zipWithNext().indexOfFirst { it.first > it.second }

    val y = if (firstErrorIndex == -1) "OK" else firstErrorIndex
    println("Case #${t + 1}: $y")
}