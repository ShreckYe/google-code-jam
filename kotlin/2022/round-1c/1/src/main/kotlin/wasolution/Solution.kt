package wasolution

fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    val nn = readLine()!!.toInt()
    val sss = readLine()!!.split(' ')

    val joineds = tryJoinAll(sss)
    val joined = joineds.joinToString("")

    val y = if (isValid(joined)) joined else null
    println("Case #${ti + 1}: ${y ?: "IMPOSSIBLE"}")
}

fun tryJoinAll(sss: List<String>): List<String> =
    if (sss.isEmpty() || sss.size == 1) sss
    else {
        var currentSsPair = sss.first() to sss.subList(1, sss.size)

        while (true) {
            val temp = tryJoin(currentSsPair.first, currentSsPair.second) ?: break
            currentSsPair = temp
        }

        listOf(currentSsPair.first) + tryJoinAll(currentSsPair.second)
    }

fun <T> List<T>.remove(index: Int): List<T> =
    subList(0, index) + subList(index + 1, size)

fun tryJoin(ss: String, ssList: List<String>): Pair<String, List<String>>? {
    for ((i, ss2) in ssList.withIndex()) {
        val joined =
            if (ss.last() == ss2.first())
                ss + ss2
            else if (ss2.last() == ss.first())
                ss2 + ss
            else continue
        return joined to ssList.remove(i)
    }
    return null
}

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