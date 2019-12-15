import ShipAreaType.*
import java.lang.IllegalStateException
import java.util.*

fun main() {
    val inputProgram = resourceFile("input.txt")
        .readLines()
        .first()
        .split(',')
        .map(String::toLong)

    val computer = Computer()
    var droidLocation = GridPoint2d.origin
    var droidDirection = GridDirection.N
    val ship = mutableMapOf<GridPoint2d, ShipAreaType>().withDefault { Unknown }.apply {
        put(droidLocation, Floor)
    }

    computer.execute(
        program = inputProgram,
        source = source@{
            val stdInput = Scanner(System.`in`).nextLong()

            droidDirection = when (stdInput) {
                1L -> GridDirection.N
                2L -> GridDirection.S
                3L -> GridDirection.W
                4L -> GridDirection.E
                else -> throw IllegalStateException("Invalid input received")
            }

            return@source stdInput
        },
        sink = { output ->
            val shipAreaType = values()[output.toInt()]
            val targetLocation = droidLocation + droidDirection.toUnitVector()
            ship += targetLocation to shipAreaType
            if (shipAreaType != Wall) droidLocation = targetLocation
            displayShip(ship, droidLocation)
        }
    )
}

fun displayShip(ship: Map<GridPoint2d, ShipAreaType>, droidLocation: GridPoint2d) {
    val (left, right, bottom, top) = ship.keys.bounds()

    for (y in top downTo bottom) {
        for (x in left..right) {
            val location = GridPoint2d(x, y)

            print(
                when (location) {
                    droidLocation -> 'D'
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
