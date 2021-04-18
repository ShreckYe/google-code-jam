import kotlin.math.E
import kotlin.math.exp
import kotlin.math.pow

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
            val factorPns = decompose(sumEqProduct, maxPrime)
            if (factorPns !== null) {
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

fun decompose(number: Long, maxPrime: Int): List<Pn>? {
    require(number >= 1)
    val pns = ArrayList<Pn>(maxPrime)
    var number = number
    for (p in 2..maxPrime) {
        if (number == 1L) break
        var n = 0L
        while (number % p == 0L) {
            number /= p
            n++
        }
        pns.add(Pn(p, n))
    }
    if (number == 1L) return pns

    return null
}