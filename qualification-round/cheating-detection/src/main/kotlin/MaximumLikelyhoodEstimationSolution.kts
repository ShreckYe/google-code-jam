import kotlin.math.exp

fun main() {
    val t = readLine()!!.toInt()
    val p = readLine()!!.toInt()

    repeat(t, ::testCase)
}

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

    TODO("The equation seems to be not analytically solvable.")
}

fun sigmoid(x: Double) =
    1 / (1 + exp(-x))

/*
$$
P = \prod_{i = 0, 1, ..., 99, j = 0, 1, ..., 9999}
\begin{cases}
f(S_i - Q_j), & A_{ij} = 1 \\
1 - f(S_i - Q_j), & A_{ij} = 0
\end{cases}
$$

$$
\max_{S_i (i = 0, 1, ..., 99), Q_j (j = 0, 1, ..., 9999)} P
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
\forall i: \frac{\partial{P}}{\partial{S_i}} = \sum_{A_{ij} = 1} h'(S_i - Q_j) - \sum_{A_{ij} = 0} g'(S_i - Q_j) = 0, \\
\forall j: \frac{\partial{P}}{\partial{Q_j}} = - \sum_{A_{ij} = 1} i'(S_i - Q_j) + \sum_{A_{ij} = 0} i'(S_i - Q_j) = 0
$$
 */

fun derivativeOfLnSigmoid(x: Double) =
    1 - sigmoid(x)

fun derivativeOfLn1MinusSigmoid(x: Double) =
    -sigmoid(x)