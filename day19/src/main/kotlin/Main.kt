fun main() {
    val inputProgram = resourceFile("input.txt")
        .readLines()
        .first()
        .split(',')
        .map(String::toLong)

    val drone = Drone(program = inputProgram)
    println("Part 1 solution: ${drone.affectedCoordCount()}")
//    drone.printNearbyMap()

    drone.shipNWCorner().let { topLeftCorner ->
        println("Part 2 solution: ${10000 * topLeftCorner.x + topLeftCorner.y}")
    }
}

private class Drone(val program: List<Long>) {

    fun affectedCoordCount(): Int {
        return (0..49).sumBy { x ->
            (0..49).count { y ->
                isCoordInBeam(GridPoint2d(x, y))
            }
        }
    }

    fun printNearbyMap() {
        return (0..49).forEach { x ->
            (0..49).forEach { y ->
                print(if (isCoordInBeam(GridPoint2d(x, y))) '#' else '.')
            }

            println()
        }
    }

    fun shipNWCorner(): GridPoint2d {
        var nwCorner = GridPoint2d.origin
        fun neCorner() = nwCorner.shiftBy(dx = 99)
        fun swCorner() = nwCorner.shiftBy(dy = 99)

        fun shiftRight() { nwCorner = nwCorner.shiftBy(dx = 1) }
        fun shiftDown() { nwCorner = nwCorner.shiftBy(dy = 1) }

        while (!neCorner().isInBeam()) {
            shiftDown()

            while (!swCorner().isInBeam()) {
                shiftRight()
            }
        }

        return nwCorner
    }

    fun isCoordInBeam(coord: GridPoint2d): Boolean {
        val inputs = listOf(coord.x, coord.y)
            .map(Int::toLong)
            .map(Computer.Input::Value)
            .iterator()

        var result = false

        Computer().execute(
            program = program,
            source = { inputs.next() },
            sink = { output -> result = output == 1L }
        )

        return result
    }

    private fun GridPoint2d.isInBeam() = isCoordInBeam(this)

}
