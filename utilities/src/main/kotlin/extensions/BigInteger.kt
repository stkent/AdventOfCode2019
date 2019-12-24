package extensions

import java.math.BigInteger

// https://en.wikipedia.org/wiki/Modular_multiplicative_inverse
// https://en.wikipedia.org/wiki/Extended_Euclidean_algorithm#Modular_integers
/**
 * Given `a` ([this]) and [n], returns the solution of `at ≡ 1 mod n`.
 */
fun BigInteger.modularInverse(n: BigInteger): BigInteger? {
    var t = 0.toBigInteger()
    var newT = 1.toBigInteger()
    var r = n
    var newR = if (this < 0.toBigInteger()) this.nonNegativeRem(n) else this

    while (newR != 0.toBigInteger()) {
        val quotient = r / newR
        val oldT = t
        t = newT
        newT = oldT - quotient * newT
        val oldR = r
        r = newR
        newR = oldR - quotient * newR
    }

    if (r > 1.toBigInteger()) return null
    if (t < 0.toBigInteger()) return t + n
    return t
}

fun BigInteger.nonNegativeRem(other: BigInteger): BigInteger {
    var result = rem(other)
    if (result < 0.toBigInteger()) result += other
    return result
}
