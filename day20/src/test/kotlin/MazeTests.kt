import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MazeTests {

    @Test
    fun `small example maze non-recursive`() {
        // Arrange
        val maze = """
                 A           
                 A
          #######.#########
          #######.........#
          #######.#######.#
          #######.#######.#
          #######.#######.#
          #####  B    ###.#
        BC...##  C    ###.#
          ##.##       ###.#
          ##...DE  F  ###.#
          #####    G  ###.#
          #########.#####.#
        DE..#######...###.#
          #.#########.###.#
        FG..#########.....#
          ###########.#####
                     Z
                     Z
        """.trimIndent().split('\n')

        // Act
        val shortestPathLength = shortestPathLength(maze)

        // Assert
        assertEquals(23, shortestPathLength)
    }

    @Test
    fun `medium example maze non-recursive`() {
        // Arrange
        val maze = """
                           A
                           A
          #################.#############
          #.#...#...................#.#.#
          #.#.#.###.###.###.#########.#.#
          #.#.#.......#...#.....#.#.#...#
          #.#########.###.#####.#.#.###.#
          #.............#.#.....#.......#
          ###.###########.###.#####.#.#.#
          #.....#        A   C    #.#.#.#
          #######        S   P    #####.#
          #.#...#                 #......VT
          #.#.#.#                 #.#####
          #...#.#               YN....#.#
          #.###.#                 #####.#
        DI....#.#                 #.....#
          #####.#                 #.###.#
        ZZ......#               QG....#..AS
          ###.###                 #######
        JO..#.#.#                 #.....#
          #.#.#.#                 ###.#.#
          #...#..DI             BU....#..LF
          #####.#                 #.#####
        YN......#               VT..#....QG
          #.###.#                 #.###.#
          #.#...#                 #.....#
          ###.###    J L     J    #.#.###
          #.....#    O F     P    #.#...#
          #.###.#####.#.#####.#####.###.#
          #...#.#.#...#.....#.....#.#...#
          #.#####.###.###.#.#.#########.#
          #...#.#.....#...#.#.#.#.....#.#
          #.###.#####.###.###.#.#.#######
          #.#.........#...#.............#
          #########.###.###.#############
                   B   J   C
                   U   P   P
        """.trimIndent().split('\n')

        // Act
        val shortestPathLength = shortestPathLength(maze)

        // Assert
        assertEquals(58, shortestPathLength)
    }

}
