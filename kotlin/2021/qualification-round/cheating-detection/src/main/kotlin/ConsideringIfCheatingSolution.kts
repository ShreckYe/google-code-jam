import kotlin.math.exp
import kotlin.math.ln

fun main() {
    val t = readLine()!!.toInt()
    val p = readLine()!!.toInt()

    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    val results = List(100) {
        readLine()!!.map { it - '0' }
    }

    // simple estimation by averaging
    val ss = results.map {
        val estimatedP = it.average()
        val ifCheatingEstimatedP = ((estimatedP - 0.5) * 2).coerceAtLeast(0.0)
        inverseSigmoid(estimatedP).coerceIn(-3.0, 3.0) to
                inverseSigmoid(ifCheatingEstimatedP).coerceIn(-3.0, 3.0)
    }
    //println(ss.withIndex().toList().joinToString("\n"))
    val qs = (0 until 10000).map { j ->
        inverseSigmoid(results.asSequence().map { it[j] }.average()).coerceIn(-3.0, 3.0)
    }

    // logarithms of player probabilities
    val lnIfCheatingPDivPs = (ss zip results).asSequence().map { (s, rs) ->
        (rs.asSequence() zip qs.asSequence()).map { (r, q) ->
            val pCorrect = sigmoid(s.first - q)
            val ifCheatingPCorrect = 0.5 + 0.5 * sigmoid(s.second - q)
            val (p, ifCheatingP) = when (r) {
                1 -> pCorrect to ifCheatingPCorrect
                0 -> 1 - pCorrect to 1 - ifCheatingPCorrect
                else -> throw IllegalArgumentException(r.toString())
            }
            ln(ifCheatingP / p)
        }.sum()
    }
    //println(lnIfCheatingPDivPs.withIndex().toList().joinToString("\n"))

    val y = lnIfCheatingPDivPs.withIndex().maxByOrNull { it.value }!!.index

    println("Case #${ti + 1}: ${y + 1}")
}

fun sigmoid(x: Double) =
    1 / (1 + exp(-x))

fun inverseSigmoid(y: Double) =
    ln(y / (1 - y))

fun Double.squared() =
    this * this