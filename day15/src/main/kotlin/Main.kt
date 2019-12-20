fun main() {
    val inputProgram = resourceFile("input.txt")
        .readLines()
        .first()
        .split(',')
        .map(String::toLong)

    val shipMap = computeShipMap(program = inputProgram)
    printShipMap(shipMap)
}

fun computeShipMap(program: List<Long>): Map<GridPoint2d, ShipAreaType> {
    var droidCoords = GridPoint2d.origin
    val droidPath = mutableListOf<GridDirection>()
    val shipMap = mutableMapOf<GridPoint2d, ShipAreaType>()
        .apply { put(droidCoords, ShipAreaType.Floor) }
        .withDefault { ShipAreaType.Unknown }

    Computer().execute(
        program = program,
        source = source@ {
            val exploreStep = GridDirection.values()
                .find { droidCoords + it.toUnitVector() !in shipMap }

            if (exploreStep != null) {
                droidCoords += exploreStep.toUnitVector()
                droidPath += exploreStep
                return@source exploreStep.asInput()
            } else {
                if (droidPath.isNotEmpty()) {
                    val backtrackStep = droidPath.removeAt(droidPath.lastIndex).`180`()
                    droidCoords += backtrackStep.toUnitVector()
                    return@source backtrackStep.asInput()
                } else {
                    return@source Computer.Input.Terminate
                }
            }
        },
        sink = { output ->
            val shipAreaType = ShipAreaType.values()[output.toInt()]

            if (shipAreaType == ShipAreaType.OxygenSystem) {
                println("Part 1 solution: " + droidPath.size)
            }

            shipMap += droidCoords to shipAreaType

            if (shipAreaType == ShipAreaType.Wall) {
                // We hit a wall and didn't advance as expected; reset our position:
                val resetStep = droidPath.removeAt(droidPath.lastIndex).`180`()
                droidCoords += resetStep.toUnitVector()
            }

        }
    )

    return shipMap
}

private fun printShipMap(shipMap: Map<GridPoint2d, ShipAreaType>) {
    val (left, right, bottom, top) = shipMap.keys.bounds()

    for (y in top downTo bottom) {
        for (x in left..right) {
            val location = GridPoint2d(x, y)

            print(
                when (location) {
                    GridPoint2d.origin -> 'O'
                    else -> {
                        when (shipMap.getValue(GridPoint2d(x, y))) {
                            ShipAreaType.Unknown -> ' '
                            ShipAreaType.Floor -> '.'
                            ShipAreaType.Wall -> '█'
                            ShipAreaType.OxygenSystem -> 'A'
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

fun GridDirection.asInput() = Computer.Input.Value(when (this) {
    GridDirection.N -> 1L
    GridDirection.E -> 4L
    GridDirection.S -> 2L
    GridDirection.W -> 3L
})
