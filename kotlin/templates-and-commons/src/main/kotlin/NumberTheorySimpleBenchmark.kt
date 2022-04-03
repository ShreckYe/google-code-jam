import kotlin.system.measureNanoTime

fun main() {
    repeat(10) { singleRun(false) }
    singleRun(true)
}

fun singleRun(print: Boolean) {
    if (print) println("Simple algorithm")
    for (i in 1..5)
        measureWithIAndPrint(i, print, ::simplePrimesTo)
    if (print) println()

    if (print) println("Faster algorithm - check with already computed primes")
    for (i in 1..7)
        measureWithIAndPrint(i, print, ::primesToWithCheckWithAlreadyComputedPrimes)
    if (print) println()

    if (print) println("Faster algorithm - Sieve of Eratosthenes")
    for (i in 1..8)
        measureWithIAndPrint(i, print, ::primesToWithSOE)
    if (print) println()
}

inline fun measureWithIAndPrint(i: Int, print: Boolean, block: (n: Int) -> Unit) {
    val n = 10 powInt i
    val time = measureNanoTime { block(n) }
    if (print) println("$i $n $time ns")
}