import kotlin.math.absoluteValue
import kotlin.math.exp
import kotlin.math.ln

fun main() {
    val t = readLine()!!.toInt()
    val p = readLine()!!.toInt()

    repeat(t, ::testCase)
}

val numHalfExtremeQuestions = 500
fun testCase(ti: Int) {
    val results = List(100) {
        readLine()!!.map { it - '0' }
    }

    // simple estimation by averaging
    val ss = results.map { inverseSigmoid(it.average()).coerceIn(-3.0, 3.0) }
    val qs = (0 until 10000).map { j ->
        inverseSigmoid(results.asSequence().map { it[j] }.average()).coerceIn(-3.0, 3.0)
    }

    val sortedIndexedQs = qs.asSequence().withIndex().sortedByDescending { it.index }.toList()
    val extremeIndexedQs =
        sortedIndexedQs.take(numHalfExtremeQuestions) + sortedIndexedQs.takeLast(numHalfExtremeQuestions)
    val extremeQs = extremeIndexedQs.map { it.value }
    val extremeRss = results.map { rs -> extremeIndexedQs.map { rs[it.index] } }

    // the difference between expected number of correct answers and actual
    val diffs = (ss zip extremeRss).asSequence().map { (s, extremeRs) ->
        assert(extremeRs.size == extremeQs.size)

        (extremeRs zip extremeQs).sumOf { (r, q) -> sigmoid(s - q) - r }.absoluteValue
        //(extremeRs zip extremeQs).sumOf { (r, q) -> (sigmoid(s - q).also(::println) - r.also(::println)).also { println(it);println() } }.absoluteValue
    }
    println(diffs.withIndex().joinToString("\n"))

    val y = diffs.withIndex().maxByOrNull { it.value }!!.index

    println("Case #${ti + 1}: ${y + 1}")
}

fun sigmoid(x: Double) =
    1 / (1 + exp(-x))

fun inverseSigmoid(y: Double) =
    ln(y / (1 - y))