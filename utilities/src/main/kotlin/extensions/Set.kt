@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package extensions

fun <T> Set<T>.powerSet(): Set<Set<T>> {
    return when (size) {
        0 -> setOf(emptySet())
        1 -> setOf(this, emptySet())

        else -> {
            val anyElement = iterator().next()

            val exclusiveRemainderPowerSet = minus(anyElement).powerSet()

            val inclusiveRemainderPowerSet = emptySet<Set<T>>().toMutableSet()

            exclusiveRemainderPowerSet.mapTo(inclusiveRemainderPowerSet) {
                it.union(setOf(anyElement))
            }

            return exclusiveRemainderPowerSet.union(inclusiveRemainderPowerSet)
        }
    }
}
