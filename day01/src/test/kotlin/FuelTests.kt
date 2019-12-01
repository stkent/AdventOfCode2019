import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FuelTests {

    @Test
    fun `mass 12 base launch fuel`() {
        // Arrange
        val module = Module(mass = Mass(12))

        // Act
        val baseLaunchFuel = module.baseLaunchFuel

        // Assert
        assertEquals(Mass(2), baseLaunchFuel)
    }

    @Test
    fun `mass 14 base launch fuel`() {
        // Arrange
        val module = Module(mass = Mass(14))

        // Act
        val baseLaunchFuel = module.baseLaunchFuel

        // Assert
        assertEquals(Mass(2), baseLaunchFuel)
    }

    @Test
    fun `mass 1969 base launch fuel`() {
        // Arrange
        val module = Module(mass = Mass(1969))

        // Act
        val baseLaunchFuel = module.baseLaunchFuel

        // Assert
        assertEquals(Mass(654), baseLaunchFuel)
    }

    @Test
    fun `mass 100756 base launch fuel`() {
        // Arrange
        val module = Module(mass = Mass(100756))

        // Act
        val baseLaunchFuel = module.baseLaunchFuel

        // Assert
        assertEquals(Mass(33583), baseLaunchFuel)
    }

    @Test
    fun `mass 14 total launch fuel`() {
        // Arrange
        val module = Module(mass = Mass(14))

        // Act
        val baseLaunchFuel = module.totalLaunchFuel

        // Assert
        assertEquals(Mass(2), baseLaunchFuel)
    }

    @Test
    fun `mass 1969 total launch fuel`() {
        // Arrange
        val module = Module(mass = Mass(1969))

        // Act
        val totalLaunchFuel = module.totalLaunchFuel

        // Assert
        assertEquals(Mass(966), totalLaunchFuel)
    }

    @Test
    fun `mass 100756 total launch fuel`() {
        // Arrange
        val module = Module(mass = Mass(100756))

        // Act
        val totalLaunchFuel = module.totalLaunchFuel

        // Assert
        assertEquals(Mass(50346), totalLaunchFuel)
    }

}