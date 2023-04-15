fun main() {
    val tt = readln().toInt()
    repeat(tt, ::testCase)
}

fun testCase(tti: Int) {
    val (mm, rr, nn) = readln().splitToSequence(' ').map { it.toInt() }.toList()
    val xxs = readln().splitToSequence(' ').map { it.toInt() }.toList()

    var i = 0
    var covered = 0
    var count = 0
    var possible = true

    while (covered < mm) {
        val xxsThatCover = xxs.subList(i, xxs.size).takeWhile { it - rr <= covered }
        val lastXXThatCovers  = xxsThatCover.lastOrNull()
        i += xxsThatCover.size

        if (lastXXThatCovers === null) {
            possible = false
            break
        }

        count++
        covered = lastXXThatCovers + rr
    }


    println("Case #${tti + 1}: ${if (possible) count else "IMPOSSIBLE"}")
}
