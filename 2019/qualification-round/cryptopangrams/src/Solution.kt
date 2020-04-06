import java.math.BigInteger

fun main() {
    val T = readLine()!!.toInt()
    repeat(T) { t ->
        val line = readLine()!!.split(' ')
        val N = line[0].toBigInteger()
        val L = line[1].toInt()
        val ciphertext = readLine()!!.splitToSequence(' ')/*.filter(String::isNotEmpty)*/.map(String::toBigInteger).toList()
        /*assert(L == ciphertext.size) {
            println("Case #${t + 1}: error: sizes don't match")
            return@repeat
        }*/

        println("Case #${t + 1}: ${case(N, L, ciphertext)}")
    }
}

fun case(N: BigInteger, L: Int, ciphertext: List<BigInteger>): String {
    val firstCipherValue = ciphertext[0]
    val secondLetterPrime = firstCipherValue.gcd(ciphertext[1])
    val firstLetterPrime = firstCipherValue / secondLetterPrime
    val letterPrimeText = (sequenceOf(firstLetterPrime) + ciphertext.asSequence().drop(1)
        .scan(secondLetterPrime) { letterPrime, cipherValue -> cipherValue / letterPrime })
        .toList()
    val letterPrimes = letterPrimeText.distinct().sorted()
    //assert(26 == letterPrimes.size) { return "error: the number of letter primes is ${letterPrimes.size}" }
    //letterPrimes.forEach { assert(it <= N) { return "error: a number $it greater than N = $N" } }
    val primeToLetter = (letterPrimes zip UPPERCASE_LETTERS).toMap()
    return letterPrimeText.map(primeToLetter::get).joinToString("")
}

val UPPERCASE_LETTERS = 'A'..'Z'

// Copied from library
fun <T, R> Sequence<T>.scan(initial: R, operation: (acc: R, T) -> R): Sequence<R> {
    return sequence {
        yield(initial)
        var accumulator = initial
        for (element in this@scan) {
            accumulator = operation(accumulator, element)
            yield(accumulator)
        }
    }
}

/*
inline fun assert(value: Boolean, lazyMessage: () -> Any) {
    if (!value) {
        val message = lazyMessage()
        throw AssertionError(message)
    }
}*/
