import java.math.BigInteger

fun main() {
    val T = readLine()!!.toInt()
    repeat(T) { t ->
        val line = readLine()!!.split(' ')
        val N = line[0].toBigInteger()
        val L = line[1].toInt()
        val ciphertext =
            readLine()!!.splitToSequence(' ')/*.filter(String::isNotEmpty)*/.map(String::toBigInteger).toList()
        /*assert(L == ciphertext.size) {
            println("Case #${t + 1}: error: sizes don't match")
            return@repeat
        }*/

        println("Case #${t + 1}: ${case(N, L, ciphertext)}")
    }
}

fun case(N: BigInteger, L: Int, ciphertext: List<BigInteger>): String {
    val (left, right) = ciphertext.withIndex().zipWithNext().first { it.first.value != it.second.value }
    val leftCipherValue = left.value
    val commonLetterPrime = leftCipherValue.gcd(right.value)
    val letterPrimeText = listOf(
        ciphertext.subListTo(left.index + 1).asReversed()
            .scan(commonLetterPrime) { letterPrime, cipherValue -> cipherValue / letterPrime }.subListFrom(1)
            .asReversed(),
        listOf(commonLetterPrime),
        ciphertext.subListFrom(right.index)
            .scan(commonLetterPrime) { letterPrime, cipherValue -> cipherValue / letterPrime }.subListFrom(1)
    ).flatten()
    val letterPrimes = letterPrimeText.distinct().sorted()
    //assert(26 == letterPrimes.size) { return "error: the number of letter primes is ${letterPrimes.size}" }
    //letterPrimes.forEach { assert(it <= N) { return "error: a number $it greater than N = $N" } }
    val primeToLetter = (letterPrimes zip UPPERCASE_LETTERS).toMap()
    return letterPrimeText.map(primeToLetter::get).joinToString("")
}

fun <E> List<E>.subListFrom(fromIndex: Int): List<E> = subList(fromIndex, size)
fun <E> List<E>.subListTo(toIndex: Int): List<E> = subList(0, toIndex)
val UPPERCASE_LETTERS = 'A'..'Z'

// Copied from library
inline fun <T, R> List<T>.scan(initial: R, operation: (acc: R, T) -> R): List<R> {
    val estimatedSize = size
    if (estimatedSize == 0) return listOf(initial)
    val result = ArrayList<R>(estimatedSize + 1).apply { add(initial) }
    var accumulator = initial
    for (element in this) {
        accumulator = operation(accumulator, element)
        result.add(accumulator)
    }
    return result
}

/*
inline fun assert(value: Boolean, lazyMessage: () -> Any) {
    if (!value) {
        val message = lazyMessage()
        throw AssertionError(message)
    }
}*/
