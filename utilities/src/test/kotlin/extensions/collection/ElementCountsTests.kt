package extensions.collection

import extensions.elementCounts
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ElementCountsTests {

    @Test
    fun `count of elements of empty iterable should be an empty map`() {
        // Arrange
        val iterable: Iterable<Int> = emptyList()

        // Act
        val elementCounts = iterable.elementCounts()

        // Assert
        assertEquals(emptyMap<Int, Int>(), elementCounts)
    }

    @Test
    fun `count of elements of iterable with one element should be correct`() {
        // Arrange
        val iterable: Iterable<String> = listOf("first")

        // Act
        val elementCounts = iterable.elementCounts()

        // Assert
        assertEquals(mapOf("first" to 1), elementCounts)
    }

    @Test
    fun `count of elements of iterable with two distinct entries should be correct`() {
        // Arrange
        val iterable: Iterable<String> = listOf("first", "second")

        // Act
        val elementCounts = iterable.elementCounts()

        // Assert
        assertEquals(mapOf("first" to 1, "second" to 1), elementCounts)
    }

    @Test
    fun `count of elements of iterable with one repeated element should be correct`() {
        // Arrange
        val iterable: Iterable<String> = listOf("first", "second", "first")

        // Act
        val elementCounts = iterable.elementCounts()

        // Assert
        assertEquals(2, elementCounts["first"])
    }

    @Test
    fun `count of element not in iterable should be null`() {
        // Arrange
        val iterable: Iterable<String> = listOf("first", "second", "first")

        // Act
        val elementCounts = iterable.elementCounts()

        // Assert
        assertEquals(null, elementCounts["four"])
    }

}
