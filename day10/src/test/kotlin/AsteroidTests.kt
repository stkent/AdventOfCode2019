import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AsteroidTests {

    @Test
    fun `best base example 1`() {
        // Arrange
        val spaceMap = """
            .#..#
            .....
            #####
            ....#
            ...##
        """.trimIndent().split('\n')

        // Act
        val bestBaseData = bestBaseData(spaceMap)

        // Assert
        assertEquals(
            BaseData(location = GridPoint2d(3, -4), visibleAsteroidCount = 8),
            bestBaseData
        )
    }

    @Test
    fun `best base example 2`() {
        // Arrange
        val spaceMap = """
            ......#.#.
            #..#.#....
            ..#######.
            .#.#.###..
            .#..#.....
            ..#....#.#
            #..#....#.
            .##.#..###
            ##...#..#.
            .#....####
        """.trimIndent().split('\n')

        // Act
        val bestBaseData = bestBaseData(spaceMap)

        // Assert
        assertEquals(
            BaseData(location = GridPoint2d(5, -8), visibleAsteroidCount = 33),
            bestBaseData
        )
    }

    @Test
    fun `best base example 3`() {
        // Arrange
        val spaceMap = """
            #.#...#.#.
            .###....#.
            .#....#...
            ##.#.#.#.#
            ....#.#.#.
            .##..###.#
            ..#...##..
            ..##....##
            ......#...
            .####.###.
        """.trimIndent().split('\n')

        // Act
        val bestBaseData = bestBaseData(spaceMap)

        // Assert
        assertEquals(
            BaseData(location = GridPoint2d(1, -2), visibleAsteroidCount = 35),
            bestBaseData
        )
    }

    @Test
    fun `best base example 4`() {
        // Arrange
        val spaceMap = """
            .#..#..###
            ####.###.#
            ....###.#.
            ..###.##.#
            ##.##.#.#.
            ....###..#
            ..#.#..#.#
            #..#.#.###
            .##...##.#
            .....#.#..
        """.trimIndent().split('\n')

        // Act
        val bestBaseData = bestBaseData(spaceMap)

        // Assert
        assertEquals(
            BaseData(location = GridPoint2d(6, -3), visibleAsteroidCount = 41),
            bestBaseData
        )
    }

    @Test
    fun `best base example 5`() {
        // Arrange
        val spaceMap = """
            .#..##.###...#######
            ##.############..##.
            .#.######.########.#
            .###.#######.####.#.
            #####.##.#.##.###.##
            ..#####..#.#########
            ####################
            #.####....###.#.#.##
            ##.#################
            #####.##.###..####..
            ..######..##.#######
            ####.##.####...##..#
            .#####..#.######.###
            ##...#.##########...
            #.##########.#######
            .####.#.###.###.#.##
            ....##.##.###..#####
            .#.#.###########.###
            #.#.#.#####.####.###
            ###.##.####.##.#..##
        """.trimIndent().split('\n')

        // Act
        val bestBaseData = bestBaseData(spaceMap)

        // Assert
        assertEquals(
            BaseData(location = GridPoint2d(11, -13), visibleAsteroidCount = 210),
            bestBaseData
        )
    }

    @Test
    fun `destruction order example 5`() {
        // Arrange
        val spaceMap = """
            .#..##.###...#######
            ##.############..##.
            .#.######.########.#
            .###.#######.####.#.
            #####.##.#.##.###.##
            ..#####..#.#########
            ####################
            #.####....###.#.#.##
            ##.#################
            #####.##.###..####..
            ..######..##.#######
            ####.##.####...##..#
            .#####..#.######.###
            ##...#.##########...
            #.##########.#######
            .####.#.###.###.#.##
            ....##.##.###..#####
            .#.#.###########.###
            #.#.#.#####.####.###
            ###.##.####.##.#..##
        """.trimIndent().split('\n')

        // Act
        val destructionOrder = destructionOrder(spaceMap)

        // Assert
        assertEquals(GridPoint2d(11, -12), destructionOrder[0])
        assertEquals(GridPoint2d(12, -1), destructionOrder[1])
        assertEquals(GridPoint2d(12, -2), destructionOrder[2])
        assertEquals(GridPoint2d(12, -8), destructionOrder[9])
        assertEquals(GridPoint2d(16, 0), destructionOrder[19])
        assertEquals(GridPoint2d(16, -9), destructionOrder[49])
        assertEquals(GridPoint2d(10, -16), destructionOrder[99])
        assertEquals(GridPoint2d(9, -6), destructionOrder[198])
        assertEquals(GridPoint2d(8, -2), destructionOrder[199])
        assertEquals(GridPoint2d(10, -9), destructionOrder[200])
        assertEquals(GridPoint2d(11, -1), destructionOrder[298])
        assertEquals(299, destructionOrder.size)
    }

}
