import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SolutionTest {
    @Test
    fun testNumbersTakenWithSingle() {
        assertEquals(numbersTakenWithSingle(1), 1)
        assertEquals(numbersTakenWithSingle(2), 1)
        assertEquals(numbersTakenWithSingle(3), 2)
        assertEquals(numbersTakenWithSingle(4), 2)
        assertEquals(numbersTakenWithSingle(5), 3)
        assertEquals(numbersTakenWithSingle(6), 3)
        assertEquals(numbersTakenWithSingle(7), 4)
        assertEquals(numbersTakenWithSingle(8), 4)
    }
}