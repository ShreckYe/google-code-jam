import kotlin.math.max

// no leading zeros
typealias NonnegDecimalNumber = List<Byte>
typealias CompactNonnegDecimalNumber = ByteArray


val zero = byteArrayOf().asList()
val one = byteArrayOf(1).asList()


fun String.toDecimalNumber() = map { it - '0' }
fun NonnegDecimalNumber.toStringOrEmptyStringIfZero() = joinToString("")
fun NonnegDecimalNumber.toStringOrZero() = if (isEmpty()) "0" else toStringOrEmptyStringIfZero()


operator fun NonnegDecimalNumber.compareTo(that: NonnegDecimalNumber): Int {
    val size = size
    val thatSize = that.size
    return if (size != thatSize) size.compareTo(thatSize)
    else compareToWithSameSize(that)
}

fun NonnegDecimalNumber.compareToWithSameSize(that: NonnegDecimalNumber): Int {
    val firstCompareResult = first().compareTo(that.first())
    return if (firstCompareResult != 0) firstCompareResult
    else subList(1, size).compareToWithSameSize(that.subList(1, size))
}

operator fun NonnegDecimalNumber.plus(that: NonnegDecimalNumber): NonnegDecimalNumber {
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
        return sum.asList().subList(1, capacity)
}

fun NonnegDecimalNumber.plusOne() =
    plus(one)
