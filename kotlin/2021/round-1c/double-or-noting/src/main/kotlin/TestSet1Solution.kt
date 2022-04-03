fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    val (s, e) = readLine()!!.splitToSequence(' ').map { it.map { it == '1' } }.toList()

    val minNumOps = search(listOf(s), e, 8)
    println("Case #${ti + 1}: ${minNumOps ?: "IMPOSSIBLE"}")
}

fun List<Boolean>.double() =
    (this + false)

fun List<Boolean>.not() =
    map { !it }.dropWhile { !it }.ifEmpty { listOf(false) }

fun search(ss: List<List<Boolean>>, e: List<Boolean>, depth: Int): Int? =
    when {
        e in ss -> 0
        depth == 0 -> null
        else -> {
            search(ss.flatMap { listOf(it.double(), it.not()) }, e, depth - 1)
                ?.let { it + 1 }
        }
    }

fun List<Boolean>.toBitString() =
    joinToString("") { if (it) "1" else "0" }