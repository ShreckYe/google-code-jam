import java.util.*
import kotlin.system.exitProcess

fun main() {
    val (T, B) = readLine()!!.splitToSequence(' ').map(String::toInt).toList()
    repeat(T) {
        var symmetricalBitIndex: Int? = null
        var asymmetricalBitIndex: Int? = null
        var bits = BitSet(B)
        fun bitString() = bits.toBitString(B)
        var q = 0

        fun query(i: Int): Boolean {
            //System.err.println("q = $q")
            q++
            val P = i + 1
            println(P)
            val line = readLine()!!
            //assertEquals(1, line.length)
            return line[0] != '0'
        }

        var i = 0
        /*fun setBitString() =
            (0 until B).asSequence()
                .map { if (it < l || it > B - 1 - r) if (bits[it]) '1' else '0' else '-' }.joinToString("")*/
        outer@ while (true) {
            //System.err.println("New round of 10 queries")
            val complemented = symmetricalBitIndex !== null && query(symmetricalBitIndex) != bits[symmetricalBitIndex]
            if (complemented) {
                bits.flip(0, B)
                //System.err.println("Complemented: ${setBitString()}")
            }
            val reversedAfterComplemented =
                asymmetricalBitIndex !== null && query(asymmetricalBitIndex) != bits[asymmetricalBitIndex]
            if (reversedAfterComplemented) {
                val newBits = BitSet(B)
                for (i in 0 until B) newBits[i] = bits[B - 1 - i]
                bits = newBits
                //System.err.println("Reversed: ${setBitString()}")
            }

            for (qq in 0 until (10 - q % 10) / 2) {
                bits[i] = query(i)
                //System.err.println("Set: bits[$l] = ${bits[l]}")

                val j = B - 1 - i
                bits[j] = query(j)
                //System.err.println("Set: bits[$ri] = ${bits[ri]}")
                if (bits[i] == bits[j]) {
                    if (symmetricalBitIndex === null) {
                        symmetricalBitIndex = i
                        //System.err.println("symmetricalBitIndex = $symmetricalBitIndex")
                    }
                } else {
                    if (asymmetricalBitIndex === null) {
                        asymmetricalBitIndex = i
                        //System.err.println("asymmetricalBitIndex = $asymmetricalBitIndex")
                    }
                }
                i++

                //System.err.println(setBitString())

                if (i * 2 == B) break@outer
            }
            if (q % 10 != 0) query(0)
        }

        //System.err.println(bitString())
        //System.err.println()

        println(bitString())
        val judge = readLine()
        when (judge) {
            "Y" -> Unit
            "N" -> exitProcess(-1)
            else -> throw IllegalArgumentException(judge)
        }
    }
}

fun BitSet.toBitString(length: Int) =
    (0 until length).asSequence().map { if (this[it]) '1' else '0' }.joinToString("")