import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ShuffleTests {

    @Test
    fun `deal into new stack`() {
        // Arrange
        val rawSteps = listOf("deal into new stack")
        val deckSize = 10u

        // Act
        val shuffledDeck = shuffleDeck(rawSteps, deckSize)

        // Assert
        assertEquals((9u downTo 0u).toList(), shuffledDeck)
    }

    @Test
    fun `cut (positive cut size)`() {
        // Arrange
        val rawSteps = listOf("cut 3")
        val deckSize = 10u

        // Act
        val shuffledDeck = shuffleDeck(rawSteps, deckSize)

        // Assert
        assertEquals(listOf(3u, 4u, 5u, 6u, 7u, 8u, 9u, 0u, 1u, 2u), shuffledDeck)
    }

    @Test
    fun `cut (negative cut size)`() {
        // Arrange
        val rawSteps = listOf("cut -4")
        val deckSize = 10u

        // Act
        val shuffledDeck = shuffleDeck(rawSteps, deckSize)

        // Assert
        assertEquals(listOf(6u, 7u, 8u, 9u, 0u, 1u, 2u, 3u, 4u, 5u), shuffledDeck)
    }

    @Test
    fun `deal with increment`() {
        // Arrange
        val rawSteps = listOf("deal with increment 3")
        val deckSize = 10u

        // Act
        val shuffledDeck = shuffleDeck(rawSteps, deckSize)

        // Assert
        assertEquals(listOf(0u, 7u, 4u, 1u, 8u, 5u, 2u, 9u, 6u, 3u), shuffledDeck)
    }

    @Test
    fun `multi step shuffle 1`() {
        // Arrange
        val rawSteps = """
            deal with increment 7
            deal into new stack
            deal into new stack
        """.trimIndent().split('\n')

        val deckSize = 10u

        // Act
        val shuffledDeck = shuffleDeck(rawSteps, deckSize)

        // Assert
        assertEquals(listOf(0u, 3u, 6u, 9u, 2u, 5u, 8u, 1u, 4u, 7u), shuffledDeck)
    }

    @Test
    fun `multi step shuffle 2`() {
        // Arrange
        val rawSteps = """
            cut 6
            deal with increment 7
            deal into new stack
        """.trimIndent().split('\n')

        val deckSize = 10u

        // Act
        val shuffledDeck = shuffleDeck(rawSteps, deckSize)

        // Assert
        assertEquals(listOf(3u, 0u, 7u, 4u, 1u, 8u, 5u, 2u, 9u, 6u), shuffledDeck)
    }

    @Test
    fun `multi step shuffle 3`() {
        // Arrange
        val rawSteps = """
            deal with increment 7
            deal with increment 9
            cut -2
        """.trimIndent().split('\n')

        val deckSize = 10u

        // Act
        val shuffledDeck = shuffleDeck(rawSteps, deckSize)

        // Assert
        assertEquals(listOf(6u, 3u, 0u, 7u, 4u, 1u, 8u, 5u, 2u, 9u), shuffledDeck)
    }

    @Test
    fun `multi step shuffle 4`() {
        // Arrange
        val rawSteps = """
            deal into new stack
            cut -2
            deal with increment 7
            cut 8
            cut -4
            deal with increment 7
            cut 3
            deal with increment 9
            deal with increment 3
            cut -1
        """.trimIndent().split('\n')

        val deckSize = 10u

        // Act
        val shuffledDeck = shuffleDeck(rawSteps, deckSize)

        // Assert
        assertEquals(listOf(9u, 2u, 5u, 8u, 1u, 4u, 7u, 0u, 3u, 6u), shuffledDeck)
    }

}
