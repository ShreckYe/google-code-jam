fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    val (r, c) = readLine()!!.splitToSequence(' ').map { it.toInt() }.toList()

    println("Case #${ti + 1}:")

    print("..")
    printInterleavedLine(c - 1, '+', '-')
    print("..")
    printInterleavedLine(c - 1, '|', '.')

    repeat(r - 1) {
        printInterleavedLine(c, '+', '-')
        printInterleavedLine(c, '|', '.')
    }
    printInterleavedLine(c, '+', '-')
}

fun printInterleavedLine(c: Int, c1: Char, c2: Char) {
    var c1OrC2 = true
    repeat(c * 2 + 1) {
        print(if (c1OrC2) c1 else c2)
        c1OrC2 = !c1OrC2
    }
    println()
}
