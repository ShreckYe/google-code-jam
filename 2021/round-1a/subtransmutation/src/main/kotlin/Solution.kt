fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    val (n, a, b) = readLine()!!.splitToSequence(' ').map { it.toInt() }.toList()
    val us = readLine()!!.splitToSequence(' ').map { it.toInt() }.toList()

    val usArray = IntArray(us.size + b) { us.getOrElse(it) { 0 } }
    val size = usArray.size
    var i = 0
    var lastRoundSum: Int = Int.MAX_VALUE
    val y: Int?
    while (true) {
        val i1 = i % size
        //println(List(i) { 0 } + usArray.asList().drop(i1))
        val i2 = (i + (b - a)) % size
        val i1b = (i1 + b) % size
        if (usArray[i1] <= usArray[i2]) {
            val u1 = usArray[i1]
            usArray[i1] = 0
            usArray[i2] -= u1
            usArray[i1b] += u1
        } else {
            val u2 = usArray[i2]
            val u1mu2 = usArray[i1] - u2

            usArray[i1] = 0
            usArray[i2] = 0
            val i1a = (i1 + a) % size
            usArray[i1a] += u1mu2
            usArray[i1b] += u2
        }

        //println(List(i) { 0 } + usArray.asList().drop(i1))
        val sum = usArray.sum()
        if (sum == 1) {
            y = (i / n * n) + usArray.indexOfFirst { it == 1 } + 1
            break
        }

        i++
        if (i % size == 0)
            if (sum < lastRoundSum)
                lastRoundSum = sum
            else {
                y = null
                break
            }
    }

    println("Case #${ti + 1}: ${y ?: "IMPOSSIBLE"}")
}