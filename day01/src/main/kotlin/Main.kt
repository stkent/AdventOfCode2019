fun main() {
    val modules = resourceFile("input.txt")
        .readLines()
        .map { Module(mass = Mass(it.toInt())) }

    // Part 1:
    modules
        // sumBy operates on Ints rather than on {any type with a plusAssign operator} so we need to
        // call Mass::rawValue and retrieve the backing Int here. Syntax like
        // Module::baseLaunchFuel::rawValue is not supported.
        .sumBy { module -> module.baseLaunchFuel.rawValue }
        .also { allFuel -> println("Part 1 solution: $allFuel") }

    // Part 2:
    modules
        .sumBy { module -> module.totalLaunchFuel.rawValue }
        .also { allFuel -> println("Part 2 solution: $allFuel") }
}

class Module(mass: Mass) {
    val baseLaunchFuel: Mass = mass.baseLaunchFuel()
    val totalLaunchFuel: Mass = mass.totalLaunchFuel()
}

inline class Mass(val rawValue: Int) {
    fun baseLaunchFuel() = Mass(((rawValue / 3) - 2).coerceAtLeast(0))

    fun totalLaunchFuel(): Mass {
        val baseLaunchFuel = baseLaunchFuel()
        if (baseLaunchFuel.rawValue == 0) return baseLaunchFuel
        return baseLaunchFuel + baseLaunchFuel.totalLaunchFuel()
    }

    // Unfortunate that we have to define this and cannot rely on Int::plusAssign:
    operator fun plus(other: Mass): Mass = Mass(this.rawValue + other.rawValue)
}
