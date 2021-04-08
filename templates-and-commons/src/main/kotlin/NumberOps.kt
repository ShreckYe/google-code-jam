@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

import java.math.BigInteger

interface NumberOps<T> {
    operator fun T.plus(that: T): T
    operator fun T.minus(that: T): T
    operator fun T.times(that: T): T
    operator fun T.div(that: T): T
}

object IntOps : NumberOps<Int> {
    override fun Int.plus(that: Int): Int = plus(that)
    override fun Int.minus(that: Int): Int = minus(that)
    override fun Int.times(that: Int): Int = times(that)
    override fun Int.div(that: Int): Int = div(that)
}

object LongOps : NumberOps<Long> {
    override fun Long.plus(that: Long): Long = plus(that)
    override fun Long.minus(that: Long): Long = minus(that)
    override fun Long.times(that: Long): Long = times(that)
    override fun Long.div(that: Long): Long = div(that)
}

object FloatOps : NumberOps<Float> {
    override fun Float.plus(that: Float): Float = plus(that)
    override fun Float.minus(that: Float): Float = minus(that)
    override fun Float.times(that: Float): Float = times(that)
    override fun Float.div(that: Float): Float = div(that)
}

object DoubleOps : NumberOps<Double> {
    override fun Double.plus(that: Double): Double = plus(that)
    override fun Double.minus(that: Double): Double = minus(that)
    override fun Double.times(that: Double): Double = times(that)
    override fun Double.div(that: Double): Double = div(that)
}

object BigIntegerOps : NumberOps<BigInteger> {
    override fun BigInteger.plus(that: BigInteger): BigInteger = plus(that)
    override fun BigInteger.minus(that: BigInteger): BigInteger = minus(that)
    override fun BigInteger.times(that: BigInteger): BigInteger = times(that)
    override fun BigInteger.div(that: BigInteger): BigInteger = div(that)
}


interface MoreNumberOps<T> : NumberOps<T> {
    val zero: T
    val one: T


    operator fun T.unaryMinus(): T =
        zero - this

    fun T.inv(): T =
        one / this

    fun T.squared(): T =
        this * this

    infix fun T.powInt(that: Int): T =
        when {
            that == 0 -> one
            that > 0 -> powInt(that / 2).squared()
                .let { if (that % 2 == 0) it else it * this }
            else -> inv() powInt (-that)
        }
}