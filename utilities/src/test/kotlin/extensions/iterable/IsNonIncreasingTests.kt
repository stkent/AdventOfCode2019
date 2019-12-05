package extensions.iterable

import extensions.isNonIncreasing
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class IsNonIncreasingTests {

    @Test
    fun `calling on empty iterable is disallowed`() {
        assertThrows(IllegalArgumentException::class.java, {
            emptyList<Int>().isNonIncreasing()
        }, "This method cannot be called on an empty Iterable.")
    }

    @Test
    fun `single entry iterable is non increasing`() {
        assertTrue(listOf(1).isNonIncreasing())
    }

    @Test
    fun `strictly decreasing iterable is non increasing`() {
        assertTrue(listOf(5, 4, 3, 2, 1).isNonIncreasing())
    }

    @Test
    fun `non increasing iterable identified correctly`() {
        assertTrue(listOf(5, 4, 3, 3, 2, 1).isNonIncreasing())
    }

    @Test
    fun `iterable containing at least one increase is identified as not non increasing`() {
        assertFalse(listOf(5, 4, 3, 4, 2, 1).isNonIncreasing())
    }

}
