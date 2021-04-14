fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

// with no leading 0s
typealias DecimalNumber = List<Char>

operator fun DecimalNumber.compareTo(that: DecimalNumber): Int {
    val size = size
    return when {
        size != that.size -> size.compareTo(that.size)
        size == 0 -> 0
        else -> {
            val first = first()
            val thatFirst = that.first()
            when {
                first < thatFirst -> -1
                first > thatFirst -> 1
                else -> subList(1, size).compareTo(that.subList(1, size))
            }
        }
    }
}

fun DecimalNumber.plusOneSeqAndCarry(): Pair<Sequence<Char>, Boolean> =
    if (size == 0) "1".asSequence() to true
    else {
        val (tailPlusOne, carry) = subList(1, size).plusOneSeqAndCarry()
        if (carry) {
            val newFirst = first() + 1
            if (newFirst <= '9') sequenceOf(newFirst) + List(size - 1) { '0' } to false
            else "1".asSequence() + List(size) { '0' } to true
        } else
            sequenceOf(first()) + tailPlusOne to false
    }

fun DecimalNumber.plusOne(): DecimalNumber =
    plusOneSeqAndCarry().first.toList()

fun DecimalNumber.appendToBeGeSeq(that: DecimalNumber): Sequence<Char> =
    if (that.isEmpty()) asSequence()
    else {
        val first = first()
        val thatFirst = that.first()
        when {
            first > thatFirst -> asSequence() + List(that.size - size) { '0' }
            first < thatFirst -> asSequence() + List(that.size - size + 1) { '0' }
            else -> sequenceOf(first) + subList(1, size).appendToBeGeSeq(that.subList(1, size))
        }
    }

fun DecimalNumber.appendToBeGe(that: DecimalNumber): DecimalNumber =
    appendToBeGeSeq(that).toList()


fun testCase(ti: Int) {
    val n = readLine()!!.toInt()
    val xs = readLine()!!.splitToSequence(' ').map { it.toList() }.toList()

    var lastNumber = emptyList<Char>()
    var minNumSDA = 0
    for (x in xs) {
        if (x > lastNumber) {
            lastNumber = x
        } else {
            val newNumber = x.appendToBeGe(lastNumber.plusOne())
            minNumSDA += newNumber.size - x.size
            lastNumber = newNumber
        }
    }

    println("Case #${ti + 1}: $minNumSDA")
}