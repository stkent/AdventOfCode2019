package extensions.collection

import extensions.allMatch
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AllMatchTests {

    @Test
    fun `calling on empty collection is disallowed`() {
        // Arrange
        val collection: Collection<Int> = emptyList()

        // Act & Assert
        assertThrows(IllegalArgumentException::class.java, {
            collection.allMatch()
        }, "This method cannot be called on empty Collections.")
    }

    @Test
    fun `collection of non-matching items identified correctly`() {
        // Arrange
        val collection: Collection<Int> = listOf(1, 1, 1, 2, 1)

        // Act
        val allMatch = collection.allMatch()

        // Assert
        assertFalse(allMatch)
    }

    @Test
    fun `collection of matching items identified correctly`() {
        // Arrange
        val collection: Collection<Int> = listOf(1, 1, 1, 1, 1)

        // Act
        val allMatch = collection.allMatch()

        // Assert
        assertTrue(allMatch)
    }

}
