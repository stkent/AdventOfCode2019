@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package extensions

// Base 10
fun Long.digits(): List<Int> {
    var remainder = this

    val result = mutableListOf<Int>()

    do {
        result.add(index = 0, element = (remainder % 10).toInt())
        remainder /= 10
    } while (remainder > 0)

    return result
}
