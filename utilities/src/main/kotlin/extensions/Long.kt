@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package extensions

// Base 10
fun Long.digits(): List<Int> {
    var remainder = this

    val result = mutableListOf<Int>()

    do {
        result.add(index = 0, element = (remainder % 10).toInt())
        remainder /= 10
    } while (remainder > 0)

    return result
}

// https://en.wikipedia.org/wiki/Modular_multiplicative_inverse
// https://en.wikipedia.org/wiki/Extended_Euclidean_algorithm#Modular_integers
/**
 * Given `a` ([this]) and [n], returns the solution of `at ≡ 1 mod n`.
 */
fun Long.modularInverse(n: Long): Long? {
    var t = 0L
    var newT = 1L
    var r = n
    var newR = if (this < 0) this.nonNegativeRem(n) else this

    while (newR != 0L) {
        val quotient = r / newR
        val oldT = t
        t = newT
        newT = oldT - quotient * newT
        val oldR = r
        r = newR
        newR = oldR - quotient * newR
    }

    if (r > 1) return null
    if (t < 0) return t + n
    return t
}

fun Long.nonNegativeRem(other: Long): Long {
    var result = rem(other)
    if (result < 0) result += other
    return result
}
