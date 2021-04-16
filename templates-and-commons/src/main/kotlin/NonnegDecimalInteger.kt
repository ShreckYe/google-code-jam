import java.math.BigInteger
import kotlin.math.max

// no leading zeros
typealias NonnegDecimalInteger = List<Byte>
typealias CompactArrayNonnegDecimalInteger = ByteArray


fun NonnegDecimalInteger.toCompactArray(): CompactArrayNonnegDecimalInteger =
    toByteArray()

fun NonnegDecimalInteger.toCompact(): NonnegDecimalInteger =
    toCompactArray().asList()

fun NonnegDecimalInteger.lsd() = first()
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

fun NonnegDecimalInteger.toContentString() = joinToString("")
fun NonnegDecimalInteger.toNumberString() = if (isEmpty()) "0" else asReversed().joinToString("")


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

operator fun NonnegDecimalInteger.minus(that: NonnegDecimalInteger): NonnegDecimalInteger {
    // imperative implementation
    val maxSize = max(size, that.size)
    val diff = ByteArray(maxSize)
    var carry = false
    for (i in 0 until maxSize) {
        val digitDiff = this.getOrElse(i) { 0 } - that.getOrElse(i) { 0 } - if (carry) 1 else 0
        if (digitDiff >= 0) {
            diff[i] = digitDiff.toByte()
            carry = false
        } else {
            diff[i] = (digitDiff + 10).toByte()
            carry = true
        }
    }

    return if (carry)
        throw IllegalArgumentException("minus diff")
    else
        diff.dropLastWhile { it == zeroByte }.toCompact()
}


fun NonnegDecimalInteger.toInt() =
    toNumberString().toInt()

fun NonnegDecimalInteger.toLong() =
    toNumberString().toLong()

fun NonnegDecimalInteger.toBigInteger() =
    toNumberString().toBigInteger()

fun Int.toNonnegDecimalInteger() =
    toString().readableStringToNonnegDecimalInteger()

fun Long.toNonnegDecimalInteger() =
    toString().readableStringToNonnegDecimalInteger()

fun BigInteger.toNonnegDecimalInteger() =
    toString().readableStringToNonnegDecimalInteger()