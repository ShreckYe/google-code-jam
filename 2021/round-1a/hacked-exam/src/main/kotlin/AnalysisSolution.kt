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

    val qTypeAndNums = (0 until q).map { qi ->
        ass.asSequence()
            .map { it.first[qi] } // get all students' answers to Question qi
            .mapIndexed { si, a -> (if (a) 1 else 0) shl si }.reduce { a, b -> a or b } // convert to its type in Int
    }.groupBy { it }

    val coefficients = ass.map { it.second }

    val answers = TODO() as List<Boolean>
    val expectedScore = TODO()
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