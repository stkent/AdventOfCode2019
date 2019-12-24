import extensions.modularInverse
import extensions.nonNegativeRem
import java.math.BigInteger

fun main() {
    val input = resourceFile("input.txt").readLines()

    // Part 1
    val deckSize1 = 10007.toBigInteger()
    finalIndexOfCard(
        card = 2019.toBigInteger(),
        rawShuffleSteps = input,
        deckSize = deckSize1
    ).also { println("Part 1 solution: $it") }

    // Part 2
    val deckSize2 = 119315717514047.toBigInteger()
    finalCardAtIndex(
        index = 2020.toBigInteger(),
        rawShuffleSteps = input,
        deckSize = deckSize2,
        shuffleCount = 101741582076661.toBigInteger()
    ).also { println("Part 2 solution: $it") }
}

fun finalIndexOfCard(
    card: BigInteger,
    rawShuffleSteps: List<String>,
    deckSize: BigInteger
): BigInteger {

    val shuffle = netShuffle(rawShuffleSteps, deckSize)
    return shuffle(card).nonNegativeRem(deckSize)
}

fun finalCardAtIndex(
    index: BigInteger,
    rawShuffleSteps: List<String>,
    deckSize: BigInteger,
    shuffleCount: BigInteger
): BigInteger {
    val singleShuffle = netShuffle(rawShuffleSteps, deckSize).invert(modulo = deckSize)
    val shuffle = singleShuffle//.toPower(shuffleCount, modulo = deckSize)
    return shuffle(index).nonNegativeRem(deckSize)
}

private fun netShuffle(rawShuffleSteps: List<String>, deckSize: BigInteger): LinearFunction {
    return rawShuffleSteps
        .map { rawShuffleStep -> ShuffleStep(rawShuffleStep).asLinearFunction() }
        .reduce { acc, shuffleStepFunction ->
            shuffleStepFunction.compose(acc, deckSize)
        }
}

private sealed class ShuffleStep {

    /**
     * Returns a representation of this shuffle step as a linear transformation. This linear
     * transformation returns the post-shuffle index of a pre-shuffle index.
     */
    abstract fun asLinearFunction(): LinearFunction

    object DealIntoNewStack : ShuffleStep() {
        override fun asLinearFunction() = LinearFunction((-1).toBigInteger(), (-1).toBigInteger())
    }

    class Cut(val size: BigInteger) : ShuffleStep() {
        override fun asLinearFunction() = LinearFunction(1.toBigInteger(), -size)
    }

    class DealWithIncrement(val size: BigInteger) : ShuffleStep() {
        override fun asLinearFunction() = LinearFunction(size, 0.toBigInteger())
    }

    companion object {
        operator fun invoke(rawStep: String): ShuffleStep {
            if (rawStep == "deal into new stack") return DealIntoNewStack
            if (rawStep.startsWith("cut")) return Cut(size = rawStep.drop(4).toBigInteger())
            return DealWithIncrement(size = rawStep.drop(20).toBigInteger())
        }
    }

}

private data class LinearFunction(val A: BigInteger, val B: BigInteger) {

    fun compose(other: LinearFunction, modulo: BigInteger): LinearFunction {
        val (C, D) = other
        return LinearFunction(
            A = (A * C).nonNegativeRem(modulo),
            B = ((A * D) + B).nonNegativeRem(modulo)
        )
    }

    fun invert(modulo: BigInteger): LinearFunction {
        val aInverse = A.modularInverse(modulo)!!
        return LinearFunction(A = aInverse, B = (-aInverse * B).nonNegativeRem(modulo))
    }

    operator fun invoke(operand: BigInteger): BigInteger {
        return A * operand + B
    }

}
