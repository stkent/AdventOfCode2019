import kotlin.math.absoluteValue

fun main() {
    val inputProgram = resourceFile("input.txt")
        .readLines()
        .first()
        .split(',')
        .map(String::toLong)

    // Part 1
    val scaffoldMap = mapScaffold(program = inputProgram)
//    printScaffoldMap(scaffoldMap)
    println("Part 1 solution: ${computeAlignmentParameterSum(scaffoldMap)}")

    // Part 2
    //    ........................................AAAAAAAAAAAAA..
    //    ........................................A...........A..
    //    ........CBBBBBB.........................A...........A..
    //    ........C.....B.........................A...........A..
    //    ........C.....B.AAAAAAAAAAAAA...........A...........A..
    //    ........C.....B.A...........B...........A...........A..
    //    ........C.....B.A...........B...........A...........A..
    //    ........C.....B.A...........B...........A...........A..
    //    ........CCCCCC#C#CCCC.......B...........A...........A..
    //    ..............B.A...C.......B...........A...........A..
    //    ..............B.A.BB#BBBBBBBB.....CCCCCCC...........A..
    //    ..............B.A.B.C.............C.................A..
    //    ..............BB#BBB#BBBB.........C.......CCCCCCCCCCA..
    //    ................A.B.C...C.........C.......C............
    //    ................A.B.C...C.........C.......C............
    //    ................A.B.C...C.........C.......C............
    //    ................AA#A#AAA#A^.......C.......C............
    //    ..................B.C...C.........C.......C............
    //    ..........BCCCCCCC#CC...C.........C.......C............
    //    ..........B.......B.....C.........C.......C............
    //    ..........B.......BAAAAA#AAAA.....C.......C............
    //    ..........B.............C...A.....C.......C............
    //    ......CCCC#CCCCCC.......CCCC#CCCCCC.......CCCCCCCCCCCCC
    //    ......C...B.....C...........A.........................C
    //    ......C...B.....C...........A.........................C
    //    ......C...B.....C...........A.........................C
    //    ......C...B.....C...........A.........................C
    //    ......C...B.....C...........A.........................C
    //    BBBBBB#BBBB.....C...........A.........................C
    //    B.....C.........C...........A..........................
    //    B.....C.........C...........A..........................
    //    B.....C.........C...........A..........................
    //    B.....C.........AAAAAAAAAAAAA..........................
    //    B.....C................................................
    //    CCCCCCC................................................

    val inputs = listOf(
        "A,B,A,C,B,C,B,C,A,C\n", // Main routine
        "L,10,R,12,R,12\n",      // Routine A
        "R,6,R,10,L,10\n",       // Routine B
        "R,10,L,10,L,12,R,6\n",  // Routine C
        "n\n"                    // Video feed y/n
    ).flatMap { it.map(Char::toLong).map(Computer.Input::Value) }.iterator()

    val outputs = mutableListOf<Long>()
    Computer().execute(
        program = inputProgram
            .toMutableList()
            .apply {
                set(0, 2)
            },
        source = { inputs.next() },
        sink = { outputs += it })

    println("Part 2 solution: ${outputs.last()}")
}

fun computeAlignmentParameterSum(scaffoldMap: Map<GridPoint2d, Char>): Int {
    return scaffoldMap
        .entries
        .fold(0) { acc, (coord, view) ->
            return@fold if (view == '#' && coord.adjacentPoints().all { scaffoldMap[it] == '#' }) {
                acc + (coord.x * coord.y).absoluteValue
            } else {
                acc
            }
        }
}

private fun mapScaffold(program: List<Long>): Map<GridPoint2d, Char> {
    val result = mutableMapOf<GridPoint2d, Char>()
    var x = 0
    var y = 0

    Computer().execute(
        program = program,
        sink = { output ->
            when (output) {
                10L -> {
                    y--
                    x = 0
                }

                else -> result += GridPoint2d(x++, y) to output.toChar()
            }
        })

    return result
}

private fun printScaffoldMap(scaffoldMap: Map<GridPoint2d, Char>) {
    println("Scaffold map: ")

    scaffoldMap.keys.bounds().let { (left, right, bottom, top) ->
        for (y in top downTo bottom) {
            for (x in left..right) {
                print(scaffoldMap[GridPoint2d(x, y)])
            }

            println()
        }
    }
}
