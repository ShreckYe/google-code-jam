fun main() {
    val tt = readln().toInt()
    repeat(tt, ::testCase)
}

fun testCase(tti: Int) {
    val ccs = readln()

    // Group nearby same elements
    class Group(val selection: Char, var num: Int)

    var previous: Char? = null
    val groups = mutableListOf<Group>()
    for (selection in ccs) {
        if (selection == previous) {
            groups.last().num++
        } else {
            groups.add(Group(selection, 1))
            previous = selection
        }
    }
    val minNumChanges =
        if (groups.size == 1) {
            (groups.single().num + 1) / 2
        } else {
            if (groups.first().selection == groups.last().selection) {
                val last = groups.removeLast()
                groups.first().num += last.num
            }

            groups.sumOf { it.num / 2 }
        }
    println("Case #${tti + 1}: $minNumChanges")
}
