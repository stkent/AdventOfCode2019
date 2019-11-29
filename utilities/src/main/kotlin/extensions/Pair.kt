@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package extensions

fun <T, U> Pair<T, U>.flip(): Pair<U, T> = second to first

fun <T : Comparable<T>> Pair<T, T>.max(): T = maxOf(first, second)

fun <T : Comparable<T>> Pair<T, T>.min(): T = minOf(first, second)

fun <T> Pair<T, T>.valuesMatch(): Boolean = first == second
