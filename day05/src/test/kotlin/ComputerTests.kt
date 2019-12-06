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
    fun `{day 2} simple program`() {
        // Arrange
        val program = listOf(1, 9, 10, 3, 2, 3, 11, 0, 99, 30, 40, 50)

        // Act
        val output = computer.execute(program)

        // Assert
        assertEquals(listOf(3500, 9, 10, 70, 2, 3, 11, 0, 99, 30, 40, 50), output)
    }

    @Test
    fun `{day 2} single add`() {
        // Arrange
        val program = listOf(1, 0, 0, 0, 99)

        // Act
        val output = computer.execute(program)

        // Assert
        assertEquals(listOf(2, 0, 0, 0, 99), output)
    }

    @Test
    fun `{day 2} single multiply`() {
        // Arrange
        val program = listOf(2, 3, 0, 3, 99)

        // Act
        val output = computer.execute(program)

        // Assert
        assertEquals(listOf(2, 3, 0, 6, 99), output)
    }

    @Test
    fun `{day 2} single multiply 2`() {
        // Arrange
        val program = listOf(2, 4, 4, 5, 99, 0)

        // Act
        val output = computer.execute(program)

        // Assert
        assertEquals(listOf(2, 4, 4, 5, 99, 9801), output)
    }

    @Test
    fun `{day 2} simple program 2`() {
        // Arrange
        val program = listOf(1, 1, 1, 4, 99, 5, 6, 0, 99)

        // Act
        val output = computer.execute(program)

        // Assert
        assertEquals(listOf(30, 1, 1, 4, 2, 5, 6, 0, 99), output)
    }

    @Test
    fun `{day 5} echoing input`() {
        // Arrange
        val program = listOf(3, 0, 4, 0, 99)
        val input = 1827
        val source = { input }

        val outputs = mutableListOf<Int>()
        val sink = { output: Int -> outputs += output }

        // Act
        computer.execute(program, source, sink)

        // Assert
        assertEquals(1, outputs.size)
        assertEquals(input, outputs.first())
    }

    @Test
    fun `{day 5} multiplying parameters using differing modes`() {
        // Arrange
        val program = listOf(1002, 4, 3, 4, 33)

        // Act
        val output = computer.execute(program)

        // Assert
        assertEquals(99, output[4])
    }

    @Test
    fun `{day 5} negative immediate parameter values are allowed`() {
        // Arrange
        val program = listOf(1101, 100, -1, 4, 0)

        // Act
        val output = computer.execute(program)

        // Assert
        assertEquals(99, output[4])
    }

    @Test
    fun `{day 5} check if input is equal to 8 (position mode, input = 8)`() {
        // Arrange
        val program = listOf(3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8)
        val source = { 8 }

        val outputs = mutableListOf<Int>()
        val sink = { output: Int -> outputs += output }

        // Act
        computer.execute(program, source, sink)

        // Assert
        assertEquals(1, outputs.size)
        assertEquals(1, outputs.first())
    }

    @Test
    fun `{day 5} check if input is equal to 8 (position mode, input != 8)`() {
        // Arrange
        val program = listOf(3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8)
        val source = { 7 }

        val outputs = mutableListOf<Int>()
        val sink = { output: Int -> outputs += output }

        // Act
        computer.execute(program, source, sink)

        // Assert
        assertEquals(1, outputs.size)
        assertEquals(0, outputs.first())
    }

    @Test
    fun `{day 5} check if input is less than 8 (position mode, input = 8)`() {
        // Arrange
        val program = listOf(3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8)
        val source = { 8 }

        val outputs = mutableListOf<Int>()
        val sink = { output: Int -> outputs += output }

        // Act
        computer.execute(program, source, sink)

        // Assert
        assertEquals(1, outputs.size)
        assertEquals(0, outputs.first())
    }

    @Test
    fun `{day 5} check if input is less than 8 (position mode, input less than 8)`() {
        // Arrange
        val program = listOf(3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8)
        val source = { 7 }

        val outputs = mutableListOf<Int>()
        val sink = { output: Int -> outputs += output }

        // Act
        computer.execute(program, source, sink)

        // Assert
        assertEquals(1, outputs.size)
        assertEquals(1, outputs.first())
    }

    @Test
    fun `{day 5} check if input is equal to 8 (immediate mode, input = 8)`() {
        // Arrange
        val program = listOf(3, 3, 1108, -1, 8, 3, 4, 3, 99)
        val source = { 8 }

        val outputs = mutableListOf<Int>()
        val sink = { output: Int -> outputs += output }

        // Act
        computer.execute(program, source, sink)

        // Assert
        assertEquals(1, outputs.size)
        assertEquals(1, outputs.first())
    }

    @Test
    fun `{day 5} check if input is equal to 8 (immediate mode, input != 8)`() {
        // Arrange
        val program = listOf(3, 3, 1108, -1, 8, 3, 4, 3, 99)
        val source = { 7 }

        val outputs = mutableListOf<Int>()
        val sink = { output: Int -> outputs += output }

        // Act
        computer.execute(program, source, sink)

        // Assert
        assertEquals(1, outputs.size)
        assertEquals(0, outputs.first())
    }

    @Test
    fun `{day 5} check if input is less than 8 (immediate mode, input = 8)`() {
        // Arrange
        val program = listOf(3, 3, 1107, -1, 8, 3, 4, 3, 99)
        val source = { 8 }

        val outputs = mutableListOf<Int>()
        val sink = { output: Int -> outputs += output }

        // Act
        computer.execute(program, source, sink)

        // Assert
        assertEquals(1, outputs.size)
        assertEquals(0, outputs.first())
    }

    @Test
    fun `{day 5} check if input is less than 8 (immediate mode, input less than 8)`() {
        // Arrange
        val program = listOf(3, 3, 1107, -1, 8, 3, 4, 3, 99)
        val source = { 7 }

        val outputs = mutableListOf<Int>()
        val sink = { output: Int -> outputs += output }

        // Act
        computer.execute(program, source, sink)

        // Assert
        assertEquals(1, outputs.size)
        assertEquals(1, outputs.first())
    }

    @Test
    fun `{day 5} check if input is non-zero (jump, position mode, input != 0)`() {
        // Arrange
        val program = listOf(3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9)
        val source = { -6 }

        val outputs = mutableListOf<Int>()
        val sink = { output: Int -> outputs += output }

        // Act
        computer.execute(program, source, sink)

        // Assert
        assertEquals(1, outputs.size)
        assertEquals(1, outputs.first())
    }

    @Test
    fun `{day 5} check if input is non-zero (jump, position mode, input = 0)`() {
        // Arrange
        val program = listOf(3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9)
        val source = { 0 }

        val outputs = mutableListOf<Int>()
        val sink = { output: Int -> outputs += output }

        // Act
        computer.execute(program, source, sink)

        // Assert
        assertEquals(1, outputs.size)
        assertEquals(0, outputs.first())
    }

    @Test
    fun `{day 5} check if input is non-zero (jump, immediate mode, input != 0)`() {
        // Arrange
        val program = listOf(3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1)
        val source = { -6 }

        val outputs = mutableListOf<Int>()
        val sink = { output: Int -> outputs += output }

        // Act
        computer.execute(program, source, sink)

        // Assert
        assertEquals(1, outputs.size)
        assertEquals(1, outputs.first())
    }

    @Test
    fun `{day 5} check if input is non-zero (jump, immediate mode, input = 0)`() {
        // Arrange
        val program = listOf(3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1)
        val source = { 0 }

        val outputs = mutableListOf<Int>()
        val sink = { output: Int -> outputs += output }

        // Act
        computer.execute(program, source, sink)

        // Assert
        assertEquals(1, outputs.size)
        assertEquals(0, outputs.first())
    }

    @Test
    fun `{day 5} compare input to 8 (input = 8)`() {
        // Arrange
        val program = listOf(
            3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
            1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
            999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99
        )

        val source = { 8 }

        val outputs = mutableListOf<Int>()
        val sink = { output: Int -> outputs += output }

        // Act
        computer.execute(program, source, sink)

        // Assert
        assertEquals(1, outputs.size)
        assertEquals(1000, outputs.first())
    }

    @Test
    fun `{day 5} compare input to 8 (input less than 8)`() {
        // Arrange
        val program = listOf(
            3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
            1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
            999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99
        )

        val source = { 3 }

        val outputs = mutableListOf<Int>()
        val sink = { output: Int -> outputs += output }

        // Act
        computer.execute(program, source, sink)

        // Assert
        assertEquals(1, outputs.size)
        assertEquals(999, outputs.first())
    }

    @Test
    fun `{day 5} compare input to 8 (input greater than 8)`() {
        // Arrange
        val program = listOf(
            3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
            1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
            999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99
        )

        val source = { 13 }

        val outputs = mutableListOf<Int>()
        val sink = { output: Int -> outputs += output }

        // Act
        computer.execute(program, source, sink)

        // Assert
        assertEquals(1, outputs.size)
        assertEquals(1001, outputs.first())
    }

}
