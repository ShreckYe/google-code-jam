data class OpenInterval<T>(val left: T, val right: T) {
    override fun toString(): String {
        return "($left, $right)"
    }
}


tailrec inline fun <A, B> binarySearch(
    left: A, right: A, f: (A) -> B, target: B,
    safeNumberOps: SafeNumberOps<A>
): Either<A, OpenInterval<A>> =
    with(safeNumberOps) {
        val mid = left + right
        val fMid = f(mid)
        TODO()
    }

fun <T> binarySearchMax(): T? = TODO()