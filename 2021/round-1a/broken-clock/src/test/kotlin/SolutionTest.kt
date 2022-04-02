import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.random.Random

class SolutionTest {

    @Test
    fun simpleTest() {
        fun test(allInNs: Long) {
            val a = allInNs * hSpeedInNs
            val b = allInNs * mSpeedInNs % numCircleTicks
            val c = allInNs * sSpeedInNs % numCircleTicks
            assertDoesNotThrow("$a $b $c") { println(ans(a, b, c)) }
        }

        val fullClockNs = 12 * hInNs

        (0 until 1000).forEach { test(it.toLong()) }
        repeat(1000) {
            test(Random.nextLong(0, fullClockNs))
        }
    }
}