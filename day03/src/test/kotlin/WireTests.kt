import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class WireTests {

    @Test
    fun `example 1 smallest crossing distance`() {
        // Arrange
        val wire1 = "R8,U5,L5,D3"
        val wire2 = "U7,R6,D4,L4"

        // Act
        val distance = smallestCrossingDistance(wire1, wire2)

        // Assert
        assertEquals(6, distance)
    }

    @Test
    fun `example 2 smallest crossing distance`() {
        // Arrange
        val wire1 = "R75,D30,R83,U83,L12,D49,R71,U7,L72"
        val wire2 = "U62,R66,U55,R34,D71,R55,D58,R83"

        // Act
        val distance = smallestCrossingDistance(wire1, wire2)

        // Assert
        assertEquals(159, distance)
    }

    @Test
    fun `example 3 smallest crossing distance`() {
        // Arrange
        val wire1 = "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51"
        val wire2 = "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"

        // Act
        val distance = smallestCrossingDistance(wire1, wire2)

        // Assert
        assertEquals(135, distance)
    }

    @Test
    fun `example 1 least smallest crossing delay`() {
        // Arrange
        val wire1 = "R8,U5,L5,D3"
        val wire2 = "U7,R6,D4,L4"

        // Act
        val delay = smallestCrossingDelay(wire1, wire2)

        // Assert
        assertEquals(30, delay)
    }

    @Test
    fun `example 2 least smallest crossing delay`() {
        // Arrange
        val wire1 = "R75,D30,R83,U83,L12,D49,R71,U7,L72"
        val wire2 = "U62,R66,U55,R34,D71,R55,D58,R83"

        // Act
        val delay = smallestCrossingDelay(wire1, wire2)

        // Assert
        assertEquals(610, delay)
    }

    @Test
    fun `example 3 least smallest crossing delay`() {
        // Arrange
        val wire1 = "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51"
        val wire2 = "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"

        // Act
        val delay = smallestCrossingDelay(wire1, wire2)

        // Assert
        assertEquals(410, delay)
    }

}
