package test

fun main() {
    for (n in 1..5) {
        println("n = $n")
        val latinMatrices = latinMatrices(n)
        println("Number: ${latinMatrices.count()}")
        val traces = latinMatrices.map { it.trace() }.toSet().sorted()
        println("Possible traces: $traces")
    }
}