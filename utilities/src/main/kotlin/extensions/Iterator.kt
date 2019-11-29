@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package extensions

import com.google.common.collect.HashMultiset

fun <T> Iterator<T>.firstRepeat(): T? = firstRepeatIndexedValue()?.value

fun <T> Iterator<T>.firstRepeat(targetCount: Int): T? = firstRepeatIndexedValue(targetCount)?.value

fun <T> Iterator<T>.firstRepeatIndex(): Int? = firstRepeatIndexedValue()?.index

fun <T> Iterator<T>.firstRepeatIndex(targetCount: Int): Int? {
    return firstRepeatIndexedValue(targetCount)?.index
}

fun <T> Iterator<T>.firstRepeatIndexedValue(): IndexedValue<T>? {
    val seen = mutableSetOf<T>()

    for (element in withIndex()) {
        if (!seen.add(element.value)) return element
    }

    return null
}

fun <T> Iterator<T>.firstRepeatIndexedValue(targetCount: Int): IndexedValue<T>? {
    require(targetCount >= 3) { "This method cannot be called with a targetCount less than 3." }

    val seen = HashMultiset.create<T>()

    for (element in withIndex()) {
        if (seen.count(element.value) + 1 == targetCount) {
            return element
        }

        seen.add(element.value)
    }

    return null
}
