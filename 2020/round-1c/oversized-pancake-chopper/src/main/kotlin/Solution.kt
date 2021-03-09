import java.lang.Integer.max

fun main() {
    val T = readLine()!!.toInt()
    repeat(T) { t ->
        val (N, D) = readLine()!!.split(' ').map(String::toInt)
        val A = readLine()!!.split(' ').map(String::toLong)

        // TODO
        val ra = A.map { it.toInt() }

        // Partial sum max size dynamic programming
        val psmsdp = Array(N + 1) { IntArray(D + 1) }
        psmsdp[0][0] = 0
        for (d in 1..D) psmsdp[0][d] = Int.MIN_VALUE

        for (n in 1..N)
            for (d in 0..D)
                psmsdp[n][d] = max(psmsdp[n - 1][d],
                    psmsdp[n - 1].getOrElse((d - ra[n - 1]).let { if (it != Int.MIN_VALUE) it + 1 else Int.MIN_VALUE }) { Int.MIN_VALUE })
    }
}