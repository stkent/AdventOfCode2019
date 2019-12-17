import extensions.nonNegativeRem
import extensions.repeat
import extensions.repeatIndefinitely

fun main() {
    val input = resourceFile("input.txt")
        .readLines()
        .first()
        .toCharArray()
        .map(Char::toString)
        .map(String::toInt)

    // Part 1
    runFftPhases(input, times = 100).also { result ->
        println("Part 1 solution: ${result.take(8).joinToString("")}")
    }

    // Part 2
    println("Part 2 observations:")
    val realInput = input
        .repeat(10000)
        .toList()

    println("- Input size = ${realInput.size}")

    val messageOffset = input
        .take(7)
        .joinToString("")
        .toInt()

    println("- Message offset = $messageOffset")

    println("Message offset is near the end of the input.")
}

fun runFftPhases(input: List<Int>, times: Int): List<Int> {
    var result = input
    repeat(times) { result = runFftPhase(result) }
    return result
}

fun runFftPhase(input: List<Int>): List<Int> {
    return input
        .withIndex()
        .map { (index, _) ->
            input
                .zip(patternForIndex(index).take(input.size).toList(), Int::times)
                .sum()
                .nonNegativeRem(10)
        }
}

private fun patternForIndex(index: Int): Sequence<Int> {
    return listOf(0, 1, 0, -1)
        .flatMap { digit -> List(index + 1) { digit } }
        .repeatIndefinitely()
        .drop(1)
}