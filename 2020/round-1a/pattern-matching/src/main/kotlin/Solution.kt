fun main() {
    val T = readLine()!!.toInt()
    repeat(T) { t ->
        val N = readLine()!!.toInt()
        val P = (0 until N).map { readLine()!! }
        println("Case #${t + 1}: ${case(N, P)}")
    }
}

fun case(N: Int, P: List<String>): String? =
    getName(P.map { it.toList() })

fun getName(patterns: List<List<Char>>): String? =
    getNameSequence(patterns)?.joinToString("") ?: "*"

fun getNameSequence(patterns: List<List<Char>>): Sequence<Char>? {
    if (patterns.any { it.isEmpty() })
        return if (patterns.asSequence().filter { it.isNotEmpty() }.all { it.all { it == '*' } }) "".asSequence() else null

    // All are not empty
    val heads = patterns.map { it[0] }
    if (heads.zipWithNext().all { it.first == it.second }) {
        val head = heads[0]
        if (head != '*') return getNameSequence(patterns.map { it.subList(1, it.size) })?.let { sequenceOf(head) + it }
    } else {
        val distinctLetters = heads.asSequence().filter { it != '*' }.distinct().toList()
        val nDistinctLetters = distinctLetters.count()
        if (nDistinctLetters == 1)
            return getNameSequence(patterns.map { if (it[0] != '*') it.subList(1, it.size) else it })
                ?.let { sequenceOf(distinctLetters[0]) + it }
        else if (nDistinctLetters > 1) return null
    }

    // All heads are asterisks
    if (patterns.any { it.last() != '*' })
        return getNameSequence(patterns.map { it.reversed() })?.run { toList().asReversed().asSequence() }

    // All patterns start and end with asterisks
    val patternStrings = patterns.map { it.subList(1, it.size).asSequence().filter { it != '*' } }
    return patternStrings.asSequence().flatten()
}