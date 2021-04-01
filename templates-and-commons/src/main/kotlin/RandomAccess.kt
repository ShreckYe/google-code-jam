@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

import java.util.*

interface RandomAccess<T, C> {
    fun create(size: Int, init: (Int) -> T): C
    operator fun C.get(i: Int): T
    operator fun C.set(i: Int, value: T)
}


// This is not really necessary for coding competitions,
// first because O(n) initialization doesn't add to the overall asymptotic complexity,
// and second because array initialization is already O(n) on the JVM since JVM initializes its contents to the default value (0 or null).
interface RandomAccessWithDefaultValue<T, C> : RandomAccess<T, C> {
    fun createWithDefaultValue(size: Int): C
}


object IntArrayRandomAccess : RandomAccessWithDefaultValue<Int, IntArray> {
    override fun create(size: Int, init: (Int) -> Int): IntArray = IntArray(size, init)
    override fun createWithDefaultValue(size: Int): IntArray = IntArray(size)
    override operator fun IntArray.get(i: Int): Int = get(i)
    override operator fun IntArray.set(i: Int, value: Int) = set(i, value)
}

object LongArrayRandomAccess : RandomAccessWithDefaultValue<Long, LongArray> {
    override fun create(size: Int, init: (Int) -> Long): LongArray = LongArray(size, init)
    override fun createWithDefaultValue(size: Int): LongArray = LongArray(size)
    override operator fun LongArray.get(i: Int): Long = get(i)
    override operator fun LongArray.set(i: Int, value: Long) = set(i, value)
}

object FloatArrayRandomAccess : RandomAccessWithDefaultValue<Float, FloatArray> {
    override fun create(size: Int, init: (Int) -> Float): FloatArray = FloatArray(size, init)
    override fun createWithDefaultValue(size: Int): FloatArray = FloatArray(size)
    override operator fun FloatArray.get(i: Int): Float = get(i)
    override operator fun FloatArray.set(i: Int, value: Float) = set(i, value)
}

object DoubleArrayRandomAccess : RandomAccessWithDefaultValue<Double, DoubleArray> {
    override fun create(size: Int, init: (Int) -> Double): DoubleArray = DoubleArray(size, init)
    override fun createWithDefaultValue(size: Int): DoubleArray = DoubleArray(size)
    override operator fun DoubleArray.get(i: Int): Double = get(i)
    override operator fun DoubleArray.set(i: Int, value: Double) = set(i, value)
}


object BitSetRandomAccess : RandomAccessWithDefaultValue<Boolean, BitSet> {
    override fun create(size: Int, init: (Int) -> Boolean): BitSet =
        BitSet(size).also { for (i in 0 until size) it[i] = init(i) }

    override fun createWithDefaultValue(size: Int): BitSet = BitSet(size)
    override fun BitSet.get(i: Int): Boolean = get(i)
    override fun BitSet.set(i: Int, value: Boolean) = set(i, value)
}


open class ArrayRandomAccess<T> : RandomAccess<T, Array<T>> {
    override fun create(size: Int, init: (Int) -> T): Array<T> {
        @Suppress("UNCHECKED_CAST")
        return Array<Any?>(size, init) as Array<T>
    }

    override operator fun Array<T>.get(i: Int): T = get(i)
    override operator fun Array<T>.set(i: Int, value: T) = set(i, value)
}

class ArrayRandomAccessWithNullDefaultValue<NotNullT : Any> :
    ArrayRandomAccess<NotNullT?>(), RandomAccessWithDefaultValue<NotNullT?, Array<NotNullT?>> {
    override fun createWithDefaultValue(size: Int): Array<NotNullT?> {
        @Suppress("UNCHECKED_CAST")
        return arrayOfNulls<Any?>(size) as Array<NotNullT?>
    }
}