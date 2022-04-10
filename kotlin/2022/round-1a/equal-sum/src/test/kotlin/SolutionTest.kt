import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertFalse

class SolutionTest {
    @Test
    fun test() = repeat(1000) {
        for (nn in listOf(1, 10, 20, 30)) {
            val bbs = List(nn) { Random.nextLong(1, 1000000000) }
            val aas = generateAas(nn)
            val expectedSum = (bbs.sum() + aas.sum()) / 2

            val n = nn.coerceAtMost(29)
            val bfAns = try {
                bruteForce(bbs, expectedSum, n)
            } catch (e: NoSuchElementException) {
                null
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }

            val solveAns = try {
                solve(nn, bbs, expectedSum)
            } catch (e: NoSuchElementException) {
                null
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }

            assertFalse(
                (bfAns !== null) xor (solveAns !== null),
                "bbs=$bbs\naas=$aas\nexpectedSum=$expectedSum\nbfAns=$bfAns\nbfAnsSum=${bfAns?.sum()}\nsolveAns=$solveAns\nsolveAnsSum=${solveAns?.sum()}"
            )
        }
    }
}