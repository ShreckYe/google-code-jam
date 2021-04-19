import kotlin.math.ln
import kotlin.math.sqrt

fun simplePrimesTo(n: Int): List<Int> =
    (2..n).filter { i -> (2 until i).all { i % it != 0 } }

val xLnXConst = 30 * ln(113.toDouble()) / 113
fun piXUpperBound(x: Int): Int = run {
    require(x >= 2)
    // see: https://en.wikipedia.org/wiki/Prime-counting_function#Inequalities
    (xLnXConst * x / ln(x.toDouble())).toInt()
}

// check with already computed primes
fun primesToWithCheckWithAlreadyComputedPrimes(n: Int): List<Int> =
    ArrayList<Int>(piXUpperBound(n)).apply {
        for (i in 2..n) {
            val sqrtN = sqrt(i.toDouble()).toInt()
            if (asSequence().takeWhile { it <= sqrtN }.none { i % it == 0 })
                add(i)
        }
        trimToSize()
    }

fun lazyPrimes(): List<Int> =
    TODO()

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

/*inline class PrimeAndNum(val factorAndNum: FactorAndNum) {
    constructor(prime: Int, num: Int) : this(FactorAndNum(prime, num))

    val prime inline get() = factorAndNum.factor
    val num inline get() = factorAndNum.num
}*/
//typealias PrimeAndNum = FactorAndNum

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

// O(sqrt(n) log(log(n)))
fun factorizePrimes(n: Long): List<FactorAndNum> {
    require(n <= Int.MAX_VALUE.toLong().squared())
    // TODO: maybe use lazy primes here when implemented
    val (pns, remaining) = factorizeWithFactors(n, primesToWithSOE(sqrt(n.toDouble()).toInt()))
    return if (remaining == 1L) pns
    else pns + FactorAndNum(remaining.toInt(), 1)
}

fun recomposeFactors(factorAndNums: List<FactorAndNum>): Long =
    factorAndNums.asSequence()
        .map { it.factor.toLong() powInt it.num }
        .reduceOrNull { a, b -> a * b } ?: 1L

fun recomposeFactors(factorizationResult: FactorResult<List<FactorAndNum>, Long>): Long =
    recomposeFactors(factorizationResult.result) * factorizationResult.remaining