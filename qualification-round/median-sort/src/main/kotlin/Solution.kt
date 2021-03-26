import kotlin.system.exitProcess

fun main() {
    val (t, n, q) = readLine()!!.splitToSequence(' ').map { it.toInt() }.toList()
    repeat(t) { testCase(n, q) }
}

fun testCase(n: Int, q: Int) {
    var orderedXIndices = listOf(1, 2)
    for (k in 3..n) {
        // size != 1
        fun insertInto(orderedXIndices: List<Int>): List<Int> =
            if (orderedXIndices.isEmpty()) listOf(k)
            else {
                // uniformly divide into 3
                val size = orderedXIndices.size
                if (size == 1) throw IllegalArgumentException()
                val iI = (size - 2) / 3
                val i = orderedXIndices[iI]
                val jI = (size * 2 - 1) / 3
                val j = orderedXIndices[jI]

                /* i and j can't be the same.
                * When size = 1, i = -1, j = 0;
                * When size = 2, i = 0, j = 1, the recursion terminates;
                * When size = 3, i = 0, j = 1;
                * When size = 4, i = 0, j = 2.
                * Therefore, size should >= 2.
                * */

                println("$i $j $k")
                val l = readLine()!!.toInt()

                when (l) {
                    i -> {
                        val divIndex = if (iI == 1) 2 else iI
                        insertInto(orderedXIndices.subList(0, divIndex)) +
                                orderedXIndices.subList(divIndex, size)
                    }
                    k -> {
                        val iIP1 = iI + 1
                        val divIndex = if (jI - iIP1 == 1) iI else iIP1
                        orderedXIndices.subList(0, divIndex) +
                                insertInto(orderedXIndices.subList(divIndex, jI)) +
                                orderedXIndices.subList(jI, size)
                    }
                    j -> {
                        val jIP1 = jI + 1
                        val divIndex = if (size - jIP1 == 1) jI else jIP1
                        orderedXIndices.subList(0, divIndex) +
                                insertInto(orderedXIndices.subList(divIndex, size))
                    }
                    -1 -> exitProcess(0)
                    else -> throw IllegalArgumentException()
                }
            }

        orderedXIndices = insertInto(orderedXIndices)
    }

    println(orderedXIndices.joinToString(" "))
    when (readLine()!!) {
        "1" -> Unit
        "-1" -> throw AssertionError()
        else -> throw IllegalArgumentException()
    }
}