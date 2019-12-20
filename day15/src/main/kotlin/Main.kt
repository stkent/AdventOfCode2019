import Computer.Input.*
import AreaType.*
import java.util.*

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

    // Part 1
    pathLengths(shipMap, start = GridPoint2d.origin)[oxygen].also { pathLengthToOxygen ->
        println("Part 1 solution: $pathLengthToOxygen")
    }

    // Part 2
    pathLengths(shipMap, start = oxygen)
        .values
        .max()!!
        .also { maxOxygenationTime ->
            println("Part 2 solution: $maxOxygenationTime")
        }
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

// DFS, simplified because there are no loops possible in our map.
private fun pathLengths(
    shipMap: Map<GridPoint2d, AreaType>,
    start: GridPoint2d
): Map<GridPoint2d, Int> {
    val unexplored: Deque<Pair<GridPoint2d, Int>> =
        LinkedList<Pair<GridPoint2d, Int>>().apply { add(start to 0) }

    val explored = mutableMapOf<GridPoint2d, Int>()

    while (unexplored.isNotEmpty()) {
        val (coord, pathLength) = unexplored.pollFirst()

        coord
            .adjacentPoints()
            .filter { it !in explored }
            .filter { neighbor -> shipMap[neighbor] != Wall }
            .forEach { newNeighbor -> unexplored.addLast(newNeighbor to pathLength + 1) }

        explored += coord to pathLength
    }

    return explored
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
