import kotlin.test.Test
import kotlin.test.assertEquals

class EnumerationsTest {
    fun <T> assertSequenceEquals(expected: Sequence<T>, actual: Sequence<T>) {
        assertEquals(expected.count(), actual.count())
        (expected zip actual).forEach { assertEquals(it.first, it.second) }
    }

    @Test
    fun testSubsets() {
        fun test(size: Int) {
            val numSubsets = 1 shl size
            val bitSubsets = bitSubsets(size)
            assertEquals(numSubsets, bitSubsets.count())
            assertSequenceEquals(bitSubsets, bitSubsets2(size))

            val list = List(size) { it }
            val allSubsets = list.allSubsets()
            val all2Partitions = list.all2Partitions()

            assertEquals(numSubsets, allSubsets.count())
            assertEquals(numSubsets, allSubsets.count())

            val sizeLong = size.toLong()
            val expectedSum = sizeLong * (sizeLong - 1) / 2 * (1 shl (size - 1))
            assertEquals(expectedSum, allSubsets.sumOf { it.sum().toLong() })
            assertSequenceEquals(allSubsets, all2Partitions.map { it.first })
            assertEquals(expectedSum, all2Partitions.map { it.second }.sumOf { it.sum().toLong() })

            all2Partitions.forEach { assertEquals(list, (it.first + it.second).sorted()) }
        }

        test(0)
        test(10)
        test(20)
    }
}