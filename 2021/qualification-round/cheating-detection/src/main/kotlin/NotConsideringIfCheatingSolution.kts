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
    val ss = results.map { inverseSigmoid(it.average()).coerceIn(-3.0, 3.0) }
    val qs = (0 until 10000).map { j ->
        inverseSigmoid(results.asSequence().map { it[j] }.average()).coerceIn(-3.0, 3.0)
    }

    // logarithms of player probabilities
    val lnPs = (ss zip results).asSequence().map { (s, rs) ->
        (rs.asSequence() zip qs.asSequence()).map { (r, q) ->
            val pCorrect = sigmoid(s - q)
            val p = when (r) {
                1 -> pCorrect; 0 -> 1 - pCorrect; else -> throw IllegalArgumentException(r.toString())
            }
            ln(p)
        }.sum()
    }
    //println(lnPs.withIndex().toList().joinToString("\n"))

    val y = lnPs.withIndex()./*min*/maxByOrNull { it.value }!!.index

    /*// squared deviations
    val sds = (ss zip results).asSequence().map { (s, rs) ->
        (rs.asSequence() zip qs.asSequence()).map { (r, q) ->
            val pCorrect = sigmoid(s - q)
            (pCorrect - r).squared()
        }.sum()
    }
    //println(sds.withIndex().toList().joinToString("\n"))

    val y = sds.withIndex().*//*max*//*minByOrNull { it.value }!!.index*/

    println("Case #${ti + 1}: ${y + 1}")
}

fun sigmoid(x: Double) =
    1 / (1 + exp(-x))

fun inverseSigmoid(y: Double) =
    ln(y / (1 - y))

fun Double.squared() =
    this * this