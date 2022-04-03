import kotlin.math.roundToLong

// very naive solution

fun main() {
    val t = readLine()!!.toInt()
    repeat(t, ::testCase)
}

fun testCase(ti: Int) {
    val (n, k) = readLine()!!.splitToSequence(' ').map { it.toInt() }.toList()

    val numsPassages = Array<Int?>(n) { null }
    fun readAndSavenumPassages() {
        val (r, p) = readLine()!!.splitToSequence(' ').map { it.toInt() }.toList()
        numsPassages[r - 1] = p
    }

    var nextRoom = 0
    repeat(k) {
        readAndSavenumPassages()

        //println("W")
        while (numsPassages[nextRoom] !== null)
            nextRoom++

        println("T ${nextRoom + 1}")
    }
    readAndSavenumPassages()

    val seen = numsPassages.filterNotNull()

    val y = ((seen.asSequence().map { it.toLong() }.sum() * n).toDouble() / seen.size / 2).roundToLong()
    println("E $y")
}
