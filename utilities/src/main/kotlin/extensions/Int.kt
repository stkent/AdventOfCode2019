@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package extensions

import kotlin.math.max

// Base 10
fun Int.digits(): List<Int> {
    var remainder = this

    val result = mutableListOf<Int>()

    do {
        result.add(index = 0, element = remainder % 10)
        remainder /= 10
    } while (remainder > 0)

    return result
}

fun Int.isNotPrime(): Boolean = !isPrime()

fun Int.isPalindrome(): Boolean = toString().isPalindrome()

// From https://en.wikipedia.org/wiki/Primality_test#Pseudocode
fun Int.isPrime(): Boolean {
    require(this > 1)
    if (this == 1) return false
    if (this <= 3) return true
    if (this % 2 == 0 || this % 3 == 0) return false

    var i = 5
    while (i * i < this) {
        if (this % i == 0 || this % (i + 2) == 0) return false
        i += 6
    }

    return true
}

// https://en.wikipedia.org/wiki/Modular_multiplicative_inverse
// https://en.wikipedia.org/wiki/Extended_Euclidean_algorithm#Modular_integers
/**
 * Given `a` ([this]) and [n], returns the solution of `at ≡ 1 mod n`.
 */
fun Int.modularInverse(n: Int): Int? {
    var t = 0
    var newT = 1
    var r = n
    var newR = if (this < 0) this.nonNegativeRem(n) else this

    while (newR != 0) {
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

fun Int.nonNegativeRem(other: Int): Int {
    var result = rem(other)
    if (result < 0) result += other
    return result
}

fun Int.pow(n: Int): Int {
    require(n >= 0) { "This method cannot be called with a negative exponent." }

    var result = 1
    repeat(n) { result *= this }
    return result
}

fun Int.toBinaryString(minLength: Int = 0): String {
    require(minLength >= 0) { "This method cannot be called with a negative length." }

    val noPadResult = Integer.toBinaryString(this)
    return "0".repeat(max(0, minLength - noPadResult.length)) + noPadResult
}
