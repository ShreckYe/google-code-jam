fun main() {
    val T = readLine()!!.toInt()
    repeat(T, ::case)
}

fun case(t: Int) {
    val N = readLine()!!.toInt()
    val Vs = readLine()!!.splitToSequence(' ').map { it.toInt() }.toList()

    val array = Vs.toIntArray()
    troubleSort(array)
    val firstErrorIndex = array.asSequence().zipWithNext().indexOfFirst { it.first > it.second }

    val y = if (firstErrorIndex == -1) "OK" else firstErrorIndex
    println("Case #${t + 1}: $y")
}

// 3 ≤ N
fun troubleSort(array: IntArray) {
    do {
        var changed = false
        for (i in 0 until array.size - 2)
            if (array[i] > array[i + 2]) {
                val t = array[i]
                array[i] = array[i + 2]
                array[i + 2] = t

                changed = true
            }
    } while (changed)
}

main()