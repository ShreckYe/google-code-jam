fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    val (n, k) = readLine()!!.splitToSequence(' ').map { it.toInt() }.toList()
    // This could be optimized to use a priority queue.
    var emptyPartitions = listOf(0 to n - 1)
    repeat(k - 1) {
        val indexedStallPartition = emptyPartitions.withIndex().maxByOrNull { it.value.run { second - first } }!!
        val partitionIndex = indexedStallPartition.index
        val stallPartition = indexedStallPartition.value
        val stall = stallPartition.run { (first + second) / 2 }
        infix fun Int.partitionToOrNull(that: Int) =
            if (this <= that) this to that else null

        emptyPartitions = emptyPartitions.subList(0, partitionIndex) +
                listOfNotNull(
                    stallPartition.first partitionToOrNull stall - 1,
                    stall + 1 partitionToOrNull stallPartition.second
                ) +
                emptyPartitions.subList(partitionIndex + 1, emptyPartitions.size)
    }

    val emptyLrSum = emptyPartitions.maxOf { it.second - it.first }
    val y = (emptyLrSum + 1) / 2 // ceil div
    val z = emptyLrSum / 2
    println("Case #${ti + 1}: $y $z")
}