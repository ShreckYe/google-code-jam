sealed class FList<out T> : Iterable<T> {
    override fun iterator(): Iterator<T> = object : Iterator<T> {
        private var current: FList<T> = this@FList
        override fun hasNext(): Boolean =
            when (current) {
                is Cons<T> -> true
                is Nil -> false
            }

        override fun next(): T {
            val current = current
            return when (current) {
                is Cons<T> -> current.head
                    .also { this.current = current.tail }
                is Nil -> throw NoSuchElementException()
            }
        }
    }
}

class Cons<out T>(val head: T, val tail: FList<T>) : FList<T>()

infix fun <T> T.cons(that: FList<T>) = Cons(this, that)

object Nil : FList<Nothing>()

fun <T> Iterable<T>.toFList(): FList<T> =
    iterator().remainingToFList()

fun <T> Iterator<T>.remainingToFList(): FList<T> =
    if (hasNext()) next() cons remainingToFList()
    else Nil

/*
fun <T> FList<T>.asSequence(): Sequence<T> =
    when (this) {
        is Cons<T> -> sequenceOf(head) + tail.asSequence()
        is Nil -> emptySequence()
    }

fun <T> Sequence<T>.toFList(): FList<T> =
    iterator().remainingToFList()
*/