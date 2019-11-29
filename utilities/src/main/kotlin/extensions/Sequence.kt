@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package extensions

fun <T> Sequence<T>.accumulate(operation: (accumulated: T, next: T) -> T): Sequence<T> {
    val iterator = iterator()

    if (!iterator.hasNext()) return emptySequence()

    return sequence {
        var accumulator = iterator.next().also { yield(it) }

        for (element in iterator) {
            accumulator = operation(accumulator, element).also { yield(it) }
        }
    }
}

fun <T, U> Sequence<T>.accumulate(
    initial: U,
    operation: (accumulated: U, next: T) -> U
): Sequence<U> {

    return sequence {
        var accumulator = initial.also { yield(it) }

        for (element in this@accumulate) {
            accumulator = operation(accumulator, element).also { yield(it) }
        }
    }
}

fun <T> Sequence<T>.firstRepeat(): T? = iterator().firstRepeat()

fun <T> Sequence<T>.firstRepeat(targetCount: Int): T? {
    require(targetCount >= 3) { "This method cannot be called with a targetCount less than 3." }

    return iterator().firstRepeat(targetCount = targetCount)
}
