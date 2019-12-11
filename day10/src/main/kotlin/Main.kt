import kotlin.math.PI
import kotlin.math.atan2

fun main() {
    val spaceMap = resourceFile("input.txt").readLines()

    println("Part 1 solution: ${bestBaseData(spaceMap).visibleAsteroidCount}")
    println("Part 2 solution: ${destructionOrder(spaceMap)[199].run { 100 * x - y }}")
}

fun bestBaseData(spaceMap: List<String>): BaseData {
    val asteroids = findAsteroids(spaceMap)

    return asteroids
        .map { base ->
            val visibleAsteroidCount = (asteroids - base)
                .map { otherAsteroid -> base.vectorTo(otherAsteroid).angleFromNorth() }
                .distinct()
                .count()

            return@map BaseData(base, visibleAsteroidCount)
        }
        .maxBy { (_, visibleAsteroidCount) -> visibleAsteroidCount }!!
}

fun destructionOrder(spaceMap: List<String>): List<GridPoint2d> {
    val asteroids = findAsteroids(spaceMap)
    val base = bestBaseData(spaceMap).location

    return (asteroids - base)
        .groupBy { target -> base.vectorTo(target).angleFromNorth() }
        .mapValues { (_, alignedTargets) ->
            alignedTargets.sortedBy { target -> base.l2DistanceTo(target) }
        }
        .toSortedMap()
        .values
        .flatMap { targetsAtAngle -> targetsAtAngle.withIndex() }
        .sortedBy { (index, _) -> index }
        .map { (_, target) -> target }
}

data class BaseData(val location: GridPoint2d, val visibleAsteroidCount: Int)

private fun findAsteroids(spaceMap: List<String>): List<GridPoint2d> {
    return spaceMap
        .withIndex()
        .flatMap { (y, row) ->
            row.withIndex()
                .filter { (_, char) -> char == '#' }
                .map { (x, _) ->
                    GridPoint2d(x, -y)
                }
        }
}

private fun GridVector2d.angleFromNorth(): Double {
    val rawAngle = 180 * atan2(x.toDouble(), y.toDouble()) / PI // In (-180, 180].
    return if (rawAngle < 0) rawAngle + 360 else rawAngle       // In [0, 360)
}
