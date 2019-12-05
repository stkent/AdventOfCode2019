import extensions.digits
import extensions.isNonDecreasing
import extensions.runs

fun main() {
    val inputRange = 273025..767253
    val passwords = inputRange.map(::Password)

    // Part 1
    passwords
        .count(Password::matchesPart1Criteria)
        .also { println("Part 1 solution: $it") }

    // Part 2
    passwords
        .count(Password::matchesPart2Criteria)
        .also { println("Part 2 solution: $it") }
}

inline class Password(private val value: Int) {
    fun matchesPart1Criteria(): Boolean {
        val digits = value.digits()

        val hasRepeat = digits
            .runs()
            .any { run -> run.size >= 2 }

        return hasRepeat && digits.isNonDecreasing()
    }

    fun matchesPart2Criteria(): Boolean {
        val digits = value.digits()

        val hasExactPair = digits
            .runs()
            .any { run -> run.size == 2 }

        return hasExactPair && digits.isNonDecreasing()
    }
}
