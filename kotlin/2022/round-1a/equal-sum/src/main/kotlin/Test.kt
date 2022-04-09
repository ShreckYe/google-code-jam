import kotlin.random.Random

fun main() {
    for (nn in listOf(10, 20, 30)) {
        val bbs = List(nn) { Random.nextLong(1, 1000000000) }
        val aas = generateAas(nn)
        val expectedSum = (bbs.sum() + aas.sum()) / 2


        val bfAns = try {
            bruteForce(bbs, expectedSum, nn)
        } catch (e: Exception) {
            null
        }
        val solveAns = try {
            solve(nn, bbs, expectedSum)
        } catch (e: Exception) {
            null
        }

        if ((bfAns !== null) xor (solveAns !== null))
            System.err.println("bbs=$bbs\naas$aas\n$expectedSum\nbfAns=$bfAns\nbfAnsSum=${bfAns?.sum()}\nsolveAns=$solveAns\nsolveAnsSum=${solveAns?.sum()}")
    }
}