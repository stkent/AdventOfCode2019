package math

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import primes

class PrimesTests {

    @Test
    fun `no primes less than or equal to 1`() {
        // Arrange
        val n = 1

        // Act
        val primes = primes().takeWhile { it <= n }.toList()

        // Assert
        assertEquals(emptyList<Int>(), primes)
    }

    @Test
    fun `single prime less than or equal to 2`() {
        // Arrange
        val n = 2

        // Act
        val primes = primes().takeWhile { it <= n }.toList()

        // Assert
        assertEquals(listOf(2), primes)
    }

    @Test
    fun `two primes less than or equal to 3`() {
        // Arrange
        val n = 3

        // Act
        val primes = primes().takeWhile { it <= n }.toList()

        // Assert
        assertEquals(listOf(2, 3), primes)
    }

    @Test
    fun `two primes less than or equal to 4`() {
        // Arrange
        val n = 4

        // Act
        val primes = primes().takeWhile { it <= n }.toList()

        // Assert
        assertEquals(listOf(2, 3), primes)
    }

    @Test
    fun `three primes less than or equal to 5`() {
        // Arrange
        val n = 5

        // Act
        val primes = primes().takeWhile { it <= n }.toList()

        // Assert
        assertEquals(listOf(2, 3, 5), primes)
    }

    @Test
    fun `correct primes less than or equal to 30`() {
        // Arrange
        val n = 30

        // Act
        val primes = primes().takeWhile { it <= n }.toList()

        // Assert
        assertEquals(listOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29), primes)
    }

}
