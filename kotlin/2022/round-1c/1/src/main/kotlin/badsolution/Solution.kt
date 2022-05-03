package badsolution

fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    val nn = readLine()!!.toInt()
    val sss = readLine()!!.split(' ')

    val y = solve(sss)
    println("Case #${ti + 1}: ${y ?: "IMPOSSIBLE"}")
}

class StringRef(value: String) {

}

fun solve(sss: List<String>): String? {
    val letterSsMap = Array<String?>(128) { null }

    for (ss in sss) {
        if (!isValid(ss))
            return null
        for (c in ss.toSet()) {
            println("State: $ss ${ss.toSet()} $c")
            println(letterSsMap.withIndex().filter { it.value !== null }.map { it.index.toChar() to it.value })
            val ss2 = letterSsMap[c.code]
            if (ss2 === null)
                letterSsMap[c.code] = ss
            else {
                val joined =
                    if (ss.last() == ss2.first())
                        ss + ss2
                    else if (ss2.last() == ss.first())
                        ss2 + ss
                    else
                        return null
                println("Joined: $joined ${isValid(joined)}")
                if (isValid(joined)) {
                    for (c in joined.toSet())
                        letterSsMap[c.code] = joined
                    break
                } else
                    return null
            }
        }
    }

    return letterSsMap.asSequence().filterNotNull().joinToString("")
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