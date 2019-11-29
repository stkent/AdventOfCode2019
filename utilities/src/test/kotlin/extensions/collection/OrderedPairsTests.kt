package extensions.collection

import extensions.orderedPairs
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OrderedPairsTests {

    @Test
    fun `ordered pairs of empty collection should be empty`() {
        // Arrange
        val collection: Collection<Int> = emptyList()

        // Act
        val orderedPairs = collection.orderedPairs()

        // Assert
        assertEquals(emptyMap<Int, Int>(), orderedPairs)
    }

    @Test
    fun `ordered pairs of collection with 1 element should be empty`() {
        // Arrange
        val collection: Collection<Int> = listOf(9)

        // Act
        val orderedPairs = collection.orderedPairs()

        // Assert
        assertEquals(emptyMap<Int, Int>(), orderedPairs)
    }

    @Test
    fun `ordered pairs of collection with 2 elements should contain correct pairs`() {
        // Arrange
        val collection: Collection<Int> = listOf(4, 7)

        // Act
        val orderedPairs = collection.orderedPairs()

        // Assert
        assertEquals(
            mapOf(
                (4 to 7) to 1,
                (7 to 4) to 1
            ),
            orderedPairs
        )
    }

    @Test
    fun `ordered pairs of collection with 3 elements should contain correct pairs`() {
        // Arrange
        val collection: Collection<Int> = listOf(2, 5, 8)

        // Act
        val orderedPairs = collection.orderedPairs()

        // Assert
        assertEquals(
            mapOf(
                (2 to 5) to 1,
                (2 to 8) to 1,
                (5 to 8) to 1,
                (5 to 2) to 1,
                (8 to 2) to 1,
                (8 to 5) to 1
            ),
            orderedPairs
        )
    }

    @Test
    fun `ordered pairs of collection with 4 elements should contain correct pairs`() {
        // Arrange
        val collection: Collection<Int> = listOf(3, 6, 9, 11)

        // Act
        val orderedPairs = collection.orderedPairs()

        // Assert
        assertEquals(
            mapOf(
                (3 to 6) to 1,
                (3 to 9) to 1,
                (3 to 11) to 1,
                (6 to 9) to 1,
                (6 to 11) to 1,
                (9 to 11) to 1,
                (6 to 3) to 1,
                (9 to 3) to 1,
                (11 to 3) to 1,
                (9 to 6) to 1,
                (11 to 6) to 1,
                (11 to 9) to 1
            ),
            orderedPairs
        )
    }

}
