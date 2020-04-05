fun main() {
    val T = readLine()!!.toInt()
    repeat(T) { t ->
        val S = readLine()!!

        val digits = S.map { it - '0' }.toIntArray()
        val builder = StringBuilder(S.length)
        var lastDigit = 0
        for (digit in digits) {
            val numOP = digit - lastDigit
            if (numOP > 0)
                repeat(numOP) { builder.append('(') }
            else if (numOP < 0)
                repeat(-numOP) { builder.append(')') }
            builder.append('0' + digit)
            lastDigit = digit
        }
        repeat(lastDigit) { builder.append(')') }

        val `S'` = builder.toString()
        
        println("Case #${t + 1}: $`S'`")
    }
}