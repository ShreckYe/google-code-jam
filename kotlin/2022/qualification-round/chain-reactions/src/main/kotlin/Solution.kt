fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    val nn = readLine()!!.toInt()
    val ffs = readLine()!!.splitToSequence(' ').map { it.toInt() }.toList()
    val pps = readLine()!!.splitToSequence(' ').map { it.toInt() }.toList()

    val outs = pps.map { if (it != 0) it - 1 else null }

    val inss = List(nn) { mutableListOf<Int>() }
    for (i in 0 until nn) {
        val out = outs[i]
        if (out !== null)
            inss[out].add(i)
    }

    val sinks = (0 until nn).filter { outs[it] === null }

    val y = sinks.sumOf {
        dfsMaxSumMaxFun(inss, ffs, it).sumMaxFun
    }

    println("Case #${ti + 1}: $y")
}

data class SelectedIn(val inV: Int?, val maxFun: Int, val sumMaxFun: Long)

// We are searching a reversed forest instead of a DAG.
fun dfsMaxSumMaxFun(inss: List<List<Int>>, fs: List<Int>, v: Int): SelectedIn {
    val f = fs[v]
    val ins = inss[v]

    return if (ins.isEmpty()) {
        val fLong = f.toLong()
        SelectedIn(null, f, fLong)
    } else {
        val inSIns = ins.map { dfsMaxSumMaxFun(inss, fs, it) }
        val min = inSIns.withIndex().minByOrNull { it.value.maxFun }!!
        val minValue = min.value
        if (f <= minValue.maxFun)
            SelectedIn(ins[min.index], minValue.maxFun, inSIns.sumOf { it.sumMaxFun })
        else
            SelectedIn(ins[min.index], f, inSIns.sumOf { it.sumMaxFun } + f - minValue.maxFun)
    }
}
