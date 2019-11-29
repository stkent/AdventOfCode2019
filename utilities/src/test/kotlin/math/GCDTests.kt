package math

import gcd
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GCDTests {

    @Test
    fun `gcd of positive integer with itself should be itself`() {
        // Arrange
        val n = 72

        // Act
        val gcd = gcd(n, n)

        // Assert
        assertEquals(n, gcd)
    }

    @Test
    fun `gcd of coprime positive integers should be 1`() {
        // Arrange
        val n1 = 8
        val n2 = 9

        // Act
        val gcd = gcd(n1, n2)

        // Assert
        assertEquals(1, gcd)
    }

    @Test
    fun `gcd of non-coprime positive integers should be correct`() {
        // Arrange
        val n1 = 54
        val n2 = 24

        // Act
        val gcd = gcd(n1, n2)

        // Assert
        assertEquals(6, gcd)
    }

    @Test
    fun `gcd of positive integer with 0 should be 0`() {
        // Arrange
        val n = 9

        // Act
        val gcd = gcd(n, 0)

        // Assert
        assertEquals(0, gcd)
    }

}
