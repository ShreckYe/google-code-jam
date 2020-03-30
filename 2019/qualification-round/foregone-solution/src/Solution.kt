fun main() {
    val T = readLine()!!.toInt()
    repeat(T) { i ->
        val N = readLine()!!

        val digitPairs = N.map {
            if (it == '4') '2' to '2' else it to '0'
        }

        fun String.toReadableNumber(): String = trimStart('0').let { if (it.isEmpty()) "0" else it }
        val A = digitPairs.map { it.first }.joinToString("").toReadableNumber()
        val B = digitPairs.map { it.second }.joinToString("").toReadableNumber()

        println("Case #${i + 1}: $A $B")
    }
}