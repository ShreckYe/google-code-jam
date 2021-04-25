import java.math.BigInteger

fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    val (a, b, c) = readLine()!!.splitToSequence(' ').map { it.toLong() }.toList()

    val ans = ans(a, b, c)

    with(ans) {
        println("Case #${ti + 1}: $h $m $s $n")
    }
}

data class Time(val h: Long, val m: Long, val s: Long, val n: Long)

fun ans(a: Long, b: Long, c: Long) =
    listOf(
        listOf(a, b, c), listOf(a, c, b),
        listOf(b, a, c), listOf(b, c, a),
        listOf(c, a, b), listOf(c, b, a)
    ).asSequence()
        .mapNotNull { (hnt, mnt, snt) ->
            ansHMSNT(hnt, mnt, snt)
        }.first()

// nt = num ticks
fun ansHMSNT(hnt: Long, mnt: Long, snt: Long): Time? {
    val nsAfterHMOverlap = (mnt - hnt).toPosNumTicks().toFraction() / (mSpeedInNs - hSpeedInNs).toFraction()
    val nsAfterMSOverlap = (snt - mnt).toPosNumTicks().toFraction() / (sSpeedInNs - mSpeedInNs).toFraction()

    val allInNs = hmOverlapTimesInNs.asSequence()
        .map { (it + nsAfterHMOverlap).toIntegerOrNull()?.toLong() }
        .find { allInNs ->
            allInNs !== null && msOverlapTimesInNs.any {
                val nsAfterH = (it + nsAfterMSOverlap).toIntegerOrNull()?.toLong()
                nsAfterH !== null && (allInNs % hInNs) == nsAfterH
            }
        }

    return if (allInNs != null) allInNsToTime(allInNs) else null
}

fun allInNsToTime(allInNs: Long): Time {
    var r: Long
    val n = allInNs % sInNs
    r = allInNs / sInNs
    val s = r % mInS
    r /= mInS
    val m = r % hInM
    r /= hInM
    val h = r
    return Time(h, m, s, n)
}


const val hSpeedInNs = 1L
const val mSpeedInNs = 12L
const val sSpeedInNs = 720L

infix fun Long.intDiv(that: Long): Long? =
    if (this % that != 0L) null
    else this / that

const val numCircleTicks = 360 * 12 * 10_000_000_000L

fun Long.toPosNumTicks(): Long =
    if (this >= 0) this
    else this + numCircleTicks

val sInNs = 1_000_000_000L
val mInS = 60L
val hInM = 60L
val hInNs = hInM * mInS * sInNs

val hmOverlapGapNumTicks = numCircleTicks divBy 11
val hmOverlapTimesInNs = List(11) { hmOverlapGapNumTicks * it.toFraction() / hSpeedInNs.toFraction() }
val msOverlapGapNumTicks = numCircleTicks divBy 59
val msOverlapTimesInNs = List(59) { msOverlapGapNumTicks * it.toFraction() / mSpeedInNs.toFraction() }


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

    fun inv() =
        if (numerator > BigInteger.ZERO) Fraction(denominator, numerator)
        else Fraction(-denominator, -numerator)

    operator fun div(that: Fraction) =
        this * that.inv()

    operator fun compareTo(that: Fraction) =
        (numerator * that.denominator).compareTo(that.numerator * denominator)

    override fun toString(): String =
        "$numerator/$denominator"

    fun toSimplestString(): String =
        if (denominator == BigInteger.ONE) numerator.toString()
        else toString()

    fun toIntegerOrNull(): BigInteger? =
        if (denominator == BigInteger.ONE) numerator
        else null

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