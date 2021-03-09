fun main() {
    val T = readLine()!!.toInt()
    repeat(T) { t ->
        val (R, S) = readLine()!!.splitToSequence(' ').map(String::toInt).toList()

        val ans = sortOps(R, S).toList()

        println("Case #${t + 1}: ${ans.size}\n${ans.asSequence().map { "${it.a} ${it.b}" }.joinToString("\n")}")
    }
}

data class Op(val a: Int, val b: Int)

fun sortOps(r: Int, s: Int): Sequence<Op> =
    if (r == 1 || s == 1) emptySequence()
    else {
        val numOps = s - 1

        val rOps = ArrayList<Op>(s - 1)
        var si = s - 1
        var front = 0
        var swapPos = r * s - 1
        repeat(numOps) {
            val a = front + r * si
            val b = swapPos - a
            rOps.add(Op(a, b))

            si--
            front += b
            swapPos--
        }

        rOps.asSequence() + sortOps(r - 1, s)
    }