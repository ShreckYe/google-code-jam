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
    /*
    println("fs: " + firstSubset)
    println("fss: " + firstSubsetSum)
    println("es: " + expectedSum)
    */
    greedy(bbs, expectedSum, nn)
        ?: if (nn <= 28) bruteForce(bbs, expectedSum, nn)
        else throw IllegalStateException("greedy doesn't work when nn=$nn")

fun generateAas(nn: Int) =
    List(nn) { (1 shl it.coerceAtMost(29)).toLong() }

fun greedy(bbs: List<Long>, expectedSum: Long, nn: Int): List<Long>? {
    val sbbs = bbs.sortedDescending()
    val firstSubset = ArrayList<Long>(nn * 2)
    var firstSubsetSum = 0L
    var secondSubsetSum = 0L
    for (b in sbbs)
        if (firstSubsetSum <= secondSubsetSum) {
            firstSubset += b
            firstSubsetSum += b
        } else
            secondSubsetSum += b

    val n = nn.coerceAtMost(29)
    return if (canPick(firstSubsetSum, expectedSum, n))
        firstSubset + pickAAs((expectedSum - firstSubsetSum).toInt(), n)
    else null
}

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

fun bruteForce(bbs: List<Long>, expectedSum: Long, n: Int) =
    bbs.allSubsets().firstNotNullOf {
        val bbsFirstSubsetSum = it.sum()
        if (canPick(bbsFirstSubsetSum, expectedSum, n))
            it + pickAAs((expectedSum - bbsFirstSubsetSum).toInt(), n)
        else
            null
    }


fun bitSubsets(size: Int): Sequence<Int> {
    require(size <= 30)
    return (0 until (1 shl size)).asSequence()
}

fun <T> List<T>.allSubsets(): Sequence<List<T>> =
    bitSubsets(size).map { s -> filterIndexed { i, _ -> (s and (1 shl i)) != 0 } }
