sealed class Either<out A, out B>
data class Left<out A, out B>(val value: A) : Either<A, B>()
data class Right<out A, out B>(val value: B) : Either<A, B>()

fun <A, B, C> Either<A, B>.fold(fa: (A) -> C, fb: (B) -> C): C =
    when (this) {
        is Left<A, B> -> fa(value)
        is Right<A, B> -> fb(value)
    }