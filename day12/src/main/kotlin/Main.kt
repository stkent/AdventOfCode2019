import extensions.firstRepeatIndex
import kotlin.math.sign

fun main() {
    val inputMoons = listOf(
        Moon(p = GridPoint3d(4, 1, 1)),
        Moon(p = GridPoint3d(11, -18, -1)),
        Moon(p = GridPoint3d(-2, -10, -4)),
        Moon(p = GridPoint3d(-7, -2, 14))
    )

    println("Part 1 solution: ${totalEnergy(inputMoons, time = 1000)}")
    println("Part 2 solution: ${cycleLength(inputMoons)}")
}

data class Moon(val p: GridPoint3d, val v: GridVector3d = GridVector3d(0, 0, 0)) {

    val energy: Int = p.l1DistanceTo(GridPoint3d.origin) * v.l1Magnitude

    fun updateVDueTo(other: Moon): Moon {
        return copy(
            v = v + p.vectorTo(other.p)
                .let { GridVector3d(x = it.x.sign, y = it.y.sign, z = it.z.sign) }
        )
    }

    fun move() = copy(p = p + v)

}

fun totalEnergy(startMoons: List<Moon>, time: Int): Int {
    return generateSequence(startMoons, List<Moon>::evolve)
        .elementAt(time)
        .sumBy(Moon::energy)
}

fun cycleLength(startMoons: List<Moon>): Long {
    fun cycleLength(pSlice: GridPoint3d.() -> Int, vSlice: GridVector3d.() -> Int): Int {
        return generateSequence(startMoons, List<Moon>::evolve)
            .map { moons -> moons.map { moon -> moon.p.pSlice() to moon.v.vSlice() } }
            .firstRepeatIndex()!!
    }

    val xCycle = cycleLength(GridPoint3d::x, GridVector3d::x)
    val yCycle = cycleLength(GridPoint3d::y, GridVector3d::y)
    val zCycle = cycleLength(GridPoint3d::z, GridVector3d::z)

    return lcm(xCycle.toLong(), lcm(yCycle, zCycle))
}

private fun List<Moon>.evolve(): List<Moon> {
    return map { orbited ->
        (this - orbited) // Assumes no moons can "merge"!
            .fold(orbited) { newOrbited, orbiting -> newOrbited.updateVDueTo(orbiting) }
            .move()
    }
}
