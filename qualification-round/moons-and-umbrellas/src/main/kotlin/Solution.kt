import kotlin.math.min

fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

const val SAFE_POS_INF = Int.MAX_VALUE / 2

fun testCase(ti: Int) {
    val lineInputs = readLine()!!.split(' ')
    val x = lineInputs[0].toInt()
    val y = lineInputs[1].toInt()
    val s = lineInputs[2]

    // dynamic programming
    var minC = 0
    var minJ = 0
    for (pattern in s) {
        val oldMinC = minC
        val oldMinJ = minJ
        fun newMinC() = min(oldMinC, oldMinJ + y)
        fun newMinJ() = min(oldMinJ, oldMinC + x)
        when (pattern) {
            '?' -> {
                minC = newMinC()
                minJ = newMinJ()
            }
            'C' -> {
                minC = newMinC()
                minJ = SAFE_POS_INF
            }
            'J' -> {
                minC = SAFE_POS_INF
                minJ = newMinJ()
            }
            else -> throw IllegalArgumentException()
        }
    }

    val minCost = min(minC, minJ)

    println("Case #${ti + 1}: $minCost")
}