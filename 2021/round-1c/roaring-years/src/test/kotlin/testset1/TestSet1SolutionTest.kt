package testset1

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class TestSet1SolutionTest {
    @Test
    fun testIsRoaring() {
        assert(isRoaring(2021L))

        assertDoesNotThrow {
            for (y in 1L..1_000_000L)
                isRoaring(y)
        }
    }
}