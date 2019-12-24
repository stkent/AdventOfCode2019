import OutputMode.*
import PaintColor.Black
import PaintColor.White

fun main() {
    val inputProgram = Computer.parseProgramFile("input.txt")

    println("Part 1 solution: ${Robot().paint(inputProgram, startColor = Black).size}")

    val part2PaintedPanels = Robot().paint(inputProgram, startColor = White)
    val (minX, maxX, minY, maxY) = part2PaintedPanels.keys.bounds()

    println("Part 2 solution:")
    for (y in minY..maxY) {           // } Together, equivalent to 180° rotation about (0, 0) to
        for (x in maxX downTo minX) { // } undo effect of inverted y-coordinate.
            val color = part2PaintedPanels.getOrDefault(GridPoint2d(x, y), Black)
            print(if (color == White) "█" else ' ')
        }

        println()
    }
}

private class Robot {

    fun paint(program: List<Long>, startColor: PaintColor): Map<GridPoint2d, PaintColor> {
        val computer = Computer()
        val paintedPanels = mutableMapOf<GridPoint2d, PaintColor>().apply {
            put(GridPoint2d.origin, startColor)
        }

        var position = GridPoint2d.origin
        var direction = GridDirection.S // Accommodate flipped y-coordinate.
        var outputMode = Paint

        computer.execute(
            program = program,
            source = {
                Computer.Input.Value(paintedPanels.getOrDefault(position, Black).ordinal.toLong())
            },
            sink = { output ->
                when (outputMode) {
                    Paint -> paintedPanels[position] = PaintColor.values()[output.toInt()]
                    Move -> {
                        direction = if (output == 0L) direction.left90() else direction.right90()
                        position += direction.toVector()
                    }
                }

                outputMode = outputMode.next()
            }
        )

        return paintedPanels
    }

}

private enum class PaintColor {
    Black, White
}

private enum class OutputMode {
    Paint, Move;

    fun next(): OutputMode {
        val values = values()
        return values[(ordinal + 1) % values.size]
    }
}
