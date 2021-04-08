interface MatrixOps<Matrix, Number> {
    val numberOps: NumberOps<Number>

    fun create(m: Int, n: Int, init: ((Int, Int) -> Number)? = null): Matrix
    fun Matrix.createWithSameDims(init: ((Int, Int) -> Number)? = null) =
        create(m, n, init)


    val Matrix.m: Int
    val Matrix.n: Int
    operator fun Matrix.get(i: Int, j: Int): Number
    operator fun Matrix.set(i: Int, j: Int, value: Number)

    fun Matrix.transpose() = create(n, m) { i, j -> this[j, i] }

    fun Matrix.incompatibleDimensionsMessage(that: Matrix) =
        "incompatible dimensions: $m x $n, ${that.m} x ${that.n}"

    infix fun Matrix.requireDimsEqual(that: Matrix) =
        require(m == that.m && n == that.n) { incompatibleDimensionsMessage(that) }

    operator fun Matrix.plus(that: Matrix): Matrix {
        requireDimsEqual(that)
        return with(numberOps) { createWithSameDims { i, j -> this@plus[i, j] + that[i, j] } }
    }

    operator fun Matrix.minus(that: Matrix): Matrix {
        requireDimsEqual(that)
        return with(numberOps) { createWithSameDims { i, j -> this@minus[i, j] - that[i, j] } }
    }

    infix fun Matrix.elementwiseTimes(that: Matrix): Matrix {
        requireDimsEqual(that)
        return with(numberOps) { createWithSameDims { i, j -> this@elementwiseTimes[i, j] * that[i, j] } }
    }

    operator fun Matrix.times(that: Matrix): Matrix {
        require(n == that.m) { incompatibleDimensionsMessage(that) }
        return with(numberOps) {
            create(m, that.n) { i, j ->
                (0 until n).asSequence().map { k -> this@times[i, k] * that[k, j] }.reduce { a, b -> a + b }
            }
        }
    }
}

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
interface TwoDMatrixOps<Number> : MatrixOps<TwoD<Number, *>, Number> {
    override val numberOps: NumberOps<Number>
    override fun create(m: Int, n: Int, init: ((Int, Int) -> Number)?): TwoD<Number, *>

    override val TwoD<Number, *>.m: Int get() = size1
    override val TwoD<Number, *>.n: Int get() = size2
    override fun TwoD<Number, *>.get(i: Int, j: Int): Number = get(i, j)
    override fun TwoD<Number, *>.set(i: Int, j: Int, value: Number) = set(i, j, value)
}

inline fun <Number> twoDMatrixOps(
    numberOps: NumberOps<Number>,
    crossinline create: (Int, Int, init: ((Int, Int) -> Number)?) -> TwoD<Number, *>
) =
    object : TwoDMatrixOps<Number> {
        override val numberOps: NumberOps<Number> get() = numberOps
        override fun create(m: Int, n: Int, init: ((Int, Int) -> Number)?): TwoD<Number, *> =
            create(m, n, init)
    }

interface MatrixAndVectorOps<Vector, Number> {
    // TODO
}