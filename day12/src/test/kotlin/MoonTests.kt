import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MoonTests {

    @Test
    fun `example 1 total energy`() {
        // Arrange
        val moons = listOf(
            Moon(p = GridPoint3d(-1, 0, 2)),
            Moon(p = GridPoint3d(2, -10, -7)),
            Moon(p = GridPoint3d(4, -8, 8)),
            Moon(p = GridPoint3d(3, 5, -1))
        )

        // Act
        val totalEnergy = totalEnergy(moons, time = 10)

        // Assert
        assertEquals(179, totalEnergy)
    }

    @Test
    fun `example 2 total energy`() {
        // Arrange
        val moons = listOf(
            Moon(p = GridPoint3d(-8, -10, 0)),
            Moon(p = GridPoint3d(5, 5, 10)),
            Moon(p = GridPoint3d(2, -7, 3)),
            Moon(p = GridPoint3d(9, -8, -3))
        )

        // Act
        val totalEnergy = totalEnergy(moons, time = 100)

        // Assert
        assertEquals(1940, totalEnergy)
    }

    @Test
    fun `example 1 cycle length`() {
        // Arrange
        val moons = listOf(
            Moon(p = GridPoint3d(-1, 0, 2)),
            Moon(p = GridPoint3d(2, -10, -7)),
            Moon(p = GridPoint3d(4, -8, 8)),
            Moon(p = GridPoint3d(3, 5, -1))
        )

        // Act
        val cycleLength = cycleLength(moons)

        // Assert
        assertEquals(2772, cycleLength)
    }

    @Test
    fun `example 2 cycle length`() {
        // Arrange
        val moons = listOf(
            Moon(p = GridPoint3d(-8, -10, 0)),
            Moon(p = GridPoint3d(5, 5, 10)),
            Moon(p = GridPoint3d(2, -7, 3)),
            Moon(p = GridPoint3d(9, -8, -3))
        )

        // Act
        val cycleLength = cycleLength(moons)

        // Assert
        assertEquals(4686774924, cycleLength)
    }

}
