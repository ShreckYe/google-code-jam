import kotlin.math.exp
import kotlin.math.ln

fun main() {
    val t = readLine()!!.toInt()
    val p = readLine()!!.toInt()

    repeat(t, ::testCase)
}

val maxAlpha = 1
val minAlpha = 1e-2
fun testCase(ti: Int) {
    val results = List(100) {
        readLine()!!.map {
            when (it) {
                '1' -> true
                '0' -> false
                else -> throw IllegalArgumentException()
            }
        }
    }

    // gradient ascend

    fun mleLnP(ss: DoubleArray, qs: DoubleArray, cheatingPlayerIndex: Int): Double =
        ss.flatMapIndexed { i, s ->
            qs.mapIndexed { j, q ->
                val isNotCheating = i != cheatingPlayerIndex
                val sigmoidSMinusQ = sigmoid(s - q)
                ln(
                    if (results[i][j])
                        if (isNotCheating) sigmoidSMinusQ
                        else (1 + sigmoidSMinusQ) / 2
                    else
                        if (isNotCheating) 1 - sigmoidSMinusQ
                        else (1 - sigmoidSMinusQ) / 2
                )
            }
        }.sum()

    fun mleLnPPartialDerivatives(
        ss: DoubleArray,
        qs: DoubleArray,
        cheatingPlayerIndex: Int
    ): Pair<List<Double>, List<Double>> {
        fun ijDerative(s: Double, q: Double, result: Boolean, isNotCheating: Boolean): Double {
            val sigmoidSMinusQ = sigmoid(s - q)
            return if (isNotCheating)
                if (result) 1 - sigmoidSMinusQ
                else -sigmoidSMinusQ
            else
                if (result) 1 - sigmoidSMinusQ.squared() / (1 + sigmoidSMinusQ)
                else 1 + sigmoidSMinusQ.squared() / (1 - sigmoidSMinusQ)
        }

        val cachedIjDeravative = ss.mapIndexed { i, s ->
            val isNotCheating = i != cheatingPlayerIndex
            qs.mapIndexed { j, q ->
                ijDerative(s, q, results[i][j], isNotCheating)
            }.toDoubleArray()
        }.toTypedArray()

        val pdss = ss.indices.map { i -> cachedIjDeravative[i].sum() }
        val pdqs = qs.indices.map { j -> -ss.indices.map { i -> cachedIjDeravative[i][j] }.sum() }
        return pdss to pdqs
    }

    val likelyhoods = (0 until 100).asSequence().map { cheatingPlayerIndex ->
        var ss = DoubleArray(100) { 0.0 }
        var qs = DoubleArray(10000) { 0.0 }

        var lnP = mleLnP(ss, qs, cheatingPlayerIndex)
        var alpha = maxAlpha
        while (true) {
            val (dss, dqs) = mleLnPPartialDerivatives(ss, qs, cheatingPlayerIndex)

            fun Double.coerceInRange() = coerceIn(-3.0, 3.0)
            val newSs = DoubleArray(100) { i -> (ss[i] + alpha * dss[i]).coerceInRange() }
            val newQs = DoubleArray(10000) { j -> (qs[j] + alpha * dqs[j]).coerceInRange() }
            val newLnP = mleLnP(ss, qs, cheatingPlayerIndex)

            /*println(cheatingPlayerIndex)
            println(lnP)
            println(alpha)
            println(ss.take(10))
            println(dss.take(10))
            println(qs.take(10))
            println(dqs.take(10))
            println(lnP)
            println()*/
            if (newLnP > lnP) {
                ss = newSs
                qs = newQs
                lnP = newLnP
            } else {
                alpha /= 2
                if (alpha < minAlpha)
                    break
            }
        }
        lnP
    }

    val y = likelyhoods.withIndex().maxByOrNull { it.value }!!.index

    println("Case #${ti + 1}: ${y + 1}")
}

fun sigmoid(x: Double) =
    1 / (1 + exp(-x))

fun Double.squared() = this * this
/*
\paragraph{If no player is cheating}
$$
P_{ij} = P (X_{ij} = a_{ij}) =
\begin{cases}
f(S_i - Q_j), & a_{ij} = 1 \\
1 - f(S_i - Q_j), & a_{ij} = 0
\end{cases}, \\
P = \prod_{i = 0, 1, ..., 99, j = 0, 1, ..., 9999} P_{ij}, \\
\max_{S_i (i = 0, 1, ..., 99), Q_j (j = 0, 1, ..., 9999)} P, \\
\ln P = \sum_{i = 0, 1, ..., 99, j = 0, 1, ..., 9999} \ln P_{ij},
$$

Let
$$
g(x) = 1 - f(x), \\
h(x) = \ln f(x), \\
i(x) = \ln g(x), \\
h'(x) = 1 - f(x), \\
i'(x) = - f(x)
$$

$$
\forall i: \frac{\partial{P}}{\partial{S_i}} = \sum_{A_{ij} = 1} h'(S_i - Q_j) + \sum_{A_{ij} = 0} i'(S_i - Q_j), \\
\forall j: \frac{\partial{P}}{\partial{Q_j}} = - (\sum_{A_{ij} = 1} h'(S_i - Q_j) + \sum_{A_{ij} = 0} i'(S_i - Q_j))
$$

\paragraph{If the $k$-th player is cheating}
$$
P_{kj} =
\begin{cases}
\frac{1}{2} + \frac{1}{2} f(S_k - Q_j), & a_{kj} = 1 \\
\frac{1}{2} - \frac{1}{2} f(S_k - Q_j), & a_{kj} = 0
\end{cases}
$$

$$
f_c(x) = \frac{1}{2} + \frac{1}{2} f(x) = \frac{1 + f(x)}{2}, \\
g_c(x) = \frac{1}{2} - \frac{1}{2} f(x) = \frac{1 - f(x)}{2}, \\
h_c(x) = \ln f_c(x) = \ln (1 + f(x)) - \ln 2, \\
i_c(x) = \ln g_c(x) = \ln (1 - f(x)) - \ln 2, \\
h_c'(x) = \frac{1 + f(x) (1 - f(x)}{1 + f(x)} = 1 - \frac{f(x)^2}{1 + f(x)}, \\
i_c'(x) = \frac{1 - f(x)(1 - f(x)}{1 - f(x)} = 1 + \frac{f(x)^2}{1 - f(x)}
$$
 */


main()