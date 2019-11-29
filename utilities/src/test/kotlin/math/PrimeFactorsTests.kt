package math

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import primeFactors

class PrimeFactorsTests {

    @Test
    fun `prime factors of 2 are correct`() {
        // Arrange
        val n = 2

        // Act
        val factors = primeFactors(n)

        // Assert
        assertEquals(listOf(2), factors)
    }

    @Test
    fun `prime factors of 3 are correct`() {
        // Arrange
        val n = 3

        // Act
        val factors = primeFactors(n)

        // Assert
        assertEquals(listOf(3), factors)
    }

    @Test
    fun `prime factors of 4 are correct`() {
        // Arrange
        val n = 4

        // Act
        val factors = primeFactors(n)

        // Assert
        assertEquals(listOf(2, 2), factors)
    }

    @Test
    fun `prime factors of 6 are correct`() {
        // Arrange
        val n = 6

        // Act
        val factors = primeFactors(n)

        // Assert
        assertEquals(listOf(2, 3), factors)
    }

    @Test
    fun `prime factors of 8 are correct`() {
        // Arrange
        val n = 8

        // Act
        val factors = primeFactors(n)

        // Assert
        assertEquals(listOf(2, 2, 2), factors)
    }

}
