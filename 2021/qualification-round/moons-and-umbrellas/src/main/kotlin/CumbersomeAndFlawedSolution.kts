fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    val lineInputs = readLine()!!.split(' ')
    val x = lineInputs[0].toInt()
    val y = lineInputs[1].toInt()
    val s = lineInputs[2]


    val numStartingEmptySpaces = s.asSequence().takeWhile { it == '?' }.count()
    val startingLastEMIndex = numStartingEmptySpaces - 1
    val numEndingEmptySpaces = s.reversed().asSequence().takeWhile { it == '?' }.count()
    val endingLastEMIndex = numEndingEmptySpaces - 1
    val length = s.length
    val lastIndex = s.lastIndex

    val minCost = allFills(startingLastEMIndex).flatMap { startingFill ->
        allFills(endingLastEMIndex).map { endingFill ->
            val psArray = s.toCharArray()
            for (i in 0..startingLastEMIndex)
                psArray[i] = startingFill(i)
            if (numStartingEmptySpaces < length) {
                for (i in 0..endingLastEMIndex)
                    psArray[lastIndex - i] = endingFill(i)

                val middlePs = psArray.asList().subList(numStartingEmptySpaces, length - numEndingEmptySpaces)
                for (ces in getContinuousEmptySpacesInTheMiddle(middlePs)) {
                    val middleFill = if (x + y >= 0)
                        if (ces.leftPattern == 'C') ::fillC
                        else ::fillJ
                    else
                        if (ces.leftPattern == 'C') ::fillJC
                        else ::fillCJ

                    for (i in 0 until ces.length)
                        psArray[ces.from + i] = middleFill(i)
                }
            }

            computeCost(psArray.asList(), x, y)
        }
    }.minOrNull()!!

    println("Case #${ti + 1}: $minCost")
}


val cjList = "CJ".toList()
val jcList = "JC".toList()
fun computeCost(ps: List<Char>, x: Int, y: Int) =
    ps.asSequence().windowed(2) {
        when {
            it == cjList -> x
            it == jcList -> y
            //it.contains('?') -> throw IllegalArgumentException(ps.joinToString(""))
            else -> 0
        }
    }.sum()


class ContinuousEmptySpaces(
    val length: Int, val from: Int,
    val leftPattern: Char,
    val rightPattern: Char
)

// "?" can't be on both ends.
fun getContinuousEmptySpacesInTheMiddle(ps: List<Char>): List<ContinuousEmptySpaces> {
    val result = ArrayList<ContinuousEmptySpaces>()
    val size = ps.size
    var cjStartingIndex = 0
    while (true) {
        val cjRemainingPs = ps.subList(cjStartingIndex, size)
        val firstEmptyRelativeIndex = cjRemainingPs.indexOfFirst { it == '?' }
        if (firstEmptyRelativeIndex == -1) break
        val emptyStartingIndex = cjStartingIndex + firstEmptyRelativeIndex
        val length = ps.subList(emptyStartingIndex, size).asSequence().takeWhile { it == '?' }.count()

        cjStartingIndex = emptyStartingIndex + length
        result.add(
            ContinuousEmptySpaces(
                length, emptyStartingIndex,
                ps[emptyStartingIndex - 1], ps[cjStartingIndex]
            )
        )
    }

    return result
}

typealias FillStartOrEndFunction = (index: Int) -> Char

fun fillC(index: Int): Char = 'C'
fun fillJ(index: Int): Char = 'J'
fun fillCJ(index: Int): Char = if (index % 2 == 0) 'C' else 'J'
fun fillJC(index: Int): Char = if (index % 2 == 0) 'J' else 'C'
fun fillCJJ(lastIndex: Int): FillStartOrEndFunction = { index ->
    if (index == lastIndex) {
        'J'
    } else fillCJ(index)
}

fun fillJCC(lastIndex: Int): FillStartOrEndFunction = { index ->
    if (index == lastIndex) {
        'C'
    } else fillJC(index)
}

fun allFills(lastIndex: Int) =
    listOf(::fillC, ::fillJ, ::fillCJ, ::fillJC, fillCJJ(lastIndex), fillJCC(lastIndex))
