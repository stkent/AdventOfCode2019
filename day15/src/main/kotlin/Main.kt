import Computer.Input.*
import AreaType.*

fun main() {
    val inputProgram = resourceFile("input.txt")
        .readLines()
        .first()
        .split(',')
        .map(String::toLong)

    val shipMap = exploreShip(program = inputProgram)
//    printShipMap(shipMap)

    val oxygen = shipMap
        .entries
        .find { (_, areaType) -> areaType == OxygenSystem }!!
        .key

    val getNeighbors: (GridPoint2d) -> Map<GridPoint2d, Int> = { current ->
        current
            .adjacentPoints()
            .filter { shipMap[it] != Wall }
            .associateWith { 1 }
    }

    println("Part 1 solution: ${GridPoint2d.origin.shortestPathTo(oxygen, getNeighbors)!!}")
    println("Part 2 solution: ${oxygen.shortestPaths(getNeighbors).values.max()!!}")
}

private fun exploreShip(program: List<Long>): Map<GridPoint2d, AreaType> {
    val path = mutableListOf<GridDirection>()
    fun droidLocation() = path.fold(GridPoint2d.origin) { acc, dir -> acc + dir.toVector() }

    val map = mutableMapOf<GridPoint2d, AreaType>()
        .apply { put(droidLocation(), Floor) }

    Computer().execute(
        program = program,
        source = source@{
            val forwardStep = droidLocation().let { droidLocation ->
                GridDirection
                    .values()
                    .find { droidLocation + it.toVector() !in map }
            }

            if (forwardStep != null) {
                // We found a new coordinate to explore; go there.
                path += forwardStep
                return@source forwardStep.toComputerInput()
            }

            if (path.isNotEmpty()) {
                // We did not find a new coordinate to explore; go back one step if possible.
                return@source path.removeAt(path.lastIndex).`180`().toComputerInput()
            }

            // We've explored the entire map; stop execution.
            return@source Terminate
        },
        sink = { output ->
            val areaType = values()[output.toInt()]
            map += droidLocation() to areaType

            if (areaType == Wall) {
                // We tried walking into a wall; step back!
                path.removeAt(path.lastIndex).`180`()
            }
        }
    )

    return map
}

private fun printShipMap(shipMap: Map<GridPoint2d, AreaType>) {
    val (left, right, bottom, top) = shipMap.keys.bounds()

    for (y in top downTo bottom) {
        for (x in left..right) {
            val location = GridPoint2d(x, y)

            print(
                when (location) {
                    GridPoint2d.origin -> 'O'
                    else -> {
                        when (shipMap.getOrDefault(GridPoint2d(x, y), Wall)) {
                            Floor -> '.'
                            Wall -> '█'
                            OxygenSystem -> 'A'
                        }
                    }
                }
            )
        }

        println()
    }
}

private fun GridDirection.toComputerInput() = Value(
    when (this) {
        GridDirection.N -> 1L
        GridDirection.E -> 4L
        GridDirection.S -> 2L
        GridDirection.W -> 3L
    }
)

private enum class AreaType {
    Wall, Floor, OxygenSystem
}
