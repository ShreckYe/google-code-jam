import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    val a = readLine()!!.toFloat()

    // `op` stands for "original point" and `hp` stands for "highest point".
    val op = doubleArrayOf(0.5, 0.5, 0.5)
    val hpy = a.toDouble() / 2 // by the cube shadow theorem
    val hpxz = sqrt((square(0.5) * 3 - square(hpy)) / 2)
    val hp = doubleArrayOf(hpy, hpxz, hpxz)

    val cosTheta = ((hp * op) / (hp.magnitude() * op.magnitude())).coerceAtMost(1.0)
    val theta = acos(cosTheta)

    val axis = doubleArrayOf(-sqrt(1.0 / 2), 0.0, sqrt(1.0 / 2))
    val originalFaceCenters = listOf(
        doubleArrayOf(0.5, 0.0, 0.0),
        doubleArrayOf(0.0, 0.5, 0.0),
        doubleArrayOf(0.0, 0.0, 0.5)
    )

    val rotationMatrix = rotationMatrix(axis, theta)
    val faceCenters = originalFaceCenters.map { rotationMatrix * it }

    println("Case #${ti + 1}:\n${faceCenters.joinToString("\n") { it.joinToString(" ") }}")
}

fun square(x: Double): Double = x * x

// vector operations

operator fun DoubleArray.times(that: DoubleArray): Double {
    require(size == that.size)
    return (this zip that).sumByDouble { it.first * it.second }
}

fun DoubleArray.magnitude() =
    sqrt(sumByDouble(::square))

// see: https://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle
fun rotationMatrix(u: DoubleArray, theta: Double): Array<DoubleArray> {
    val (ux, uy, uz) = u
    val cosTheta = cos(theta)
    val sinTheta = sin(theta)
    fun firstRow(ux: Double, uy: Double, uz: Double) =
        doubleArrayOf(
            cosTheta + square(ux) * (1 - cosTheta),
            ux * uy * (1 - cosTheta) - uz * sinTheta,
            ux * uz * (1 - cosTheta) + uy * sinTheta
        )
    return arrayOf(
        firstRow(ux, uy, uz),
        firstRow(uy, uz, ux).let { doubleArrayOf(it[2], it[0], it[1]) },
        firstRow(uz, ux, uy).let { doubleArrayOf(it[1], it[2], it[0]) }
    )
}

operator fun Array<DoubleArray>.times(thatVector: DoubleArray) =
    map { it * thatVector }.toDoubleArray()