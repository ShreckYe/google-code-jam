fun <T : Any> tryOrNull(block: () -> T): T? =
    try {
        block()
    } catch (t: Throwable) {
        t.printStackTrace()
        null
    }
