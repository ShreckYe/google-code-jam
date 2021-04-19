fun Int.squared(): Int =
    this * this

fun Long.squared(): Long =
    this * this


infix fun Int.powInt(that: Int): Int =
    when {
        that == 0 -> 1
        that > 0 -> powInt(that / 2).squared()
            .let { if (that % 2 == 0) it else it * this }
        else -> inv() powInt (-that)
    }

infix fun Long.powInt(that: Int): Long =
    when {
        that == 0 -> 1L
        that > 0 -> powInt(that / 2).squared()
            .let { if (that % 2 == 0) it else it * this }
        else -> inv() powInt (-that)
    }