import kotlin.math.max

fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

fun NonnegDecimalInteger.appendToLsdToBeGeSeq(that: NonnegDecimalInteger): Sequence<Byte> =
    when {
        isZero() -> that.asSequence()
        that.isZero() -> asSequence()
        else -> {
            val msd = msd()
            val thatMsd = that.msd()

            when {
                msd > thatMsd -> generateSequence { zeroByte }.take(that.size - size) + asSequence()
                msd < thatMsd -> generateSequence { zeroByte }.take(that.size - size + 1) + asSequence()
                else -> subList(0, size - 1).appendToLsdToBeGeSeq(that.subList(0, that.size - 1)) + msd
            }
        }
    }

fun NonnegDecimalInteger.appendToLsdToBeGe(that: NonnegDecimalInteger): NonnegDecimalInteger =
    appendToLsdToBeGeSeq(that).toList()


fun testCase(ti: Int) {
    val n = readLine()!!.toInt()
    val xs = readLine()!!.splitToSequence(' ').map { it.readableStringToNonnegDecimalInteger() }.toList()

    var lastNumber = zero
    var minNumSDA = 0
    for (x in xs) {
        if (x > lastNumber) {
            lastNumber = x
        } else {
            val newNumber = x.appendToLsdToBeGe(lastNumber.plusOne())
            minNumSDA += newNumber.size - x.size
            lastNumber = newNumber
        }
    }

    println("Case #${ti + 1}: $minNumSDA")
}



// no leading zeros
typealias NonnegDecimalInteger = List<Byte>


fun NonnegDecimalInteger.msd() = last()
fun NonnegDecimalInteger.isZero() = isEmpty()


val zero = byteArrayOf().asList()
val one = byteArrayOf(1).asList()

const val zeroByte: Byte = 0


fun String.contentStringToNonnegDecimalInteger() = map { (it - '0').toByte().also { require(it in 0..9) } }
fun String.readableStringToNonnegDecimalInteger(): NonnegDecimalInteger {
    require(isNotEmpty())
    return if (this == "0") zero
    else reversed().contentStringToNonnegDecimalInteger()
}

infix operator fun NonnegDecimalInteger.compareTo(that: NonnegDecimalInteger): Int {
    val size = size
    val thatSize = that.size
    return if (size != thatSize) size.compareTo(thatSize)
    else compareToWithSameSize(that)
}

infix fun NonnegDecimalInteger.compareToWithSameSize(that: NonnegDecimalInteger): Int {
    val size = size
    return if (size == 0) 0
    else {
        // msd: most significant digit
        val msdCompareResult = last().compareTo(that.last())
        if (msdCompareResult != 0) msdCompareResult
        else subList(0, size - 1) compareToWithSameSize (that.subList(0, size - 1))
    }
}

operator fun NonnegDecimalInteger.plus(that: NonnegDecimalInteger): NonnegDecimalInteger {
    // imperative implementation
    val maxSize = max(size, that.size)
    val capacity = maxSize + 1
    val sum = ByteArray(capacity)
    var carry = false
    for (i in 0 until maxSize) {
        val digitSum = this.getOrElse(i) { 0 } + that.getOrElse(i) { 0 } + if (carry) 1 else 0
        if (digitSum < 10) {
            sum[i] = digitSum.toByte()
            carry = false
        } else {
            sum[i] = (digitSum - 10).toByte()
            carry = true
        }
    }

    @Suppress("LiftReturnOrAssignment")
    if (carry) {
        sum[maxSize] = 1
        return sum.asList()
    } else
        return sum.asList().subList(0, maxSize)
}

fun NonnegDecimalInteger.plusOne() =
    plus(one)