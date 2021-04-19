import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.random.Random
import kotlin.test.assertEquals

internal class NumberTheoryTest {
    @Test
    fun testSimplePrimesTo() {
        assertEquals(listOf(), simplePrimesTo(0))
        assertEquals(listOf(), simplePrimesTo(1))
        assertEquals(listOf(2), simplePrimesTo(2))
        assertEquals(listOf(2, 3, 5, 7, 11, 13, 17, 19), simplePrimesTo(20))
        assertEquals(25, simplePrimesTo(100).size)
    }

    @Test
    fun testXLnXConst() {
        Assertions.assertEquals(1.25506, xLnXConst, 0.00001)
    }

    @Test
    fun testPrimesToAndPiXUpperBound() {
        for (n in 2 until 1000) {
            val simplePrimesToX = simplePrimesTo(n)

            assert(simplePrimesToX.size <= piXUpperBound(n))

            assertEquals(simplePrimesToX, primesToWithCheckWithAlreadyComputedPrimes(n))
            assertEquals(simplePrimesToX, primesToWithSOE(n))
        }
    }

    @Test
    fun testFactorization() {
        for (n in 1 until 1000) {
            val primes = primesToWithSOE(n)
            fun testFactorization(n: Long) {
                val factorizationResult = factorizeWithFactors(n, primes)
                val (factorAndNums, remaining) = factorizationResult
                assert(factorAndNums.all { it.factor in primes && it.num > 0 })
                assert(remaining == 1L || factorAndNums.lastOrNull().let { it == null || remaining > it.factor })
                assertEquals(n, recomposeFactors(factorizationResult))
            }

            for (m in 1 until 1000)
                testFactorization(m.toLong())
            repeat(1000) {
                val m = Random.nextLong(1, 1000000)
                testFactorization(m)
            }
        }

        for (nInt in 1 until 1000000) {
            val n = nInt.toLong()
            assertEquals(n, recomposeFactors(factorizePrimes(n)))
        }
        /*repeat() {
            val n = Random.nextLong(1, 1000000)
            testDecomposeAndRecompose(n)
        }*/
    }
}