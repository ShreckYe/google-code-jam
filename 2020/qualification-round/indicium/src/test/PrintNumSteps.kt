package test

import kotlin.concurrent.thread

fun main() {
    for (n in 1..10) {
        println("n = $n")
        val latinMatrices = latinMatrices(n)
        for (k in n..n * n) {
            if (k == n + 1 || k == n * n - 1)
                continue
            val t = thread {
                println("k = $k")
                val numSteps = latinMatrices.indexOfFirst {
                    if (Thread.interrupted()) throw InterruptedException()
                    it.trace() == k
                }
                println("Steps taken to find the first answer: $numSteps")
            }
            t.join(1000)
            if (t.isAlive) {
                t.interrupt()
                println("Took too long to compute. Skipped.")
            }
        }
    }
}