fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    val (d, n) = readLine()!!.splitToSequence(' ').map { it.toInt() }.toList()
    class Horse(val k: Int, val s: Int)

    val horses = List(n) {
        val (k, s) = readLine()!!.splitToSequence(' ').map { it.toInt() }.toList()
        Horse(k, s)
    }

    val latestHorseTime = horses.maxOf { (d - it.k).toDouble() / it.s }
    val y = d.toDouble() / latestHorseTime

    println("Case #${ti + 1}: $y")
}