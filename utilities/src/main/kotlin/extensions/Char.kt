@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package extensions

fun Char.repeated(times: Int): CharSequence {
    require(times >= 0) { "This method only accepts non-negative integers." }

    return CharArray(times) { this }.joinToString(separator = "")
}
