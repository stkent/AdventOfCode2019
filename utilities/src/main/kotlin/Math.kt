@file:Suppress("unused", "MemberVisibilityCanBePrivate")

// From https://en.wikipedia.org/wiki/Greatest_common_divisor#Using_Euclid's_algorithm
fun gcd(a: Int, b: Int): Int {
    if (a == 0 || b == 0) {
        return 0
    }

    return when {
        a < b -> gcd(a, b - a)
        a > b -> gcd(a - b, b)
        else -> a
    }
}

// From https://en.wikipedia.org/wiki/Least_common_multiple#Reduction_by_the_greatest_common_divisor
fun lcm(a: Int, b: Int): Int {
    if (a == 0 && b == 0) {
        return 0
    }

    return a * b / gcd(a, b)
}

// From https://www.geeksforgeeks.org/print-all-prime-factors-of-a-given-number/
fun primeFactors(n: Int): List<Int> {
    val result = mutableListOf<Int>()

    if (n < 2) return result

    var remainder = n

    fun divideOutFactor(factor: Int) {
        while (remainder % factor == 0) {
            result += factor
            remainder /= factor
        }
    }

    divideOutFactor(2)
    if (remainder == 1) return result

    for (f in generateSequence(3) { it + 2 }) {
        divideOutFactor(f)
        if (remainder == 1) return result
    }

    return result
}

// Based on https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes#Pseudocode
// Inspired by https://www.youtube.com/watch?v=bnRNiE_OVWA&amp=&t=10m55s
//
// Note that this implementation will be outperformed by a Sieve of Eratosthenes algorithm with a known upper bound on
// the prime size. In that (canonical) case, the efficiency comes from erasing candidates from an in-memory data
// structure when their smallest prime factor is found, and then _never considering those erased candidates again_. In
// this (infinite) case, it's not possible to hold the candidates in memory, so _every_ candidate must be evaluated in
// turn by comparing it to _all_ previously-discovered prime numbers.
//
// Therefore, this implementation should be preferred only when there is no known upper bound on the _size_ of prime
// number needed (an example of this would be generating the first 100 primes).
fun primes(candidates: Sequence<Int> = generateSequence(2) { it + 1 }): Sequence<Int> {
    val newPrime = candidates.first()

    return sequence {
        yield(newPrime)
        yieldAll(primes(candidates.filterNot { it % newPrime == 0 }))
    }
}
