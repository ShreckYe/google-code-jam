fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    val nn = readLine()!!.toInt()
    val sss = readLine()!!.split(' ')

    val validJoins = sss.permutations().map { it.joinToString("") }.filter { isValid(it) }

    val y = validJoins.firstOrNull()
    println("Case #${ti + 1}: ${y ?: "IMPOSSIBLE"}")
}

fun <T> List<T>.remove(index: Int): List<T> =
    subList(0, index) + subList(index + 1, size)

fun <T> List<T>.permutations(): Sequence<List<T>> =
    if (isEmpty()) sequenceOf(this)
    else asSequence().flatMapIndexed { i, e -> remove(i).permutations().map { listOf(e) + it } }

fun isValid(ss: String): Boolean {
    val letterExistentMap = BooleanArray(128) { false }
    var i = 0
    while (i < ss.length) {
        val c = ss[i]
        if (letterExistentMap[c.code])
            return false
        else {
            letterExistentMap[c.code] = true
            @Suppress("ControlFlowWithEmptyBody")
            while (ss.getOrNull(++i) == c);
        }
        //i++ // This is not necessary!
    }

    return true
}