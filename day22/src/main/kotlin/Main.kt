import extensions.accumulate
import extensions.firstRepeatIndex
import extensions.nonNegativeRem

fun main() {
    val input = resourceFile("input.txt").readLines()

    val steps = input.map { rawStep -> ShuffleStep(rawStep) }

    // Part 1
    steps
        .fold(2019L) { acc, step ->
            step.apply(acc, deckSize = 10007)
        }
        .also { println("Part 1 solution: $it") }

    // Part 2
    steps
        .reversed()
        .asSequence()
        .accumulate(2000L) { acc, step ->
            step.invert(acc, deckSize = 119315717514047)
        }
        .firstRepeatIndex()
        .also { println("Part 2 solution: $it") }
}

sealed class ShuffleStep {

    abstract fun apply(index: Long, deckSize: Long): Long
    abstract fun invert(index: Long, deckSize: Long): Long

    object DealIntoNewStack : ShuffleStep() {
        override fun apply(index: Long, deckSize: Long) = deckSize - 1 - index
        override fun invert(index: Long, deckSize: Long) = apply(index, deckSize)
    }

    class Cut(val size: Long) : ShuffleStep() {
        override fun apply(index: Long, deckSize: Long): Long {
            return (index - size).nonNegativeRem(deckSize)
        }

        override fun invert(index: Long, deckSize: Long): Long {
            return (index + size).nonNegativeRem(deckSize)
        }
    }

    class DealWithIncrement(val size: Long) : ShuffleStep() {
        override fun apply(index: Long, deckSize: Long): Long {
            return (size * index) % deckSize
        }

        override fun invert(index: Long, deckSize: Long): Long {
            return index.modularInverse(size)!!
        }
    }

    companion object {
        operator fun invoke(rawStep: String): ShuffleStep {
            if (rawStep == "deal into new stack") return DealIntoNewStack
            if (rawStep.startsWith("cut")) return Cut(size = rawStep.drop(4).toLong())
            return DealWithIncrement(size = rawStep.drop(20).toLong())
        }
    }

}

// https://en.wikipedia.org/wiki/Modular_multiplicative_inverse
// https://en.wikipedia.org/wiki/Extended_Euclidean_algorithm#Modular_integers
/**
 * Given a ([this]) and [n], returns the solution of `at ≡ 1 mod n`.
 */
fun Long.modularInverse(n: Long): Long? {
    var t = 0L
    var newT = 1L
    var r = n
    var newR = this

    while (newR != 0L) {
        val quotient = r.rem(newR)
        t = newT
        newT = t - quotient * newT
        r = newR
        newR = r - quotient * newR
    }

    if (r > 1) return null
    if (t < 0) return t + n
    return t
}
