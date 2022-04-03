fun main() {
    val T = readLine()!!.toInt()
    repeat(T, ::case)
}

val csList = "CS".toList()
//val scList = "SC".toList()

fun case(t: Int) {
    val lineInputs = readLine()!!.split(' ')
    val D = lineInputs[0].toInt()
    val P = lineInputs[1]

    val p = P.toCharArray()
    var countSwaps = 0
    var impossible = false
    while (computeDamage(p) > D) {
        val i = p.asSequence().windowed(2).lastIndexOf(csList)
        if (i == -1) {
            impossible = true
            break
        }
        p[i] = 'S'; p[i + 1] = 'C'
        countSwaps++
    }

    val y = if (impossible) "IMPOSSIBLE" else countSwaps
    println("Case #${t + 1}: $y")
}

fun computeDamage(p: CharArray): Int {
    var strength = 1
    var damage = 0
    for (instruction in p) {
        when (instruction) {
            'C' -> strength *= 2
            'S' -> damage += strength
            else -> throw IllegalArgumentException()
        }
    }

    return damage
}