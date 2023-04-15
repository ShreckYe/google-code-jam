fun main() {
    val tt = readln().toInt()
    repeat(tt, ::testCase)
}

fun testCase(tti: Int) {
    val nn = readln().toInt()
    val sss = readln().splitToSequence(' ').map { it.toInt() }.toList()

    //val colorIntegers = arrayOfNulls<Int>(10001)
    val colorIntegerMap = mutableMapOf<Int, Int>()
    var nextInteger = 0
    var possible = true
    for (ss in sss) {
        val colorInteger = colorIntegerMap[ss]
        if (colorInteger == null) {
            colorIntegerMap[ss] = nextInteger++
        } else {
            if (colorInteger < nextInteger - 1) {
                possible = false
                break
            }
        }
    }

    /*
    val colorsInOrder =
        colorIntegers.asSequence().withIndex().filter { it.value !== null }.sortedBy { it.value }.map { it.index }
    */
    val colorsInOrder =
        colorIntegerMap.entries.asSequence().sortedBy { it.value }.map { it.key }.toList()

    println("Case #${tti + 1}: ${if (possible) colorsInOrder.joinToString(" ") else "IMPOSSIBLE"}")
}
