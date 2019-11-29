@file:Suppress("unused", "MemberVisibilityCanBePrivate")

import extensions.pow
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sqrt

data class GridPoint4d(val x: Int, val y: Int, val z: Int, val w: Int) {

    companion object {
        val origin = GridPoint4d(0, 0, 0, 0)
    }

    fun adjacentPoints(): Set<GridPoint4d> = setOf(
        GridPoint4d(x + 1, y, z, w),
        GridPoint4d(x - 1, y, z, w),
        GridPoint4d(x, y + 1, z, w),
        GridPoint4d(x, y - 1, z, w),
        GridPoint4d(x, y, z + 1, w),
        GridPoint4d(x, y, z - 1, w),
        GridPoint4d(x, y, z, w + 1),
        GridPoint4d(x, y, z, w - 1)
    )

    fun isInBounds(
        xBounds: IntRange = Int.MIN_VALUE..Int.MAX_VALUE,
        yBounds: IntRange = Int.MIN_VALUE..Int.MAX_VALUE,
        zBounds: IntRange = Int.MIN_VALUE..Int.MAX_VALUE,
        wBounds: IntRange = Int.MIN_VALUE..Int.MAX_VALUE
    ): Boolean {

        return x in xBounds && y in yBounds && z in zBounds && w in wBounds
    }

    fun l1DistanceTo(other: GridPoint4d) =
        abs(x - other.x) + abs(y - other.y) + abs(z - other.z) + abs(w - other.w)

    fun l2DistanceTo(other: GridPoint4d): Double {
        return sqrt(
            (x - other.x).pow(2).toDouble() +
                    (y - other.y).pow(2).toDouble() +
                    (z - other.z).pow(2).toDouble() +
                    (w - other.w).pow(2).toDouble()
        )
    }

    fun lInfDistanceTo(other: GridPoint4d) =
        max(max(max(abs(x - other.x), abs(y - other.y)), abs(z - other.z)), abs(w - other.w))

}
