import java.math.BigInteger

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