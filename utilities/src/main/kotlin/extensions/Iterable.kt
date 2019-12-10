@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package extensions

// Adapted from stdlib proposal: https://youtrack.jetbrains.com/issue/KT-7657#comment=27-2602841
fun <T> Iterable<T>.accumulate(operation: (accumulated: T, next: T) -> T): List<T> {
    val iterator = iterator()

    if (!iterator.hasNext()) return emptyList()

    val result = mutableListOf(iterator.next())

    for (element in iterator) {
        result.add(operation(result.last(), element))
    }

    return result
}

// Adapted from stdlib proposal: https://youtrack.jetbrains.com/issue/KT-7657#comment=27-2602841
fun <T, R> Iterable<T>.accumulate(initial: R, operation: (accumulated: R, next: T) -> R): List<R> {
    val result = mutableListOf(initial)

    for (element in this) {
        result.add(operation(result.last(), element))
    }

    return result
}

fun <T> Iterable<T>.allNeighborsSatisfy(predicate: (prev: T, next: T) -> Boolean): Boolean {
    val iterator = iterator()

    require(iterator.hasNext()) { "This method cannot be called on an empty Iterable." }

    var prev = iterator.next()

    while (iterator.hasNext()) {
        val next = iterator.next()
        if (!predicate(prev, next)) return false
        prev = next
    }

    return true
}

fun <T> Iterable<T>.collapseToString(): String {
    return joinToString(separator = "")
}

fun <T> Iterable<T>.elementCounts(): Map<T, Int> {
    return groupingBy { it }.eachCount()
}

fun <T> Iterable<T>.firstRepeat(): T? = iterator().firstRepeat()

fun <T> Iterable<T>.firstRepeat(targetCount: Int): T? {
    require(targetCount >= 3) { "This method cannot be called with a targetCount less than 3." }

    return iterator().firstRepeat(targetCount = targetCount)
}

fun <T> Iterable<T>.head(): T = first()

infix fun <T> Iterable<T>.intersects(other: Iterable<T>): Boolean = intersect(other).isNotEmpty()

fun <T : Comparable<T>> Iterable<T>.isDecreasing(): Boolean {
    return allNeighborsSatisfy { prev, next -> prev > next }
}

fun <T : Comparable<T>> Iterable<T>.isIncreasing(): Boolean {
    return allNeighborsSatisfy { prev, next -> prev < next }
}

fun <T : Comparable<T>> Iterable<T>.isNonDecreasing(): Boolean {
    return allNeighborsSatisfy { prev, next -> prev <= next }
}

fun <T : Comparable<T>> Iterable<T>.isNonIncreasing(): Boolean {
    return allNeighborsSatisfy { prev, next -> prev >= next }
}

fun <T> Iterable<T>.mode(): Mode<T>? {
    return elementCounts()
        .maxBy { it.value }
        ?.run { Mode(modalValue = key, count = value) }
}

fun <T> Iterable<T>.partitionBy(connected: (T, T) -> Boolean): Set<List<T>> {
    val result = mutableSetOf<List<T>>()

    forEach { newElement ->
        val partsToConnect = result.filter { part ->
            part.any { element -> connected(newElement, element) }
        }

        result.apply {
            removeAll(partsToConnect)
            add(partsToConnect.flatten() + newElement)
        }
    }

    return result
}

/**
 * Returns a finite Sequence that loops through the original iterable [count] times.
 */
fun <T> Iterable<T>.repeat(count: Int): Sequence<T> {
    if (!iterator().hasNext()) return emptySequence()

    return sequence {
        repeat(count) { yieldAll(this@repeat) }
    }
}

/** Returns an infinite Sequence that loops through the original iterable indefinitely. */
fun <T> Iterable<T>.repeatIndefinitely(): Sequence<T> {
    if (!iterator().hasNext()) return emptySequence()

    return sequence {
        while (true) yieldAll(this@repeatIndefinitely)
    }
}

fun <T: Comparable<T>> Iterable<T>.runs(): List<List<T>> {
    val result = mutableListOf<List<T>>()

    var runValue: T? = null
    var runLength = 0

    fun startRun(value: T) {
        runValue = value
        runLength = 1
    }

    fun recordRun() {
        result.add(List(runLength) { runValue!! })
    }

    val iterator = iterator()
    while (iterator.hasNext()) {
        val nextValue = iterator.next()

        if (runValue == null) {
            startRun(value = nextValue)
        } else {
            if (nextValue == runValue) {
                runLength++
            } else {
                recordRun()
                startRun(value = nextValue)
            }
        }
    }

    recordRun()
    return result
}

fun <T> Iterable<T>.tail(): List<T> = drop(1)

// Supporting classes

data class Mode<T>(val modalValue: T, val count: Int)
