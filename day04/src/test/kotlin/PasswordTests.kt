import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class PasswordTests {

    @Test
    fun `111111 is valid in part 1`() {
        assertTrue(Password(111111).matchesPart1Criteria())
    }

    @Test
    fun `223450 is invalid in part 1 (decreasing digits)`() {
        assertFalse(Password(223450).matchesPart1Criteria())
    }

    @Test
    fun `123789 is invalid in part 1 (no run of digits of length exactly 2)`() {
        assertFalse(Password(123789).matchesPart1Criteria())
    }

    @Test
    fun `112233 is valid in part 2 (multiple runs of digits all of length exactly 2)`() {
        assertTrue(Password(112233).matchesPart2Criteria())
    }

    @Test
    fun `123444 is invalid in part 2 (no run of digits of length exactly 2)`() {
        assertFalse(Password(123444).matchesPart2Criteria())
    }

    @Test
    fun `111122 is valid in part 2 (one run of digits of length exactly 2, one longer run)`() {
        assertTrue(Password(111122).matchesPart2Criteria())
    }

}
