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
    var minC: Int
    var minJ: Int
    when (s.first()) {
        '?' -> {
            minC = 0
            minJ = 0
        }
        'C' -> {
            minC = 0
            minJ = SAFE_POS_INF
        }
        'J' -> {
            minC = SAFE_POS_INF
            minJ = 0
        }
        else -> throw IllegalArgumentException()
    }

    for (pattern in s.drop(1)) {
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