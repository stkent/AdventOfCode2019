@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package extensions

/**
 * Removes the specified keys and their corresponding values from this map.
 *
 * Based on MutableMap<K, V>.remove(K) and MutableCollection<T>.extensions.removeAll(Collection<T>).
 *
 * @return `true` if any of the specified keys were removed from the map, `false` if the map was not modified.
 */
fun <K, V : Any> MutableMap<K, V>.removeAll(keys: Iterable<K>): Boolean {
    return keys.mapNotNull { remove(it) }.isNotEmpty()
}
