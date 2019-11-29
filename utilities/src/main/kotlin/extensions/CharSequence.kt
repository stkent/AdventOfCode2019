@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package extensions

fun CharSequence.allDistinct(): Boolean {
    require(isNotEmpty()) { "This method cannot be called on empty CharSequences." }

    val seen = mutableSetOf<Char>()

    for (char in this) if (!seen.add(char)) return false
    return true
}

fun CharSequence.allMatch(): Boolean {
    require(isNotEmpty()) { "This method cannot be called on empty CharSequences." }

    val iterator = iterator()
    val seen = mutableSetOf(iterator.next())

    while (iterator.hasNext()) if (seen.add(iterator.next())) return false
    return true
}

fun CharSequence.anyDistinct(): Boolean = !allMatch()


fun CharSequence.anyRepeat(): Boolean = !allDistinct()

fun CharSequence.charCounts(): Map<Char, Int> {
    return groupingBy { it }.eachCount()
}
