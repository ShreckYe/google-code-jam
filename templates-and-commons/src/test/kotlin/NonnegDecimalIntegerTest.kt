import org.junit.jupiter.api.Test
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

internal class NonnegDecimalIntegerTest {
    val defaultNumTestTimes = 100
    val defaultMaxLength = 100
    fun Random.randomNonnegDecimalInteger(maxLength: Int = defaultMaxLength): NonnegDecimalInteger =
        generateSequence { nextInt(0, 10).toByte() }
            .take(maxLength).toList().dropLastWhile { it == 0.toByte() }
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
            val contentString = it.toContentString()
            contentString.getOrNull(contentString.lastIndex)?.let { assertNotEquals('0', it) }
            assertEquals(it, contentString.contentStringToNonnegDecimalInteger())

            val numberString = it.toNumberString()
            assert(numberString == "0" || numberString[0] != '0')
            assertEquals(it, numberString.readableStringToNonnegDecimalInteger())

            try {
                assertEquals(it, it.toInt().toNonnegDecimalInteger())
            } catch (e: NumberFormatException) {
            }
            try {
                assertEquals(it, it.toLong().toNonnegDecimalInteger())
            } catch (e: NumberFormatException) {
            }
            assertEquals(it, it.toBigInteger().toNonnegDecimalInteger())
        }

    @Test
    fun testCompareToAndArithmetics() {
        testEdgeCasesAndRandomNonnegDecimalIntegers { a ->
            testEdgeCasesAndRandomNonnegDecimalIntegers { b ->
                assertEquals((a.toBigInteger().compareTo(b.toBigInteger())), a compareTo b)
                assertEquals((b.toBigInteger().compareTo(a.toBigInteger())), b compareTo a)

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