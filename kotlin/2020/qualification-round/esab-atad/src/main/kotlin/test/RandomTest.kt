package test

import java.io.PipedInputStream
import java.io.PipedOutputStream
import java.io.PrintStream
import java.util.*
import kotlin.concurrent.thread
import kotlin.random.Random
import kotlin.test.assertEquals

fun main() {
    val originalSystemOut = System.out

    val solutionIn = PipedInputStream()
    val solutionOut = PipedOutputStream()
    System.setIn(solutionIn)
    System.setOut(PrintStream(solutionOut))

    val testIn = PipedInputStream()
    val testOut = PipedOutputStream()

    testOut.connect(solutionIn)
    solutionOut.connect(testIn)
    val testThread = thread {
        val testOut = PrintStream(testOut)
        val testScanner = Scanner(testIn)

        val T = 100
        val B = 20

        testOut.println("$T $B")
        repeat(T) { t ->
            originalSystemOut.println("t = $t")
            val nBytes = (B + Byte.SIZE_BITS - 1) / Byte.SIZE_BITS
            var bits = BitSet.valueOf(Random.nextBytes(nBytes))
            bits.clear(B, nBytes * Byte.SIZE_BITS)
            for (i in 0 until 150) {
                val line = readLine()!!
                if (line.all { it == '0' || it == '1' }) {
                    val solutionBits = BitSet(B)
                    line.forEachIndexed { i, c -> solutionBits[i] = c != '0' }
                    assertEquals(bits, solutionBits)
                    break
                }
                if (i % 10 == 0) {
                    TODO()
                }
                val P = line.toInt()
                TODO()

            }
        }
    }
    val solutionThread = thread {
        TODO()
        /*solutionMain()*/
    }
}