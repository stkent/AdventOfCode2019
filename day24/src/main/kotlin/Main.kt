import extensions.firstRepeat
import extensions.pow

fun main() {
    val input = resourceFile("input.txt").readLines()
    val gridSize = input.size

    val bugs = input
        .withIndex()
        .flatMap { (y, row) ->
            row.withIndex()
                .mapNotNull { (x, char) ->
                    if (char == '#') GridPoint2d(x, y) else null
                }
        }
        .toSet()

    generateSequence(bugs) { prev -> evolve(prev, gridSize) }
        .firstRepeat()!!
        .biodiversityRating()
        .also { println("Part 1 solution: $it") }
}

fun evolve(bugs: Set<GridPoint2d>, gridSize: Int): Set<GridPoint2d> {
    val result = mutableSetOf<GridPoint2d>()

    for (y in 0 until gridSize) {
        for (x in 0 until gridSize) {
            val target = GridPoint2d(x, y)
            val neighbors = target
                .adjacentPoints()
                .filter { it.x in 0 until gridSize }
                .filter { it.y in 0 until gridSize }

            if (target in bugs && (neighbors intersect bugs).size == 1) {
                // Existing bug stays alive.
                result += target
            }

            if (target !in bugs && (neighbors intersect bugs).size in intArrayOf(1, 2)) {
                // Target is infested with a new bug.
                result += target
            }
        }
    }

    return result
}

fun Set<GridPoint2d>.biodiversityRating(): Int {
    return sumBy { coords -> 2.pow(5 * coords.y + coords.x) }
}
