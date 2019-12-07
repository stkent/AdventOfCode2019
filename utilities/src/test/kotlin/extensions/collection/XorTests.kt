package extensions.collection

import extensions.xor
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class XorTests {

    @Test
    fun `xor of set with empty set should be itself`() {
        // Arrange
        val set = setOf(1, 2, 3)

        // Act
        val xor = set.xor(emptySet())

        // Assert
        assertEquals(set, xor)
    }

    @Test
    fun `xor of set with itself should be empty`() {
        // Arrange
        val set = setOf(1, 2, 3)
        val setCopy = set.toSet()

        // Act
        val xor = set.xor(setCopy)

        // Assert
        assertTrue(xor.isEmpty())
    }

    @Test
    fun `xor of set with strict subset should be correct`() {
        // Arrange
        val set1 = setOf("1", "2", "3")
        val set2 = setOf("1", "2")

        // Act
        val xor = set1.xor(set2)

        // Assert
        assertEquals(setOf("3"), xor)
    }

    @Test
    fun `xor of set with strict superset should be correct`() {
        // Arrange
        val set1 = setOf("1", "2", "3")
        val set2 = setOf("1", "2", "3", "4")

        // Act
        val xor = set1.xor(set2)

        // Assert
        assertEquals(setOf("4"), xor)
    }

}
