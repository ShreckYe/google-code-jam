fun main() {
    println((0 until 100).map {
        it to it.toString().toList().plusOne().joinToString("")
    })
}