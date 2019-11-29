package extensions.pair

import extensions.flip
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FlipTests {

    @Test
    fun `flip of symmetric pair should equal original pair`() {
        // Arrange
        val n = 1
        val pair = n to n

        // Act
        val flippedPair = pair.flip()

        // Assert
        assertEquals(pair, flippedPair)
    }

    @Test
    fun `flip of asymmetric pair should be correct`() {
        // Arrange
        val n1 = 1
        val n2 = "2"
        val pair = n1 to n2

        // Act
        val flippedPair = pair.flip()

        // Assert
        assertEquals(n2 to n1, flippedPair)
    }

}
