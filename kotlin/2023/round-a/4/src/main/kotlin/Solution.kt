import kotlin.math.floor
import kotlin.math.sqrt

fun main() {
    val tt = readln().toInt()
    repeat(tt, ::testCase)
}

fun testCase(tti: Int) {
    val nn = readln().toLong()
    val index = nn - 1
    // Let's assume that i stars from 0
    val `i-1` = floor(sqrt((index / 26 + 0.25) * 2) - 0.5).toLong()

    val numBeforeThisGroup = 26 * (`i-1` * (`i-1` + 1) / 2)
    val indexInGroup = index - numBeforeThisGroup
    val character = 'A' + (indexInGroup / (`i-1` + 1)).toInt()

    println("Case #${tti + 1}: $character")
}
