import java.util.*

class TwoD<T, C>(
    val size1: Int, val size2: Int, val randomAccess: RandomAccess<T, C>, init: ((Int, Int) -> T)? = null
) {
    val backing = randomAccess.create(size1 * size2).also {
        if (init !== null)
            with(randomAccess) {
                for (i in 0 until size1) {
                    val size1TimesI = size1 * i
                    for (j in 0 until size2)
                        it[size1TimesI + j] = init(i, j)
                }
            }
    }

    fun backingIndex(i: Int, j: Int): Int {
        require(i in 0 until size1)
        require(j in 0 until size2)
        return size1 * i + j
    }

    fun get(i: Int, j: Int): T = with(randomAccess) { backing[backingIndex(i, j)] }
    operator fun set(i: Int, j: Int, value: T) = with(randomAccess) { backing[backingIndex(i, j)] = value }
}

fun twoDIntArray(size1: Int, size2: Int, init: ((Int, Int) -> Int)? = null): TwoD<Int, IntArray> =
    TwoD(size1, size2, IntArrayRandomAccess, init)

fun twoDLongArray(size1: Int, size2: Int, init: ((Int, Int) -> Long)? = null): TwoD<Long, LongArray> =
    TwoD(size1, size2, LongArrayRandomAccess, init)

fun twoDFloatArray(size1: Int, size2: Int, init: ((Int, Int) -> Float)? = null): TwoD<Float, FloatArray> =
    TwoD(size1, size2, FloatArrayRandomAccess, init)

fun twoDDoubleArray(size1: Int, size2: Int, init: ((Int, Int) -> Double)? = null): TwoD<Double, DoubleArray> =
    TwoD(size1, size2, DoubleArrayRandomAccess, init)


fun twoDBitSet(size1: Int, size2: Int, init: ((Int, Int) -> Boolean)? = null): TwoD<Boolean, BitSet> =
    TwoD(size1, size2, BitSetRandomAccess, init)


// These are not really much more efficient than native 2D arrays.

fun <NotNullT : Any> twoDArrayOfNullables(
    size1: Int, size2: Int, init: ((Int, Int) -> NotNullT?)? = null
): TwoD<NotNullT?, Array<NotNullT?>> =
    TwoD(size1, size2, NullableArrayRandomAccess(), init)

fun <T : Any> twoDArrayOfNotNulls(
    size1: Int, size2: Int, defaultValue: T
): TwoD<T, Array<T>> =
    TwoD(size1, size2, NotNullArrayRandomAccess(defaultValue), null)

fun <T : Any> twoDArrayOfNotNulls(
    size1: Int, size2: Int, init: ((Int, Int) -> T)
): TwoD<T, Array<T>> {
    @Suppress("UNCHECKED_CAST")
    return twoDArrayOfNullables(size1, size2, init) as TwoD<T, Array<T>>
}