import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ComputerTests {

    private lateinit var computer: Computer

    @BeforeEach
    fun setUp() {
        computer = Computer()
    }

    @Test
    fun `test program 1`() {
        // Arrange
        val program = listOf(1, 9, 10, 3, 2, 3, 11, 0, 99, 30, 40, 50)

        // Act
        val output = computer.execute(program)

        // Assert
        assertEquals(listOf(3500, 9, 10, 70, 2, 3, 11, 0, 99, 30, 40, 50), output)
    }

    @Test
    fun `test program 2`() {
        // Arrange
        val program = listOf(1, 0, 0, 0, 99)

        // Act
        val output = computer.execute(program)

        // Assert
        assertEquals(listOf(2, 0, 0, 0, 99), output)
    }

    @Test
    fun `test program 3`() {
        // Arrange
        val program = listOf(2, 3, 0, 3, 99)

        // Act
        val output = computer.execute(program)

        // Assert
        assertEquals(listOf(2, 3, 0, 6, 99), output)
    }

    @Test
    fun `test program 4`() {
        // Arrange
        val program = listOf(2, 4, 4, 5, 99, 0)

        // Act
        val output = computer.execute(program)

        // Assert
        assertEquals(listOf(2, 4, 4, 5, 99, 9801), output)
    }

    @Test
    fun `test program 5`() {
        // Arrange
        val program = listOf(1, 1, 1, 4, 99, 5, 6, 0, 99)

        // Act
        val output = computer.execute(program)

        // Assert
        assertEquals(listOf(30, 1, 1, 4, 2, 5, 6, 0, 99), output)
    }

}