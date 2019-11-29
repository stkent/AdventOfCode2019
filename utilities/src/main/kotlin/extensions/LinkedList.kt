@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package extensions

import java.util.*
import kotlin.math.abs

fun <T> LinkedList<T>.rotate(times: Int) {
    if (times > 0) {
        repeat(times) {
            val oldFirst = pollFirst()
            addLast(oldFirst)
        }
    } else if (times < 0) {
        repeat(abs(times)) {
            val oldLast = pollLast()
            addFirst(oldLast)
        }
    }
}
