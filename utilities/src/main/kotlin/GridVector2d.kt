@file:Suppress("unused", "MemberVisibilityCanBePrivate")

import kotlin.math.abs
import kotlin.math.hypot
import kotlin.math.max

data class GridVector2d(val x: Int, val y: Int) {

    operator fun plus(other: GridVector2d): GridVector2d {
        return GridVector2d(x + other.x, y + other.y)
    }

    fun left90() = GridVector2d(-y, x)

    fun right90() = GridVector2d(y, -x)

    @Suppress("FunctionName")
    fun `180`() = GridVector2d(-x, -y)

    val l1Magnitude = abs(x) + abs(y)

    val l2Magnitude = hypot(x.toDouble(), y.toDouble())

    val lInfMagnitude = max(abs(x), abs(y))

}
