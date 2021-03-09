fun main() {
    val T = readLine()!!.toInt()
    repeat(T) { t ->
        val N = readLine()!!.toInt()
        val tasks = (0 until N).map {
            readLine()!!.splitToSequence(' ').map(String::toInt).toList().let { Task(it[0], it[1]) }
        }

        val sortedTasks = tasks.withIndex().sortedBy { it.value.s }
        fun schedule(): String {
            val assign = CharArray(tasks.size)
            var lastCE = 0
            var lastJE = 0

            for ((i, task) in sortedTasks) {
                val (s, e) = task

                if (s >= lastCE) {
                    assign[i] = 'C'
                    lastCE = e
                } else if (s >= lastJE) {
                    assign[i] = 'J'
                    lastJE = e
                } else
                    return "IMPOSSIBLE"
            }
            return String(assign)
        }

        println("Case #${t + 1}: ${schedule()}")
    }
}

data class Task(val s: Int, val e: Int)