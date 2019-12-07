import extensions.xor

fun main() {
    val input = resourceFile("input.txt").readLines()

    val orbits = input
        .map { it.split(')') }
        .map { (body, moon) -> body isOrbitedBy moon }
        .toSet()

    println("Part 1 solution: ${totalOrbits(orbits)}")
    println("Part 2 solution: ${orbitalTransfers(orbits)}")
}

fun totalOrbits(orbits: Set<Orbit>): Int {
    val moons = orbits.map { (_, moon) -> moon }.toSet()
    return moons.sumBy { moon -> orbits.chainTo(moon).size - 1 }
}

fun orbitalTransfers(orbits: Set<Orbit>): Int {
    return (orbits.chainTo("YOU").toSet() xor orbits.chainTo("SAN").toSet()).size - 2
}

private fun Set<Orbit>.chainTo(target: Body): List<Body> {
    return firstOrNull { (_, moon) -> moon == target }
        ?.let { (body, moon) -> chainTo(body) + moon } ?: listOf(target)
}

typealias Body = String
typealias Orbit = Pair<Body, Body>
infix fun Body.isOrbitedBy(other: Body): Orbit = this to other
