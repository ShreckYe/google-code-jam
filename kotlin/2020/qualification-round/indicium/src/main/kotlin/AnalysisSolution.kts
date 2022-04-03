fun main() {
    val T = readLine()!!.toInt()
    repeat(T, ::case)
}

fun case(t: Int) {
    val (N, K) = readLine()!!.splitToSequence(' ').map(String::toInt).toList()

    // N ≤ K ≤ N ^ 2
    // 1 ≤ a ≤ N
    val a = K / N
    val r = K % N

    TODO()

    //println("Case #${t + 1}: ${if (answerMatrix == null) "IMPOSSIBLE" else "POSSIBLE\n${answerMatrix}"}")
}

main()