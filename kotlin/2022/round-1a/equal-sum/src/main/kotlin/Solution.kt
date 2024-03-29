import kotlin.system.exitProcess

fun main() {
    val t = readLineOrExit()!!.toInt()
    repeat(t) { testCase() }
}

fun readLineOrExit() =
    readLine().also {
        if (it == "-1")
            exitProcess(0)
    }

fun testCase() {
    val nn = readLineOrExit()!!.toInt()
    val aas = generateAas(nn)
    println(aas.joinToString(" "))
    val bbs = readLineOrExit()!!.splitToSequence(' ').map { it.toLong() }.toList()

    val expectedSum = (bbs.sum() + aas.sum()) / 2
    println(solve(nn, bbs, expectedSum).joinToString(" "))
}

fun solve(nn: Int, bbs: List<Long>, expectedSum: Long): List<Long> =
    greedyAndPick(bbs, expectedSum, nn)!!

fun generateAas(nn: Int) =
    List(nn) { (1 shl it.coerceAtMost(29)).toLong() }

fun greedyPartitionMinDiff(bbs: List<Long>): Pair<Long, List<Long>> {
    val sbbs = bbs.sortedDescending()
    val firstSubset = ArrayList<Long>(bbs.size * 2)
    var firstSubsetSum = 0L
    var secondSubsetSum = 0L
    for (b in sbbs)
        if (firstSubsetSum <= secondSubsetSum) {
            firstSubset += b
            firstSubsetSum += b
        } else
            secondSubsetSum += b

    return firstSubsetSum to firstSubset
}

fun greedyAndPick(bbs: List<Long>, expectedSum: Long, nn: Int): List<Long>? {
    val (firstSubsetSum, firstSubset) = greedyPartitionMinDiff(bbs)

    val n = nn.coerceAtMost(29)
    return if (canPick(firstSubsetSum, expectedSum, n))
        firstSubset + pickAAs((expectedSum - firstSubsetSum).toIntExact(), n)
    else null
}

fun Long.toIntExact() =
    Math.toIntExact(this)

fun maxSum(n: Int): Int {
    require(n <= 29)
    return 2 shl (n + 1) - 1
}

fun canPick(bbsFirstSubsetSum: Long, expectedSum: Long, n: Int): Boolean {
    require(n <= 29)
    return bbsFirstSubsetSum <= expectedSum && bbsFirstSubsetSum + maxSum(n) >= expectedSum
}

fun pickAAs(diff: Int, n: Int): List<Long> {
    require(n <= 29)
    val list = ArrayList<Long>(n + 1)
    for (i in 0..n) {
        val twoPowI = 1 shl i
        if (diff and twoPowI != 0)
            list.add(twoPowI.toLong())
    }
    return list
}
