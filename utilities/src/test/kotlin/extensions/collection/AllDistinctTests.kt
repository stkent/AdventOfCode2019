package extensions.collection

import extensions.allDistinct
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AllDistinctTests {

    @Test
    fun `calling on empty collection is disallowed`() {
        // Arrange
        val collection: Collection<Int> = emptyList()

        // Act & Assert
        assertThrows(IllegalArgumentException::class.java, {
            collection.allDistinct()
        }, "This method cannot be called on empty Collections.")
    }

    @Test
    fun `collection of distinct items identified correctly`() {
        // Arrange
        val collection: Collection<Int> = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

        // Act
        val allDistinct = collection.allDistinct()

        // Assert
        assertTrue(allDistinct)
    }

    @Test
    fun `collection of non-distinct items identified correctly`() {
        // Arrange
        val collection: Collection<Int> = listOf(1, 2, 3, 4, 5, 1, 7, 8, 9, 10)

        // Act
        val allDistinct = collection.allDistinct()

        // Assert
        assertFalse(allDistinct)
    }

}
