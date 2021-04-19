import kotlin.math.*

fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    val m = readLine()!!.toInt()
    val pns = List(m) {
        val lineInputs = readLine()!!.split(' ')
        Pn(lineInputs[0].toInt(), lineInputs[1].toLong())
    }

    val y = ans(pns)
    println("Case #${ti + 1}: $y")
}

fun ans(pns: List<Pn>): Long {
    val upperBound = pns.sum()
    val minProductGroupSum = (0..upperBound)
        .first { exp(it.toDouble() / E) >= upperBound - it }
    val maxProductGroupSum = (minProductGroupSum..upperBound)
        .last { maxPrime.toDouble().pow(it.toDouble() / maxPrime) <= upperBound - it }

    val pnMap = LongArray(maxPrime + 1)
    pns.forEach { pnMap[it.p] = it.n }

    return (minProductGroupSum..maxProductGroupSum).asSequence()
        .map { upperBound - it }
        .firstOrNull { sumEqProduct ->
            val (factorPns, remaining) = factorizeWithFactors(sumEqProduct, primesToMaxPrime)
            if (remaining == 1L) {
                val sumGroupPnMap = pnMap.copyOf().also {
                    for ((p, n) in factorPns) {
                        val rn = it[p] - n
                        if (rn >= 0)
                            it[p] = rn
                        else
                            return@firstOrNull false
                    }
                }
                val sumGroupPns =
                    sumGroupPnMap.asSequence().withIndex().map { Pn(it.index, it.value) }.filter { it.n > 0 }
                sumGroupPns.sum() == sumEqProduct
            } else false
        } ?: 0
}

data class Pn(val p: Int, val n: Long)

fun List<Pn>.sum() = sumOf { it.p * it.n }
fun Sequence<Pn>.sum() = sumOf { it.p * it.n }
const val maxPrime = 499
val primesToMaxPrime = primesToWithSOE(maxPrime)



// copied from NumberTheory.kt

// see: https://en.wikipedia.org/wiki/Generation_of_primes#Complexity and https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes
// see: https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes#Pseudocode
// O(n log(log(n)))
fun primesToWithSOE(n: Int): List<Int> {
    // BooleanArray is slightly faster than BitSet
    val a = BooleanArray(n + 1) { true }
    for (i in 2..sqrt(n.toDouble()).toInt())
        if (a[i]) {
            var j = i.squared()
            while (j <= n) {
                a[j] = false
                j += i
            }
        }
    return (2..n).filter { a[it] }
}


data class FactorAndNum(val factor: Int, val num: Int)
data class FactorResult<R, N>(val result: R, val remaining: N)

// O(log(factor, n)) = O(log(n))
fun countFactors(n: Long, factor: Int): FactorResult<Int, Long> {
    require(n > 0 && factor > 1)
    return if (n % factor != 0L) FactorResult(0, n)
    else {
        val (nNum, remaining) = countFactors(n / factor, factor)
        FactorResult(nNum + 1, remaining)
    }
}

// O(max(primes.size, log(n)))
fun factorizeWithFactors(n: Long, factors: List<Int>): FactorResult<List<FactorAndNum>, Long> {
    @Suppress("NAME_SHADOWING")
    var n = n
    val pns = ArrayList<FactorAndNum>(factors.size)
    for (p in factors) {
        val (num, remainingN) = countFactors(n, p)
        if (num > 0) {
            pns.add(FactorAndNum(p, num))
            n = remainingN
        }
    }
    pns.trimToSize()
    return FactorResult(pns, n)
}



// copied from MathExtensions.kt

fun Int.squared(): Int =
    this * this