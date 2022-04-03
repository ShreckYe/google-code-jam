typealias ConcatTree<T> = ConcatTreeNode<T>

sealed class ConcatTreeNode<T> : Iterable<T>

data class BranchNode<T>(val children: List<ConcatTreeNode<T>>) : ConcatTreeNode<T>() {
    // DFS
    // Iteration should be be O(n).
    override fun iterator(): Iterator<T> = object : Iterator<T> {
        var currentLeafIterator: Iterator<T> = EmptyIterator
        val recursionStack = ArrayDeque(children)

        fun popFirstNonEmptyIteratorIfNeeded(): Boolean {
            return if (currentLeafIterator.hasNext())
                true
            else {
                while (recursionStack.isNotEmpty()) {
                    val first = recursionStack.removeFirst()
                    when (first) {
                        is BranchNode<T> ->
                            recursionStack.addAll(first.children)
                        is LeafNode<T> ->
                            if (first.isEmpty()) continue
                            else {
                                val iterator = first.iterator()
                                this.currentLeafIterator = iterator
                                return true
                            }
                    }
                }
                false
            }
        }

        override fun hasNext(): Boolean =
            popFirstNonEmptyIteratorIfNeeded()

        override fun next(): T =
            if (popFirstNonEmptyIteratorIfNeeded()) currentLeafIterator.next()
            else throw NoSuchElementException()
    }
}

object EmptyIterator : Iterator<Nothing> {
    override fun hasNext(): Boolean = false
    override fun next(): Nothing = throw NoSuchElementException()
}

data class LeafNode<T>(val list: List<T>) : ConcatTreeNode<T>() {
    fun isEmpty() = list.isEmpty()
    override fun iterator(): Iterator<T> = list.iterator()
}


fun <T> concatTreeOf(vararg elements: T) =
    LeafNode(elements.asList())

operator fun <T> ConcatTree<T>.plus(that: ConcatTree<T>) =
    BranchNode(listOf(this, that))

infix fun <T> ConcatTree<T>.plusEnsuringON(that: ConcatTree<T>) =
    when {
        this.none() -> that
        that.none() -> this
        else -> this + that
    }