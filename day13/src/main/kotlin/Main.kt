import OutputState.*
import TileType.*
import kotlin.math.sign

fun main() {
    val inputProgram = resourceFile("input.txt")
        .readLines()
        .first()
        .split(',')
        .map(String::toLong)

    // Part 1
    val (startTiles, _) = Game().play(inputProgram)
    println("Part 1 solution: ${startTiles.count { it.value == Block }}")
//    display(startTiles)

    // Part 2
    val hackedInputProgram = inputProgram
        .toMutableList()
        .apply {
            set(0, 2)
        }

    val (_, endScore) = Game().play(
        hackedInputProgram,
        joystick = joy@ { tiles ->
//            display(tiles)
            val ball = tiles.filterValues { it == Ball }.keys.first()
            val paddle = tiles.filterValues { it == Paddle }.keys.first()

            return@joy (ball.x - paddle.x).sign.toLong()
        }
    )

    println("Part 2 solution: $endScore")
}

private class Game {

    fun play(
        program: List<Long>,
        joystick: ((tiles: Map<GridPoint2d, TileType>) -> Long)? = null
    ): Pair<Map<GridPoint2d, TileType>, Long> {

        val computer = Computer()

        val tiles = mutableMapOf<GridPoint2d, TileType>()
        var outputState: OutputState = AwaitingFirstValue()
        var score = 0L

        computer.execute(
            program = program,
            source = { joystick?.invoke(tiles) ?: 0L },
            sink = { output ->
                outputState.accept(output).let {
                    outputState = it

                    when (it) {
                        is CompletedTile -> tiles += it.location to it.type
                        is CompletedScore -> score = it.score
                    }
                }
            }
        )

        return tiles to score
    }

}

private enum class TileType {
    Empty, Wall, Block, Paddle, Ball
}

private sealed class OutputState {

    abstract fun accept(output: Long): OutputState

    open class AwaitingFirstValue : OutputState() {
        override fun accept(output: Long) = AwaitingSecondValue(first = output)
    }

    class AwaitingSecondValue(val first: Long) : OutputState() {
        override fun accept(output: Long) = if (first == -1L && output == 0L) {
            AwaitingScore
        } else {
            AwaitingTile(location = GridPoint2d(x = first.toInt(), y = output.toInt()))
        }
    }

    class AwaitingTile(val location: GridPoint2d) : OutputState() {
        override fun accept(output: Long) =
            CompletedTile(location, TileType.values()[output.toInt()])
    }

    object AwaitingScore : OutputState() {
        override fun accept(output: Long) = CompletedScore(score = output)
    }

    class CompletedTile(val location: GridPoint2d, val type: TileType) : AwaitingFirstValue()
    class CompletedScore(val score: Long) : AwaitingFirstValue()
}

private fun display(tiles: Map<GridPoint2d, TileType>) {
    val screenBounds = tiles.keys.bounds()

    for (y in screenBounds.bottom..screenBounds.top) {
        for (x in screenBounds.left..screenBounds.right) {
            print(
                when (tiles.getOrDefault(GridPoint2d(x, y), Empty)) {
                    Empty -> ' '
                    Wall -> '█'
                    Block -> '░'
                    Paddle -> '—'
                    Ball -> '●'
                }
            )
        }

        println()
    }
}
