fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    val (n, k) = readLine()!!.splitToSequence(' ').map { it.toLong() }.toList()

    // This part of the algorithm can be optimized to use logarithm but it's not really necessary.
    var numRoundStallsSlashPartitions = 1L // Long, not Int!
    var rk = k
    var rn = n
    while (rk > numRoundStallsSlashPartitions) {
        rk -= numRoundStallsSlashPartitions
        rn -= numRoundStallsSlashPartitions
        numRoundStallsSlashPartitions *= 2
    }
    val smallerPartitionLength = rn / numRoundStallsSlashPartitions
    val numLargerPartitions = rn % numRoundStallsSlashPartitions

    val partitionLength =
        if (rk <= numLargerPartitions) smallerPartitionLength + 1
        else smallerPartitionLength

    val emptyLrSum = partitionLength - 1
    val y = (emptyLrSum + 1) / 2 // ceil div
    val z = emptyLrSum / 2

    println("Case #${ti + 1}: $y $z")
}