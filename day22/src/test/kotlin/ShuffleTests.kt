import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ShuffleTests {

    @Test
    fun `{final index of card} deal into new stack`() {
        // Arrange
        val shuffleSteps = listOf("deal into new stack")
        val deckSize = 10L

        // Act
        val shuffledDeck = (0L until 10L).map { finalIndexOfCard(it, shuffleSteps, deckSize) }

        // Assert
        assertEquals(listOf(9L, 8L, 7L, 6L, 5L, 4L, 3L, 2L, 1L, 0L), shuffledDeck)
    }

    @Test
    fun `{final index of card} cut (positive)`() {
        // Arrange
        val shuffleSteps = listOf("cut 3")
        val deckSize = 10L

        // Act
        val shuffledDeck = (0L until 10L).map { finalIndexOfCard(it, shuffleSteps, deckSize) }

        // Assert
        assertEquals(listOf(7L, 8L, 9L, 0L, 1L, 2L, 3L, 4L, 5L, 6L), shuffledDeck)
    }

    @Test
    fun `{final index of card} cut (negative)`() {
        // Arrange
        val shuffleSteps = listOf("cut -4")
        val deckSize = 10L

        // Act
        val shuffledDeck = (0L until 10L).map { finalIndexOfCard(it, shuffleSteps, deckSize) }

        // Assert
        assertEquals(listOf(4L, 5L, 6L, 7L, 8L, 9L, 0L, 1L, 2L, 3L), shuffledDeck)
    }

    @Test
    fun `{final index of card} deal with increment`() {
        // Arrange
        val shuffleSteps = listOf("deal with increment 3")
        val deckSize = 10L

        // Act
        val shuffledDeck = (0L until 10L).map { finalIndexOfCard(it, shuffleSteps, deckSize) }

        // Assert
        assertEquals(listOf(0L, 3L, 6L, 9L, 2L, 5L, 8L, 1L, 4L, 7L), shuffledDeck)
    }

    @Test
    fun `{final index of card} example 1`() {
        // Arrange
        val shuffleSteps = """
            deal with increment 7
            deal into new stack
            deal into new stack
        """.trimIndent().split('\n')

        val deckSize = 10L

        // Act
        val shuffledDeck = (0L until 10L).map { finalIndexOfCard(it, shuffleSteps, deckSize) }

        // Assert
        assertEquals(listOf(0L, 7L, 4L, 1L, 8L, 5L, 2L, 9L, 6L, 3L), shuffledDeck)
    }

    @Test
    fun `{final index of card} example 2`() {
        // Arrange
        val shuffleSteps = """
            cut 6
            deal with increment 7
            deal into new stack
        """.trimIndent().split('\n')

        val deckSize = 10L

        // Act
        val shuffledDeck = (0L until 10L).map { finalIndexOfCard(it, shuffleSteps, deckSize) }

        // Assert
        assertEquals(listOf(1L, 4L, 7L, 0L, 3L, 6L, 9L, 2L, 5L, 8L), shuffledDeck)
    }

    @Test
    fun `{final index of card} example 3`() {
        // Arrange
        val shuffleSteps = """
            deal with increment 7
            deal with increment 9
            cut -2
        """.trimIndent().split('\n')

        val deckSize = 10L

        // Act
        val shuffledDeck = (0L until 10L).map { finalIndexOfCard(it, shuffleSteps, deckSize) }

        // Assert
        assertEquals(listOf(2L, 5L, 8L, 1L, 4L, 7L, 0L, 3L, 6L, 9L), shuffledDeck)
    }

    @Test
    fun `{final index of card} example 4`() {
        // Arrange
        val shuffleSteps = """
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

        val deckSize = 10L

        // Act
        val shuffledDeck = (0L until 10L).map { finalIndexOfCard(it, shuffleSteps, deckSize) }

        // Assert
        assertEquals(listOf(7L, 4L, 1L, 8L, 5L, 2L, 9L, 6L, 3L, 0L), shuffledDeck)
    }

    @Test
    fun `{final card at index} deal into new stack`() {
        // Arrange
        val shuffleSteps = listOf("deal into new stack")
        val deckSize = 10L

        // Act
        val shuffledDeck = (0L until 10L).map { finalCardAtIndex(it, shuffleSteps, deckSize) }

        // Assert
        assertEquals(listOf(9L, 8L, 7L, 6L, 5L, 4L, 3L, 2L, 1L, 0L), shuffledDeck)
    }

    @Test
    fun `{final card at index} cut (positive)`() {
        // Arrange
        val shuffleSteps = listOf("cut 3")
        val deckSize = 10L

        // Act
        val shuffledDeck = (0L until 10L).map { finalCardAtIndex(it, shuffleSteps, deckSize) }

        // Assert
        assertEquals(listOf(3L, 4L, 5L, 6L, 7L, 8L, 9L, 0L, 1L, 2L), shuffledDeck)
    }

    @Test
    fun `{final card at index} cut (negative)`() {
        // Arrange
        val shuffleSteps = listOf("cut -4")
        val deckSize = 10L

        // Act
        val shuffledDeck = (0L until 10L).map { finalCardAtIndex(it, shuffleSteps, deckSize) }

        // Assert
        assertEquals(listOf(6L, 7L, 8L, 9L, 0L, 1L, 2L, 3L, 4L, 5L), shuffledDeck)
    }

    @Test
    fun `{final card at index} deal with increment`() {
        // Arrange
        val shuffleSteps = listOf("deal with increment 3")
        val deckSize = 10L

        // Act
        val shuffledDeck = (0L until 10L).map { finalCardAtIndex(it, shuffleSteps, deckSize) }

        // Assert
        assertEquals(listOf(0L, 7L, 4L, 1L, 8L, 5L, 2L, 9L, 6L, 3L), shuffledDeck)
    }

    @Test
    fun `{final card at index} example 1`() {
        // Arrange
        val shuffleSteps = """
            deal with increment 7
            deal into new stack
            deal into new stack
        """.trimIndent().split('\n')

        val deckSize = 10L

        // Act
        val shuffledDeck = (0L until 10L).map { finalCardAtIndex(it, shuffleSteps, deckSize) }

        // Assert
        assertEquals(listOf(0L, 3L, 6L, 9L, 2L, 5L, 8L, 1L, 4L, 7L), shuffledDeck)
    }

    @Test
    fun `{final card at index} example 2`() {
        // Arrange
        val shuffleSteps = """
            cut 6
            deal with increment 7
            deal into new stack
        """.trimIndent().split('\n')

        val deckSize = 10L

        // Act
        val shuffledDeck = (0L until 10L).map { finalCardAtIndex(it, shuffleSteps, deckSize) }

        // Assert
        assertEquals(listOf(3L, 0L, 7L, 4L, 1L, 8L, 5L, 2L, 9L, 6L), shuffledDeck)
    }

    @Test
    fun `{final card at index} example 3`() {
        // Arrange
        val shuffleSteps = """
            deal with increment 7
            deal with increment 9
            cut -2
        """.trimIndent().split('\n')

        val deckSize = 10L

        // Act
        val shuffledDeck = (0L until 10L).map { finalCardAtIndex(it, shuffleSteps, deckSize) }

        // Assert
        assertEquals(listOf(6L, 3L, 0L, 7L, 4L, 1L, 8L, 5L, 2L, 9L), shuffledDeck)
    }

    @Test
    fun `{final card at index} example 4`() {
        // Arrange
        val shuffleSteps = """
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

        val deckSize = 10L

        // Act
        val shuffledDeck = (0L until 10L).map { finalCardAtIndex(it, shuffleSteps, deckSize) }

        // Assert
        assertEquals(listOf(9L, 2L, 5L, 8L, 1L, 4L, 7L, 0L, 3L, 6L), shuffledDeck)
    }

}
