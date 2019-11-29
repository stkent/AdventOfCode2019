package extensions

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class CharTests {

    @Test
    fun `char repeated zero times is correct`() {
        // Arrange
        val char = 'f'

        // Act
        val repeatedChar = char.repeated(0)

        // Assert
        assertEquals("", repeatedChar)
    }

    @Test
    fun `char repeated positive number of times is correct`() {
        // Arrange
        val char = 'f'

        // Act
        val repeatedChar = char.repeated(5)

        // Assert
        assertEquals("fffff", repeatedChar)
    }

    @Test
    fun `char repeated negative number of times is disallowed`() {
        // Arrange
        val char = 'f'

        // Act & Assert
        assertThrows(IllegalArgumentException::class.java, {
            char.repeated(-1)
        }, "This method only accepts non-negative integers.")
    }

}
