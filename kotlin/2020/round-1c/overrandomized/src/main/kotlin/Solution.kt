fun main() {
    val T = readLine()!!.toInt()
    repeat(T) { t ->
        val U = readLine()!!.toInt()
        val leSets = Array(10) { HashSet<Char>(it + 1) }
        val nonZeroSet = HashSet<Char>(9)
        repeat(1000) {
            val (Q, R) = readLine()!!.split(' ')
            val q = Q.reversed().map { it - '0' }
            val ql = q.size
            val r = R.reversed()
            val rl = r.length
            if (rl == ql)
                leSets[q[ql - 1]].add(r[rl - 1])

            for (i in 0 until rl - 1)
                leSets[9].add(r[i])

            if (rl == 1)
                nonZeroSet.add(r[0])
        }

        val allSet = HashSet<Char>(10)
        for (s in leSets) allSet.addAll(s)

        val da = CharArray(10)
        da[0] = (allSet - nonZeroSet).first()
        for (i in 1 until 10)
            da[i] = (leSets[i] - leSets[i - 1]).first()
        val y = String(da)

        println("Case #${t + 1}: $y")
    }
}