import GridDirection.*
import ShipAreaType.*

fun main() {
    val inputProgram = resourceFile("input.txt")
        .readLines()
        .first()
        .split(',')
        .map(String::toLong)

    val shipMap = computeShipMap(program = inputProgram)
}

fun computeShipMap(program: List<Long>): Map<GridPoint2d, ShipAreaType> {
    var droidCoords = GridPoint2d.origin
    val droidPath = mutableListOf<GridDirection>()
    val shipMap = mutableMapOf<GridPoint2d, ShipAreaType>()

    Computer().execute(
        program = program,
        source = source@ {
            val nextStep = GridDirection.values()
                .firstOrNull { droidCoords + it.toUnitVector() !in shipMap }

            return@source (nextStep?.let {
                // we have found a location that's not in the map yet; try moving there
                nextStep
            } ?: run {
                droidPath.last().`180`()
            }).asInput()
        },
        sink = { output ->
//            val shipAreaType = values()[output.toInt()]
//            val targetLocation = droidCoords + nextStep().toUnitVector()
//            ship += targetLocation to shipAreaType
//            if (shipAreaType != Wall) droidLocationData = targetLocation
//            displayShip(ship, droidLocationData)
        }
    )
}

private fun printShipMap(ship: Map<GridPoint2d, ShipAreaType>) {
    val (left, right, bottom, top) = ship.keys.bounds()

    for (y in top downTo bottom) {
        for (x in left..right) {
            val location = GridPoint2d(x, y)

            print(
                when (location) {
                    GridPoint2d.origin -> '*'
                    else -> {
                        when (ship.getValue(GridPoint2d(x, y))) {
                            Unknown -> ' '
                            Floor -> '.'
                            Wall -> '█'
                            OxygenSystem -> '!'
                        }
                    }
                }
            )
        }

        println()
    }
}

enum class ShipAreaType {
    Wall, Floor, OxygenSystem, Unknown
}

private data class Location(val coords: GridPoint2d, val steps: List<GridDirection>)

fun GridDirection.asInput() = when (this) {
    N -> 1L
    E -> 4L
    S -> 2L
    W -> 3L
}
