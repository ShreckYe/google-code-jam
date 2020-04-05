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
                fun scheduledToC() {
                    assign[i] = 'C'
                    lastCE = e
                }

                fun scheduledToJ() {
                    assign[i] = 'J'
                    lastJE = e
                }

                if (!if (lastCE >= lastJE)
                        preferToScheduleToPersonWithLaterLastE(s, lastCE, ::scheduledToC, lastJE, ::scheduledToJ)
                    else
                        preferToScheduleToPersonWithLaterLastE(s, lastJE, ::scheduledToJ, lastCE, ::scheduledToC)
                )
                    return "IMPOSSIBLE"
            }
            return String(assign)
        }

        println("Case #${t + 1}: ${schedule()}")
    }
}

data class Task(val s: Int, val e: Int)

inline fun preferToScheduleToPersonWithLaterLastE(
    s: Int,
    laterLastE: Int, scheduledToLaterLastE: () -> Unit,
    earlierLastE: Int, scheduledToEarlierLastE: () -> Unit
): Boolean =
    if (s >= laterLastE) {
        scheduledToLaterLastE()
        true
    } else if (s >= earlierLastE) {
        scheduledToEarlierLastE()
        true
    } else
        false