fun bitSubsets(size: Int): Sequence<Int> {
    require(size <= 30)
    return (0 until (1 shl size)).asSequence()
}

fun bitSubsets2(size: Int): Sequence<Int> {
    require(size <= 30)
    return generateSequence({ 0 }, { it + 1 }).take(1 shl size)
}

fun <T> List<T>.allSubsets(): Sequence<List<T>> =
    bitSubsets(size).map { s -> filterIndexed { i, _ -> (s and (1 shl i)) != 0 } }

fun <T> List<T>.all2Partitions(): Sequence<Pair<List<T>, List<T>>> =
    bitSubsets(size).map { s ->
        filterIndexed { i, _ -> (s and 1 shl i) != 0 } to
                filterIndexed { i, _ -> (s and 1 shl i) == 0 }
    }