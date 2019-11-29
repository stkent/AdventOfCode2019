@file:Suppress("unused", "MemberVisibilityCanBePrivate")

import kotlin.math.abs
import kotlin.math.hypot
import kotlin.math.max

data class GridPoint2d(val x: Int, val y: Int) {

    companion object {
        val origin = GridPoint2d(0, 0)
    }

    operator fun minus(vector: GridVector2d) = GridPoint2d(x - vector.x, y - vector.y)

    operator fun plus(vector: GridVector2d) = GridPoint2d(x + vector.x, y + vector.y)

    fun adjacentPoints(): Set<GridPoint2d> = setOf(
        GridPoint2d(x + 1, y),
        GridPoint2d(x - 1, y),
        GridPoint2d(x, y + 1),
        GridPoint2d(x, y - 1)
    )

    fun flipX(): GridPoint2d {
        return copy(x = -x)
    }

    fun flipY(): GridPoint2d {
        return copy(y = -y)
    }

    fun isInBounds(
        xBounds: IntRange = Int.MIN_VALUE..Int.MAX_VALUE,
        yBounds: IntRange = Int.MIN_VALUE..Int.MAX_VALUE
    ): Boolean {

        return x in xBounds && y in yBounds
    }

    fun l1DistanceTo(other: GridPoint2d) = abs(x - other.x) + abs(y - other.y)

    fun l2DistanceTo(other: GridPoint2d) = hypot((x - other.x).toDouble(), (y - other.y).toDouble())

    fun lInfDistanceTo(other: GridPoint2d) = max(abs(x - other.x), abs(y - other.y))

    fun shiftBy(dx: Int = 0, dy: Int = 0): GridPoint2d {
        return copy(x = x + dx, y = y + dy)
    }

    fun surroundingPoints(): Set<GridPoint2d> = adjacentPoints().union(
        setOf(
            GridPoint2d(x + 1, y + 1),
            GridPoint2d(x + 1, y - 1),
            GridPoint2d(x - 1, y - 1),
            GridPoint2d(x - 1, y + 1)
        )
    )

}
