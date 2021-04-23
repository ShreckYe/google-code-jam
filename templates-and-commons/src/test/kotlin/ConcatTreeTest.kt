import kotlin.test.Test
import kotlin.test.assertEquals

class ConcatTreeTest {
    @Test
    fun simpleTest() {
        fun buildTreeAndGetEnd(depth: Int, start: Int): Pair<ConcatTree<Int>, Int> =
            if (depth == 0) concatTreeOf(start) to start + 1
            else {
                val (left, leftEnd) = buildTreeAndGetEnd(depth - 1, start)
                val (right, rightEnd) = buildTreeAndGetEnd(depth - 1, leftEnd)
                left + right to rightEnd
            }

        fun buildTree(depth: Int) = buildTreeAndGetEnd(depth, 0).first

        assertEquals((0 until (2 powInt 4)).toList(), buildTree(4).toList())
        // This test should run in O(n) which is about 1 million iterations. If it were O(n^2) it would be 10^12 which shouldn't finish.
        assertEquals((0 until (2 powInt 20)).toList(), buildTree(20).toList())
    }
}