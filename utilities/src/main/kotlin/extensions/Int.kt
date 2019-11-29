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
