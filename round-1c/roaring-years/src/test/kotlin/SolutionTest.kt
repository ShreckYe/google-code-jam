import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import testset1.ans as testSet1Ans

class SolutionTest {
    @Test
    fun testAns() {
        for (y in 1L..1_000_000L) {
            println(y)
            val yS = y.toString()
            assertEquals(testSet1Ans(yS), ans(yS))
        }
    }
}