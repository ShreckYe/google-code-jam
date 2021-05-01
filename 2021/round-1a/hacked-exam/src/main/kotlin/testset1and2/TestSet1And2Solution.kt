package testset1and2

import testset1and2.Fraction.Companion.one
import java.math.BigInteger

fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    val (n, q) = readLine()!!.splitToSequence(' ').map { it.toInt() }.toList()
    val ass = List(n) {
        val lineInputs = readLine()!!.splitToSequence(' ').toList()
        lineInputs[0].map { it == 'T' } to lineInputs[1].toInt()
    }

    val answers: List<Boolean>
    val expectedScore: Fraction
    when (n) {
        1 -> {
            val (a0, s0) = ass[0]
            if (s0 divBy q >= half) {
                answers = a0
                expectedScore = s0.toFraction()
            } else {
                answers = a0.map { !it }
                expectedScore = (q - s0).toFraction()
            }
        }
        2 -> {
            val (a0, s0) = ass[0]
            val (a1, s1) = ass[1]
            val (sameAs, differentAs) = (a0 zip a1).withIndex()
                .partition { it.value.first == it.value.second }
            val numStudent0CorrectInDifferentAs = (differentAs.size + s0 - s1) / 2
            val numStudent0CorrectInSameAs = s0 - numStudent0CorrectInDifferentAs

            val trueProbabilities = Array<Fraction?>(q) { null }.also {
                val numSameAs = sameAs.size
                for ((i, a01) in sameAs) {
                    val correctProbability = numStudent0CorrectInSameAs divBy numSameAs
                    it[i] = if (a01.first) correctProbability else one - correctProbability
                }
                val numDifferentAs = differentAs.size
                for ((i, a01) in differentAs) {
                    val correctProbability = numStudent0CorrectInDifferentAs divBy numDifferentAs
                    it[i] = if (a01.first) correctProbability else one - correctProbability
                }
            } as Array<Fraction>

            val answerAneCorrectProbabilities =
                trueProbabilities.map { if (it >= half) true to it else false to (one - it) }
            answers = answerAneCorrectProbabilities.map { it.first }
            expectedScore = answerAneCorrectProbabilities.asSequence().map { it.second }.reduce(Fraction::plus)
        }
        else -> throw IllegalArgumentException()
    }

    println("Case #${ti + 1}: ${answers.joinToString("") { if (it) "T" else "F" }} $expectedScore")
}

val half = 1 divBy 2


// copied from Fractions.kt
data class Fraction internal constructor(val numerator: BigInteger, val denominator: BigInteger) {
    init {
        require(denominator > BigInteger.ZERO)
    }

    operator fun plus(that: Fraction) =
        reducedFraction(
            numerator * that.denominator + that.numerator * denominator,
            denominator * that.denominator
        )

    operator fun unaryMinus() =
        Fraction(-numerator, denominator)

    operator fun minus(that: Fraction) =
        this + -that

    operator fun times(that: Fraction) =
        reducedFraction(numerator * that.numerator, denominator * that.denominator)

    fun reciprocal() =
        if (numerator > BigInteger.ZERO) Fraction(denominator, numerator)
        else Fraction(-denominator, -numerator)

    operator fun div(that: Fraction) =
        this * that.reciprocal()

    operator fun compareTo(that: Fraction) =
        (numerator * that.denominator).compareTo(that.numerator * denominator)

    override fun toString(): String =
        "$numerator/$denominator"

    fun toSimplestString(): String =
        if (denominator == BigInteger.ONE) numerator.toString()
        else toString()

    companion object {
        val zero = Fraction(BigInteger.ZERO, BigInteger.ONE)
        val one = Fraction(BigInteger.ONE, BigInteger.ONE)
    }
}

fun reducedFraction(numerator: BigInteger, denominator: BigInteger): Fraction {
    val gcd = numerator.gcd(denominator)
    val nDivGcd = numerator / gcd
    val dDivGcd = denominator / gcd
    return if (denominator > BigInteger.ZERO) Fraction(nDivGcd, dDivGcd)
    else Fraction(-nDivGcd, -dDivGcd)
}

infix fun BigInteger.divBy(that: BigInteger) = reducedFraction(this, that)
infix fun Int.divBy(that: Int) = toBigInteger() divBy that.toBigInteger()
infix fun Long.divBy(that: Long) = toBigInteger() divBy that.toBigInteger()

fun BigInteger.toFraction() =
    Fraction(this, BigInteger.ONE)

fun Int.toFraction() = toBigInteger().toFraction()
fun Long.toFraction() = toBigInteger().toFraction()