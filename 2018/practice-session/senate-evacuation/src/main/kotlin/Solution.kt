fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    // 2 ≤ N
    val n = readLine()!!.toInt()
    val ps = readLine()!!.splitToSequence(' ').map { it.toInt() }.toList()

    val psArray = ps.toIntArray()
    val plan = ArrayList<List<Int>>(psArray.sum())
    while (true) {
        val sortedDescPs = psArray.withIndex().sortedByDescending { it.value }
        val (firstMaxP, secondMaxP) = sortedDescPs
        if (firstMaxP.value == 0) break

        fun evacuateOneFromFirst() {
            plan.add(listOf(firstMaxP.index))
            psArray[firstMaxP.index]--
        }

        if (firstMaxP.value > secondMaxP.value)
            evacuateOneFromFirst()
        else /*if (firstMaxP.value == secondMaxP.value)*/ {
            val thirdMaxP = sortedDescPs.getOrNull(2)
            if (thirdMaxP === null || secondMaxP.value > thirdMaxP.value) {
                plan.add(listOf(firstMaxP.index, secondMaxP.index))
                psArray[firstMaxP.index]--
                psArray[secondMaxP.index]--
            } else /*if (secondMaxP.value == thirdMaxP.value)*/
                evacuateOneFromFirst()
        }
    }

    val y = plan.joinToString(" ") { it.joinToString("") { ('A' + it).toString() } }
    println("Case #${ti + 1}: $y")
}