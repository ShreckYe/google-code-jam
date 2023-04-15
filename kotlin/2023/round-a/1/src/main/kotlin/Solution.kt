fun main() {
    val tt = readln().toInt()
    repeat(tt, ::testCase)
}

fun testCase(tti: Int) {
    val dds = readln().splitToSequence(' ').map { it.toInt() }.toList()
    val nn = readln().toInt()
    val sss = List(nn) { readln() }

    val encodedSss = sss.map {
        it.map { dds[it - 'A'] }
    }

    val y = encodedSss.toSet().size != encodedSss.size

    println("Case #${tti + 1}: ${if (y) "YES" else "NO"}")
}
