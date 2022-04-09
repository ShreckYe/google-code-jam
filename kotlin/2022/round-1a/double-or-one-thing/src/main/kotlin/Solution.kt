fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    val ss = readLine()!!

    val stringBuilder = StringBuilder(ss.length * 2)
    var i2 = 0
    for (c in ss) {
        while (i2 in ss.indices && c == ss[i2]) i2++

        stringBuilder.append(c)
        if (i2 in ss.indices && c < ss[i2]) stringBuilder.append(c)
    }

    println("Case #${ti + 1}: ${stringBuilder.toString()}")
}
