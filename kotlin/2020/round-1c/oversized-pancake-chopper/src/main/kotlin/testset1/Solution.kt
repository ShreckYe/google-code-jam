package testset1

fun main() {
    val T = readLine()!!.toInt()
    repeat(T) { t ->
        val (N, D) = readLine()!!.split(' ').map(String::toInt)
        val A = readLine()!!.split(' ').map(String::toLong)

        val DL = D.toLong()
        val aSeqWI = A.asSequence().withIndex()
        val twoSlicesY = aSeqWI.flatMap { ia1 -> aSeqWI.map { ia2->ia1 to ia2 } }
            .filter { it.first.index != it.second.index }
            .map { (ia1, ia2) ->
                val (i1, a1) = ia1
                val (i2, a2) = ia2
                val d = gcd(a1, a2)
                val s = (a1 + a2) / d
                if (s == DL) D - 2
                else if (s < DL) {
                    // s == 2, DL == 3
                    val oa = aSeqWI.filter { (i, _) -> i != i1 && i != i2 }.map { it.value }
                    if (oa.any { it == d }) 0
                    else if (oa.any { it > d }) 1
                    else Int.MAX_VALUE
                } else Int.MAX_VALUE
            }.min() ?: Int.MAX_VALUE
        val y = twoSlicesY.coerceAtMost(D - 1)

        println("Case #${t + 1}: $y")
    }
}

tailrec fun gcd(x: Long, y: Long): Long =
    if (y == 0L) x else gcd(y, x % y)