import org.junit.jupiter.api.Test
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

internal class NonnegDecimalIntegerTest {
    val defaultNumTestTimes = 100
    val defaultMaxLength = 100
    fun Random.randomNonnegDecimalInteger(maxLength: Int = defaultMaxLength): NonnegDecimalInteger =
        generateSequence { nextInt(0, 10).toByte() }
            .take(maxLength).dropWhile { it == 0.toByte() }
            .toList()

    inline fun testEdgeCasesAndRandomNonnegDecimalIntegers(
        numRandomTestTimes: Int = defaultNumTestTimes,
        maxLength: Int = defaultMaxLength,
        test: (NonnegDecimalInteger) -> Unit
    ) {
        test(zero)
        test(one)
        repeat(numRandomTestTimes) {
            test(Random.randomNonnegDecimalInteger(maxLength))
        }
    }

    @Test
    fun testToCompact() = testEdgeCasesAndRandomNonnegDecimalIntegers {
        assertEquals(it, it.toCompact())
    }


    @Test
    fun testConversions() =
        testEdgeCasesAndRandomNonnegDecimalIntegers {
            val nlzString = it.toNLZString()
            nlzString.getOrNull(0)?.let { assertNotEquals('0', it) }
            assertEquals(it, nlzString.nLZToNonnegDecimalInteger())

            val stringOrZero = it.toStringOrZero()
            assert(stringOrZero == "0" || stringOrZero[0] != '0')
            assertEquals(it, stringOrZero.zeroOrNLZToNonnegDecimalInteger())

            try {
                assertEquals(it, it.toInt().toNonnegDecimalInteger())
            } catch (e: NumberFormatException) {
            }
            try {
                assertEquals(it, it.toLong().toNonnegDecimalInteger())
            } catch (e: java.lang.NumberFormatException) {
            }
            assertEquals(it, it.toBigInteger().toNonnegDecimalInteger())
        }

    @Test
    fun testCompareToAndArithmetics() {
        assert(zero compareTo zero == 0)
        assert(zero < one)
        assert(one > zero)
        assert(one compareTo one == 0)

        testEdgeCasesAndRandomNonnegDecimalIntegers { a ->
            testEdgeCasesAndRandomNonnegDecimalIntegers { b ->
                if (a == b) assertEquals(0, a compareTo b)
                assertEquals(0, (a compareTo b) + (b compareTo a))

                assertEquals(
                    (a.toBigInteger() + b.toBigInteger()).toNonnegDecimalInteger(),
                    a + b
                )

                fun testMinus(a: NonnegDecimalInteger, b: NonnegDecimalInteger) =
                    assertEquals(
                        (a.toBigInteger() - b.toBigInteger()).toNonnegDecimalInteger(),
                        a - b
                    )
                if (a >= b) testMinus(a, b)
                else testMinus(b, a)
            }
        }
    }
}