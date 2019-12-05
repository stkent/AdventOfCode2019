package extensions.iterable

import extensions.isNonDecreasing
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class IsNonDecreasingTests {

    @Test
    fun `calling on empty iterable is disallowed`() {
        assertThrows(IllegalArgumentException::class.java, {
            emptyList<Int>().isNonDecreasing()
        }, "This method cannot be called on an empty Iterable.")
    }

    @Test
    fun `single entry iterable is non decreasing`() {
        assertTrue(listOf(1).isNonDecreasing())
    }

    @Test
    fun `strictly increasing iterable is non decreasing`() {
        assertTrue(listOf(1, 2, 3, 4, 5).isNonDecreasing())
    }

    @Test
    fun `non decreasing iterable identified correctly`() {
        assertTrue(listOf(1, 2, 3, 3, 4, 5).isNonDecreasing())
    }

    @Test
    fun `iterable containing at least one decrease is identified as not non decreasing`() {
        assertFalse(listOf(1, 2, 4, 3, 4, 5).isNonDecreasing())
    }

}
