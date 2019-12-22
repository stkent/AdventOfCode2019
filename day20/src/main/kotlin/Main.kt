import Door.Type.Inner
import Door.Type.Outer
import extensions.collapseToString

fun main() {
    val inputMaze = resourceFile("input.txt").readLines()

    println("Part 1 solution: ${shortestPortalPathLength(inputMaze)}")
    println("Part 2 solution: ${shortestRecursivePathLength(inputMaze)}")
}

fun shortestPortalPathLength(rawMaze: List<String>): Int {
    val maze = parseMaze(rawMaze)

    return maze.start.shortestPathTo(maze.end) { current ->
        val allNeighbors = current
            .adjacentPoints()
            .filter { neighbor -> neighbor in maze.floor }
            .toMutableSet()

        maze.doors
            .find { (_, _, coords) -> current == coords }
            ?.let { door ->
                maze.doors
                    .find { (name, _, coords) ->
                        name == door.name && coords != current
                    }
            }
            ?.let { exitPortal -> allNeighbors += exitPortal.coords }

        return@shortestPathTo allNeighbors.associateWith { 1 }
    }!!
}

fun shortestRecursivePathLength(rawMaze: List<String>): Int {
    val maze = parseMaze(rawMaze)

    fun GridPoint2d.withZ(level: Int) = GridPoint3d(x, y, level)
    fun GridPoint3d.xyCoords() = GridPoint2d(x, y)

    return maze.start.withZ(0).shortestPathTo(maze.end.withZ(0)) { current ->
        val allNeighbors = current
            .xyCoords()
            .adjacentPoints()
            .filter { neighbor -> neighbor in maze.floor }
            .map { coords -> coords.withZ(current.z) }
            .toMutableSet()

        maze.doors
            .filterNot { (_, type, _) -> current.z == 0 && type == Outer }
            .find { (_, _, coords) -> current.xyCoords() == coords }
            ?.let { door ->
                maze.doors
                    .find { (name, _, coords) ->
                        name == door.name && coords != current.xyCoords()
                    }
            }
            ?.let { exitDoor ->
                allNeighbors += when (exitDoor.type) {
                    Outer -> exitDoor.coords.withZ(current.z + 1)
                    Inner -> exitDoor.coords.withZ(current.z - 1)
                }
            }

        return@shortestPathTo allNeighbors.associateWith { 1 }
    }!!
}

private fun parseMaze(rawMaze: List<String>): Maze {
    val floor = parseMazeFloor(rawMaze)
    val doors = parseAllDoors(rawMaze)
    val (_, _, start) = doors.find { (name, _) -> name == "AA" }!!
    val (_, _, end) = doors.find { (name, _) -> name == "ZZ" }!!
    return Maze(floor, start, end, doors)
}

private fun parseMazeFloor(rawMaze: List<String>): Set<GridPoint2d> {
    return rawMaze
        .foldIndexed(mutableSetOf()) { y, acc, row ->
            acc.apply {
                addAll(
                    row.withIndex()
                        .filter { (_, char) -> char == '.' }
                        .map { (x, _) -> GridPoint2d(x - 2, y - 2) }
                )
            }
        }
}

private fun parseAllDoors(rawMaze: List<String>): Collection<Door> {
    return parseAllLeftRightDoors(rawMaze) + parseAllTopBottomDoors(rawMaze)
}

private fun parseAllLeftRightDoors(rawMaze: List<String>): Collection<Door> {
    return rawMaze.foldIndexed(emptySet()) { y, acc, row ->
        acc + parseLinearDoors(row).map { (name, type, x) ->
            Door(name, type, GridPoint2d(x - 2, y - 2))
        }
    }
}

private fun parseAllTopBottomDoors(rawMaze: List<String>): Collection<Door> {
    val height = rawMaze.size
    val maxWidth = rawMaze.map(String::length).max()!!

    return (0 until maxWidth).fold(emptySet()) { acc, x ->
        val column = (0 until height)
            .map { y -> rawMaze[y].getOrElse(x) { ' ' } }
            .collapseToString()

        acc + parseLinearDoors(column).map { (name, type, y) ->
            Door(name, type, GridPoint2d(x - 2, y - 2))
        }
    }
}

private fun parseLinearDoors(string: String): Collection<LinearDoorParsingResult> {
    val windowSize = 3

    fun doorType(firstIndex: Int): Door.Type {
        return if (firstIndex == 0 || firstIndex == string.length - windowSize) Outer else Inner
    }

    return string.windowed(size = windowSize)
        .withIndex()
        .mapNotNull { (firstIndex, window) ->
            return@mapNotNull when {
                window.matches(Regex("\\.[A-Z]{2}")) -> {
                    LinearDoorParsingResult(
                        doorName = window.drop(1),
                        doorType = doorType(firstIndex),
                        index = firstIndex
                    )
                }

                window.matches(Regex("[A-Z]{2}\\.")) -> {
                    LinearDoorParsingResult(
                        doorName = window.dropLast(1),
                        doorType = doorType(firstIndex),
                        index = firstIndex + 2
                    )
                }

                else -> null
            }
        }
}

private class Maze(
    val floor: Set<GridPoint2d>,
    val start: GridPoint2d,
    val end: GridPoint2d,
    val doors: Collection<Door>
)

private data class Door(val name: String, val type: Type, val coords: GridPoint2d) {
    enum class Type { Outer, Inner }
}

private data class LinearDoorParsingResult(
    val doorName: String,
    val doorType: Door.Type,
    val index: Int
)
