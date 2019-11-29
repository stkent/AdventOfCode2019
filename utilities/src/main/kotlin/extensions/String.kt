@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package extensions

// Extracts all the discrete integers from a string.
// For example, "#1372 @ 865,426: 27x15".extractInts() = [1372, 865, 426, 27, 15].
fun String.extractInts(): List<Int> {
    val delimiter = "/"

    return replace("""[^-\d]+""".toRegex(), delimiter)
        .split(delimiter)
        .filter(CharSequence::isNotEmpty)
        .map(String::toInt)
}

// https://en.wikipedia.org/wiki/Hamming_distance
fun String.hammingDistanceFrom(other: String): Int {
    require(length == other.length) { "This method can only be called using equal length strings." }

    return zip(other).count { charPair -> !charPair.valuesMatch() }
}

fun String.isAnagramOf(target: String): Boolean {
    return toCharArray().sorted() == target.toCharArray().sorted()
}

fun String.isLong(): Boolean = isLong(radix = 10)

fun String.isLong(radix: Int): Boolean = toLongOrNull(radix) != null

fun String.isPalindrome(ignoreWhitespace: Boolean = false): Boolean {
    val forward = if (ignoreWhitespace) replace(Regex("[^a-zA-Z]+"), "") else this
    val reverse = forward.reversed()
    return forward == reverse
}

// Rotates a String to the left.
fun String.rotate(steps: Int): String {
    val nSteps = steps.nonNegativeRem(length)
    return "${takeLast(length - nSteps)}${take(nSteps)}"
}

fun String.swap(index1: Int, index2: Int): String {
    val chars = toCharArray()
    val char1 = chars[index1]
    val char2 = chars[index2]
    chars[index1] = char2
    chars[index2] = char1
    return String(chars)
}
