import GridDirection.*
import GridPoint2d.Companion.origin
import java.lang.IllegalStateException

fun main() {
    val (wire1, wire2) = resourceFile("input.txt").readLines().take(2)

    println("Part 1 solution: ${smallestCrossingDistance(wire1, wire2)}")
    println("Part 2 solution: ${smallestCrossingDelay(wire1, wire2)}")
}

fun smallestCrossingDistance(wire1: String, wire2: String): Int? {
    val wirePath1 = wirePath(wire1)
    val wirePath2 = wirePath(wire2)
    val crossings = (wirePath1 intersect wirePath2) - origin

    return crossings
        .map { crossing -> origin.l1DistanceTo(crossing) }
        .min()
}

fun smallestCrossingDelay(wire1: String, wire2: String): Int? {
    val wirePath1 = wirePath(wire1)
    val wirePath2 = wirePath(wire2)
    val crossings = (wirePath1 intersect wirePath2) - origin

    return crossings
        .map { crossing -> wirePath1.indexOf(crossing) + wirePath2.indexOf(crossing) }
        .min()
}

private fun wirePath(wire: String): List<GridPoint2d> {
    var wireEnd = origin
    val wirePath = mutableListOf<GridPoint2d>().apply { add(origin) }

    wire.split(',')
        .forEach { segment ->
            val step = (when (segment.first()) {
                'U' -> N
                'R' -> E
                'D' -> S
                'L' -> W
                else -> throw IllegalStateException()
            }).toVector()

            val stepCount = segment.drop(1).toInt()

            repeat(stepCount) {
                wireEnd += step
                wirePath += wireEnd
            }
        }

    return wirePath
}
