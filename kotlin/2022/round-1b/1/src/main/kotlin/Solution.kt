fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    val nn = readLine()!!.toInt()
    val dd = readLine()!!.splitToSequence(' ').map { it.toInt() }.toList()

    var left = 0
    var right = dd.lastIndex
    var currentMaxDeliciousnessLevel = 0
    var count = 0
    while (left <= right)
        if (dd[left] < currentMaxDeliciousnessLevel)
            left++
        else if (dd[right] < currentMaxDeliciousnessLevel)
            right--
        else {
            if (dd[left] <= dd[right])
                currentMaxDeliciousnessLevel = dd[left++]
            else
                currentMaxDeliciousnessLevel = dd[right--]
            count++
        }

    val y = count
    println("Case #${ti + 1}: $y")
}
