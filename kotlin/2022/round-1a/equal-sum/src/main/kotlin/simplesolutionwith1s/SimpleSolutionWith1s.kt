package simplesolutionwith1s

import kotlin.system.exitProcess

fun main() {
    val t = readLineOrExit()!!.toInt()
    repeat(t, ::testCase)
}

fun readLineOrExit() =
    readLine().also {
        if (it == "-1")
            exitProcess(0)
    }

fun testCase(ti: Int) {
    val nn = readLineOrExit()!!.toInt()
    println(List(nn) { 1 }.joinToString(" "))
    val bbs = readLineOrExit()!!.splitToSequence(' ').map { it.toLong() }.toList()

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

    val expectedSum = (bbs.sum() + nn) / 2
    assert(firstSubsetSum <= expectedSum)
    firstSubset += List((expectedSum - firstSubsetSum).toInt()) { 1L }

    val y = firstSubset.joinToString(" ")
    println(y)
}
