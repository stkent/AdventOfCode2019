import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OrbitTests {

    @Test
    fun `total count (no bodies)`() {
        // Arrange
        val orbits = emptySet<Orbit>()

        // Act
        val totalOrbits = totalOrbits(orbits)

        // Assert
        assertEquals(0, totalOrbits)
    }

    @Test
    fun `total count (two bodies)`() {
        // Arrange
        val orbits = setOf(
            "COM" isOrbitedBy "A"
        )

        // Act
        val totalOrbits = totalOrbits(orbits)

        // Assert
        assertEquals(1, totalOrbits)
    }

    /*
     *     A
     *    /
     * COM
     *    \
     *     B
     */
    @Test
    fun `total count (three bodies, fork)`() {
        // Arrange
        val orbits = setOf(
            "COM" isOrbitedBy "A",
            "COM" isOrbitedBy "B"
        )

        // Act
        val totalOrbits = totalOrbits(orbits)

        // Assert
        assertEquals(2, totalOrbits)
    }

    /*
     * COM - A - B
     */
    @Test
    fun `total count (three bodies, linear)`() {
        // Arrange
        val orbits = setOf(
            "COM" isOrbitedBy "A",
            "A" isOrbitedBy "B"
        )

        // Act
        val totalOrbits = totalOrbits(orbits)

        // Assert
        assertEquals(3, totalOrbits)
    }

    /*
     *         G - H       J - K - L
     *        /           /
     * COM - B - C - D - E - F
     *                \
     *                 I
     */
    @Test
    fun `total count (full example)`() {
        // Arrange
        val orbits = setOf(
            "COM" isOrbitedBy "B",
            "B" isOrbitedBy "C",
            "C" isOrbitedBy "D",
            "D" isOrbitedBy "E",
            "E" isOrbitedBy "F",
            "B" isOrbitedBy "G",
            "G" isOrbitedBy "H",
            "D" isOrbitedBy "I",
            "E" isOrbitedBy "J",
            "J" isOrbitedBy "K",
            "K" isOrbitedBy "L"
        )

        // Act
        val totalOrbits = totalOrbits(orbits)

        // Assert
        assertEquals(42, totalOrbits)
    }

    /*
     *     YOU
     *    /
     * COM
     *    \
     *     SAN
     */
    @Test
    fun `orbital transfers (no transfers needed)`() {
        // Arrange
        val orbits = setOf(
            "COM" isOrbitedBy "YOU",
            "COM" isOrbitedBy "SAN"
        )

        // Act
        val totalOrbits = orbitalTransfers(orbits)

        // Assert
        assertEquals(0, totalOrbits)
    }

    /*
     *                           YOU
     *                          /
     *         G - H       J - K - L                        G - H       J - K - L
     *        /           /                                /           /
     * COM - B - C - D - E - F            ==>       COM - B - C - D - E - F
     *                \                                            \
     *                 I - SAN                                      I - SAN
     *                                                               \
     *                                                                YOU
     */
    @Test
    fun `orbital transfers (full example)`() {
        // Arrange
        val orbits = setOf(
            "COM" isOrbitedBy "B",
            "B" isOrbitedBy "C",
            "C" isOrbitedBy "D",
            "D" isOrbitedBy "E",
            "E" isOrbitedBy "F",
            "B" isOrbitedBy "G",
            "G" isOrbitedBy "H",
            "D" isOrbitedBy "I",
            "E" isOrbitedBy "J",
            "J" isOrbitedBy "K",
            "K" isOrbitedBy "L",
            "K" isOrbitedBy "YOU",
            "I" isOrbitedBy "SAN"
        )

        // Act
        val totalOrbits = orbitalTransfers(orbits)

        // Assert
        assertEquals(4, totalOrbits)
    }

}
