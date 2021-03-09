package test

fun main() {
    val a = readLine()!!
    val b = readLine()!!
    println((a zip b).asSequence().withIndex().filter { (i, v) -> v.first != v.second }.map { it.index }.toList())
}