import java.math.BigInteger
import kotlin.math.max

// no leading zeros
typealias NonnegDecimalInteger = List<Byte>
typealias CompactArrayNonnegDecimalInteger = ByteArray


fun NonnegDecimalInteger.toCompactArray(): CompactArrayNonnegDecimalInteger =
    toByteArray()

fun NonnegDecimalInteger.toCompact(): NonnegDecimalInteger =
    toCompactArray().asList()


val zero = byteArrayOf().asList()
val one = byteArrayOf(1).asList()


// NLZ: no leading zero

fun String.nLZToNonnegDecimalInteger() = map { (it - '0').toByte().also { require(it in 0..9) } }
fun String.zeroOrNLZToNonnegDecimalInteger(): NonnegDecimalInteger {
    require(isNotEmpty())
    return if (this == "0") zero
    else nLZToNonnegDecimalInteger()
}

fun NonnegDecimalInteger.toNLZString() = joinToString("")
fun NonnegDecimalInteger.toStringOrZero() = if (isEmpty()) "0" else toNLZString()


infix operator fun NonnegDecimalInteger.compareTo(that: NonnegDecimalInteger): Int {
    val size = size
    val thatSize = that.size
    return if (size != thatSize) size.compareTo(thatSize)
    else compareToWithSameSize(that)
}

infix fun NonnegDecimalInteger.compareToWithSameSize(that: NonnegDecimalInteger): Int =
    if (size == 0) 0
    else {
        val firstCompareResult = first().compareTo(that.first())
        if (firstCompareResult != 0) firstCompareResult
        else subList(1, size) compareToWithSameSize (that.subList(1, size))
    }

operator fun NonnegDecimalInteger.plus(that: NonnegDecimalInteger): NonnegDecimalInteger {
    // imperative implementation
    val thisReversed = asReversed()
    val thatReversed = that.asReversed()
    val maxSize = max(size, that.size)
    val capacity = maxSize + 1
    val sum = ByteArray(capacity)
    val sumLastIndex = sum.lastIndex
    var carry = false
    for (i in 0 until maxSize) {
        val digitSum = thisReversed.getOrElse(i) { 0 } + thatReversed.getOrElse(i) { 0 } + if (carry) 1 else 0
        if (digitSum < 10) {
            sum[sumLastIndex - i] = digitSum.toByte()
            carry = false
        } else {
            sum[sumLastIndex - i] = (digitSum - 10).toByte()
            carry = true
        }
    }

    @Suppress("LiftReturnOrAssignment")
    if (carry) {
        sum[0] = 1
        return sum.asList()
    } else
        return sum.asList().subList(1, capacity)
}

fun NonnegDecimalInteger.plusOne() =
    plus(one)

operator fun NonnegDecimalInteger.minus(that: NonnegDecimalInteger): NonnegDecimalInteger {
    // imperative implementation
    val thisReversed = asReversed()
    val thatReversed = that.asReversed()
    val maxSize = max(size, that.size)
    val capacity = maxSize
    val diff = ByteArray(capacity)
    val diffLastIndex = diff.lastIndex
    var carry = false
    for (i in 0 until maxSize) {
        val digitDiff = thisReversed.getOrElse(i) { 0 } - thatReversed.getOrElse(i) { 0 } - if (carry) 1 else 0
        if (digitDiff >= 0) {
            diff[diffLastIndex - i] = digitDiff.toByte()
            carry = false
        } else {
            diff[diffLastIndex - i] = (digitDiff + 10).toByte()
            carry = true
        }
    }

    return if (carry)
        throw IllegalArgumentException("minus diff")
    else
        diff.asSequence().dropWhile { it == 0.toByte() }.toList().toCompact()
}


fun NonnegDecimalInteger.toInt() =
    toStringOrZero().toInt()

fun NonnegDecimalInteger.toLong() =
    toStringOrZero().toLong()

fun NonnegDecimalInteger.toBigInteger() =
    toStringOrZero().toBigInteger()

fun Int.toNonnegDecimalInteger() =
    toString().zeroOrNLZToNonnegDecimalInteger()

fun Long.toNonnegDecimalInteger() =
    toString().zeroOrNLZToNonnegDecimalInteger()

fun BigInteger.toNonnegDecimalInteger() =
    toString().zeroOrNLZToNonnegDecimalInteger()