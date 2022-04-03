@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

import java.util.*

interface RandomAccess<T, C> {
    /* `init` is not really necessary for coding competitions,
    first because O(n) initialization doesn't add to the overall asymptotic complexity,
    and second because array initialization is already O(n) on the JVM since JVM initializes its contents to the default value (0 or null). */
    fun create(size: Int, init: ((Int) -> T)? = null): C
    operator fun C.get(i: Int): T
    operator fun C.set(i: Int, value: T)
}


object IntArrayRandomAccess : RandomAccess<Int, IntArray> {
    override fun create(size: Int, init: ((Int) -> Int)?): IntArray =
        if (init === null) IntArray(size) else IntArray(size, init)

    override operator fun IntArray.get(i: Int): Int = get(i)
    override operator fun IntArray.set(i: Int, value: Int) = set(i, value)
}

object LongArrayRandomAccess : RandomAccess<Long, LongArray> {
    override fun create(size: Int, init: ((Int) -> Long)?): LongArray =
        if (init === null) LongArray(size) else LongArray(size, init)

    override operator fun LongArray.get(i: Int): Long = get(i)
    override operator fun LongArray.set(i: Int, value: Long) = set(i, value)
}

object FloatArrayRandomAccess : RandomAccess<Float, FloatArray> {
    override fun create(size: Int, init: ((Int) -> Float)?): FloatArray =
        if (init === null) FloatArray(size) else FloatArray(size, init)

    override operator fun FloatArray.get(i: Int): Float = get(i)
    override operator fun FloatArray.set(i: Int, value: Float) = set(i, value)
}

object DoubleArrayRandomAccess : RandomAccess<Double, DoubleArray> {
    override fun create(size: Int, init: ((Int) -> Double)?): DoubleArray =
        if (init === null) DoubleArray(size) else DoubleArray(size, init)

    override operator fun DoubleArray.get(i: Int): Double = get(i)
    override operator fun DoubleArray.set(i: Int, value: Double) = set(i, value)
}


object BitSetRandomAccess : RandomAccess<Boolean, BitSet> {
    override fun create(size: Int, init: ((Int) -> Boolean)?): BitSet =
        BitSet(size).also { if (init !== null) for (i in 0 until size) it[i] = init(i) }

    override fun BitSet.get(i: Int): Boolean = get(i)
    override fun BitSet.set(i: Int, value: Boolean) = set(i, value)
}


interface ArrayRandomAccess<T> : RandomAccess<T, Array<T>> {
    fun createWithDefault(size: Int): Array<T>
    override fun create(size: Int, init: ((Int) -> T)?): Array<T> {
        @Suppress("UNCHECKED_CAST")
        return if (init === null) createWithDefault(size) else Array<Any?>(size, init) as Array<T>
    }

    override operator fun Array<T>.get(i: Int): T = get(i)
    override operator fun Array<T>.set(i: Int, value: T) = set(i, value)
}

class NullableArrayRandomAccess<NotNullT : Any> : ArrayRandomAccess<NotNullT?> {
    override fun createWithDefault(size: Int): Array<NotNullT?> {
        @Suppress("UNCHECKED_CAST")
        return arrayOfNulls<Any>(size) as Array<NotNullT?>
    }
}

class NotNullArrayRandomAccess<T : Any>(val defaultValue: T) : ArrayRandomAccess<T> {
    override fun createWithDefault(size: Int): Array<T> {
        @Suppress("UNCHECKED_CAST")
        return Array<Any>(size) { defaultValue } as Array<T>
    }
}