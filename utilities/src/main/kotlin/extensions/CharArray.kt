@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package extensions

fun CharArray.collapseToString(): String {
    return joinToString(separator = "")
}
