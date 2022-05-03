import kotlin.experimental.xor
import kotlin.system.exitProcess

fun main() {
    val equivalentClasses = findEquivalentClasses()
    val graph = buildGraph(equivalentClasses, getECIndexArray(equivalentClasses))
    println(graph)
    val bests = computeBests(equivalentClasses, graph)
    println(bests)

    /*val t = readLine()!!.toInt()
    repeat(t, ::testCase)*/
}

fun testCase(ti: Int) {
    repeat(300) {
        println("01010101")
        val nn = readLine()!!.toInt()
        if (nn == 0)
            return
        if (nn == -1)
            exitProcess(0)
    }
}

infix fun Byte.isEquivalentTo(that: Byte) =
    (0 until 8).any {
        rotateRight(it) == that
    }

fun findEquivalentClasses(): List<List<Byte>> {
    val equivalentClasses = mutableListOf<MutableList<Byte>>()

    for (bInt in Byte.MIN_VALUE..Byte.MAX_VALUE) {
        val b = bInt.toByte()
        val equivalentClass = equivalentClasses.find { equivalentClass ->
            equivalentClass.first() isEquivalentTo b
        }

        if (equivalentClass !== null)
            equivalentClass.add(b)
        else
            equivalentClasses.add(mutableListOf(b))
    }

    return equivalentClasses
}

fun getECIndexArray(equivalentClasses: List<List<Byte>>): IntArray {
    val array = IntArray(256)
    for ((i, equivalentClass) in equivalentClasses.withIndex())
        for (b in equivalentClass)
            array[b.toUByte().toInt()] = i
    return array
}

data class OutXorEdges(/*val xorIndex : Int,*/ val outs: Set<Int>)

fun buildGraph(equivalentClasses: List<List<Byte>>, ecIndexArray: IntArray): List<List<OutXorEdges>> =
    equivalentClasses.map {
        val v = it.first()
        equivalentClasses.map {
            val xorRight = it.first()
            OutXorEdges((0 until 8).map {
                val result = (v xor (xorRight.rotateRight(it)))
                ecIndexArray[result.toUByte().toInt()]
            }.toSet())
        }
    }

data class BestEdgeAndDepth(val xorRightECIndex: Int, val depth: Int)

fun computeBests(equivalentClasses: List<List<Byte>>, graph: List<List<OutXorEdges>>): List<BestEdgeAndDepth> {
    val size = graph.size
    val cache = MutableList<BestEdgeAndDepth?>(size) { null }
    val zeroV = equivalentClasses.indexOf(listOf(0))
    println("zeroV:" + zeroV)
    cache[zeroV] = BestEdgeAndDepth(-1, 0)
    for (v in 0 until size)
        dfsBest(graph, v, cache)
    return cache.map { it!! }
}

fun dfsBest(
    graph: List<List<OutXorEdges>>, v: Int, cache: MutableList<BestEdgeAndDepth?>, searched: BooleanArray
): BestEdgeAndDepth = run {
    println(v)
    if (cache[v] !== null) cache[v]!!
    else {
        val bestOut = graph[v].map {
            it.outs.maxOf { out ->
                dfsBest(graph, out, cache).depth
            }
        }.withIndex().minByOrNull { it.value }!!
        val best = BestEdgeAndDepth(bestOut.index, bestOut.value)

        cache[v] = best
        best
    }
}
