import ShuffleStep.*
import extensions.rotate
import java.util.*

fun main() {
    val input = resourceFile("input.txt").readLines()

    println("Part 1 solution: ${shuffleDeck(input).indexOf(2019u)}")
}

fun shuffleDeck(rawSteps: List<String>, deckSize: UInt = 10007u): List<UInt> {
    val deck = LinkedList<UInt>().apply { addAll(0u until deckSize) }

    rawSteps
        .map { rawStep -> ShuffleStep(rawStep) }
        .forEach { step ->
            when (step) {
                DealIntoNewStack -> deck.reverse()
                is Cut -> deck.rotate(step.size)
                is DealWithIncrement -> {
                    val newDeck = (0u until deckSize)
                        .map { src -> deck[src.toInt()] to (src * step.size) % deckSize }
                        .sortedBy(Pair<UInt, UInt>::second)
                        .map(Pair<UInt, UInt>::first)
                        .toList()

                    deck.clear()
                    deck.addAll(newDeck)
                }
            }
        }

    return deck
}

sealed class ShuffleStep {
    object DealIntoNewStack : ShuffleStep()
    class Cut(val size: Int) : ShuffleStep()
    class DealWithIncrement(val size: UInt) : ShuffleStep()

    companion object {
        operator fun invoke(rawStep: String): ShuffleStep {
            if (rawStep == "deal into new stack") return DealIntoNewStack
            if (rawStep.startsWith("cut")) return Cut(size = rawStep.drop(4).toInt())
            return DealWithIncrement(size = rawStep.drop(20).toUInt())
        }
    }
}
