import extensions.collapseToString
import extensions.orderedPairs

fun main() {
    val inputMaze = resourceFile("input.txt").readLines()

    println("Part 1 solution: ${shortestPathLength(inputMaze)}")
}

fun shortestPathLength(rawMaze: List<String>): Int {
    val maze = parseMaze(rawMaze)

    return maze.start.shortestPathTo(maze.end) { current ->
        val allNeighbors = current
            .adjacentPoints()
            .filter { neighbor -> neighbor in maze.floor }
            .toMutableSet()

        maze.wormholes
            .find { (start, _) -> current == start }
            ?.end
            ?.let { portalNeighbor -> allNeighbors += portalNeighbor }

        return@shortestPathTo allNeighbors.associateWith { 1 }
    }!!
}

private fun parseMaze(rawMaze: List<String>): Maze {
    val floor = parseMazeFloor(rawMaze)
    val namedPortals = parseMazePortals(rawMaze)
    val (_, start) = namedPortals.find { (name, _) -> name == "AA" }!!
    val (_, end) = namedPortals.find { (name, _) -> name == "ZZ" }!!
    val wormholes = namedPortals
        .groupBy({ (name, _) -> name }) { (_, connectedPortals) -> connectedPortals }
        .values
        .flatMap { connectedPortals -> connectedPortals.orderedPairs().keys }
        .map { (startPortal, endPortal) -> Wormhole(start = startPortal, end = endPortal) }

    return Maze(floor, start, end, wormholes)
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

private fun parseMazePortals(rawMaze: List<String>): Set<Pair<String, GridPoint2d>> {
    return parseLeftRightPortals(rawMaze) + parseTopBottomPortals(rawMaze)
}

private fun parseLeftRightPortals(rawMaze: List<String>): Set<Pair<String, GridPoint2d>> {
    return rawMaze.foldIndexed(emptySet()) { y, acc, row ->
        acc + parsePortals(row).map { (name, x) -> name to GridPoint2d(x - 2, y - 2) }
    }
}

private fun parseTopBottomPortals(rawMaze: List<String>): Set<Pair<String, GridPoint2d>> {
    val height = rawMaze.size
    val maxWidth = rawMaze.map(String::length).max()!!

    return (0 until maxWidth).fold(emptySet()) { acc, x ->
        val column = (0 until height)
            .map { y -> rawMaze[y].getOrElse(x) { ' ' } }
            .collapseToString()

        acc + parsePortals(column).map { (name, y) -> name to GridPoint2d(x - 2, y - 2) }
    }
}

private fun parsePortals(string: String): List<Pair<String, Int>> {
    return string.windowed(size = 3)
        .withIndex()
        .mapNotNull { (firstIndex, window) ->
            return@mapNotNull when {
                window.matches(Regex("\\.[A-Z]{2}")) -> {
                    window.drop(1) to firstIndex
                }

                window.matches(Regex("[A-Z]{2}\\.")) -> {
                    window.dropLast(1) to firstIndex + 2
                }

                else -> null
            }
        }
}

private class Maze(
    val floor: Set<GridPoint2d>,
    val start: GridPoint2d,
    val end: GridPoint2d,
    val wormholes: List<Wormhole>
)

private data class Wormhole(val start: GridPoint2d, val end: GridPoint2d)
