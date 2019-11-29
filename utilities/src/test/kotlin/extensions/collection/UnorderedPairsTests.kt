package extensions.collection

import extensions.unorderedPairs
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UnorderedPairsTests {

    @Test
    fun `unordered pairs of empty collection should be empty`() {
        // Arrange
        val collection: Collection<Int> = emptyList()

        // Act
        val unorderedPairs = collection.unorderedPairs()

        // Assert
        assertEquals(emptyMap<Int, Int>(), unorderedPairs)
    }

    @Test
    fun `unordered pairs of collection with 1 element should be empty`() {
        // Arrange
        val collection: Collection<Int> = listOf(9)

        // Act
        val unorderedPairs = collection.unorderedPairs()

        // Assert
        assertEquals(emptyMap<Int, Int>(), unorderedPairs)
    }

    @Test
    fun `unorderedPairs pairs of collection with 2 elements should contain correct pairs`() {
        // Arrange
        val collection: Collection<Int> = listOf(4, 7)

        // Act
        val unorderedPairs = collection.unorderedPairs()

        // Assert
        assertEquals(mapOf((4 to 7) to 1), unorderedPairs)
    }

    @Test
    fun `unorderedPairs pairs of collection with 3 elements should contain correct pairs`() {
        // Arrange
        val collection: Collection<Int> = listOf(2, 5, 5)

        // Act
        val unorderedPairs = collection.unorderedPairs()

        // Assert
        assertEquals(
            mapOf(
                (2 to 5) to 2,
                (5 to 5) to 1
            ),
            unorderedPairs
        )
    }

    @Test
    fun `unorderedPairs pairs of collection with 4 elements should contain correct pairs`() {
        // Arrange
        val collection: Collection<Int> = listOf(3, 6, 9, 9)

        // Act
        val unorderedPairs = collection.unorderedPairs()

        // Assert
        assertEquals(
            mapOf(
                (3 to 6) to 1,
                (3 to 9) to 2,
                (6 to 9) to 2,
                (9 to 9) to 1
            ),
            unorderedPairs
        )
    }

}
