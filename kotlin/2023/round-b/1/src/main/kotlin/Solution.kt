fun main() {
    val tt = readln().toInt()
    repeat(tt, ::testCase)
}

fun testCase(tti: Int) {
    val nn = readln().toInt()
    val aas = readln().splitToSequence(' ').map { it.toLong() }.toList()
    val (lla, rra, llb, rrb) = readln().splitToSequence(' ').map { it.toInt() }.toList()

    val prefixSums = aas.scan(0L) { acc, aa -> acc + aa }
    val medium = prefixSums.last() / 2

    fun binarySearch(l: Int, r: Int) {
        val mid = (l + r) / 2
        if (prefixSums[mid] <= medium)
            TODO()

    }

    println("Case #${tti + 1}: ${TODO()}")
}
