import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FuelTests {

    @Test
    fun `{ore per fuel unit} simplest reaction with no waste`() {
        // Arrange
        val rawReactions = listOf("1 ORE => 1 FUEL")

        // Act
        val orePerFuelUnit = orePerFuelUnit(rawReactions)

        // Assert
        assertEquals(1, orePerFuelUnit)
    }

    @Test
    fun `{ore per fuel unit} simplest reaction with waste`() {
        // Arrange
        val rawReactions = listOf("3 ORE => 1 FUEL")

        // Act
        val orePerFuelUnit = orePerFuelUnit(rawReactions)

        // Assert
        assertEquals(3, orePerFuelUnit)
    }

    @Test
    fun `{ore per fuel unit} simple reaction with no waste`() {
        // Arrange
        val rawReactions = """
            4 A => 1 FUEL
            1 ORE => 2 A
        """.trimIndent().split('\n')

        // Act
        val orePerFuelUnit = orePerFuelUnit(rawReactions)

        // Assert
        assertEquals(2, orePerFuelUnit)
    }

    @Test
    fun `{ore per fuel unit} simple reaction with waste`() {
        // Arrange
        val rawReactions = """
            4 A => 1 FUEL
            1 ORE => 3 A
        """.trimIndent().split('\n')

        // Act
        val orePerFuelUnit = orePerFuelUnit(rawReactions)

        // Assert
        assertEquals(2, orePerFuelUnit)
    }

    @Test
    fun `{ore per fuel unit} simple reaction with second order effects`() {
        // Arrange
        val rawReactions = """
            2 A, 3 B => 1 FUEL
            5 A => 2 B
            2 ORE => 7 A
        """.trimIndent().split('\n')

        // Isolating the components of fuel directly:
        //
        // - 2 A requires 2 ORE
        // - 3 B requires 10 A requires 4 ORE
        //
        // Summed requirement = 6 ORE.
        //
        // But we can do better:
        //
        // - 4 ORE produces 14 A
        //         produces 4 A, 4 B
        //         produces 1 FUEL, 2 A, 1 B

        // Act
        val orePerFuelUnit = orePerFuelUnit(rawReactions)

        // Assert
        // 4 ORE
        // => 14 A
        // => (2 A + 10 A) + (2 A)
        // => (2 A + 4 B) + (2 A)
        // => (2 A + 3 B) + (2 A + B)
        // => 1 FUEL + ...
        assertEquals(3, orePerFuelUnit)
    }

    @Test
    fun `{ore per fuel unit} example 1`() {
        // Arrange
        val rawReactions = """
            10 ORE => 10 A
            1 ORE => 1 B
            7 A, 1 B => 1 C
            7 A, 1 C => 1 D
            7 A, 1 D => 1 E
            7 A, 1 E => 1 FUEL
        """.trimIndent().split('\n')

        // Act
        val orePerFuelUnit = orePerFuelUnit(rawReactions)

        // Assert
        assertEquals(31, orePerFuelUnit)
    }

    @Test
    fun `{ore per fuel unit} example 2`() {
        // Arrange
        val rawReactions = """
            9 ORE => 2 A
            8 ORE => 3 B
            7 ORE => 5 C
            3 A, 4 B => 1 AB
            5 B, 7 C => 1 BC
            4 C, 1 A => 1 CA
            2 AB, 3 BC, 4 CA => 1 FUEL
        """.trimIndent().split('\n')

        // Act
        val orePerFuelUnit = orePerFuelUnit(rawReactions)

        // Assert
        assertEquals(165, orePerFuelUnit)
    }

    @Test
    fun `{ore per fuel unit} example 3`() {
        // Arrange
        val rawReactions = """
            157 ORE => 5 NZVS
            165 ORE => 6 DCFZ
            44 XJWVT, 5 KHKGT, 1 QDVJ, 29 NZVS, 9 GPVTF, 48 HKGWZ => 1 FUEL
            12 HKGWZ, 1 GPVTF, 8 PSHF => 9 QDVJ
            179 ORE => 7 PSHF
            177 ORE => 5 HKGWZ
            7 DCFZ, 7 PSHF => 2 XJWVT
            165 ORE => 2 GPVTF
            3 DCFZ, 7 NZVS, 5 HKGWZ, 10 PSHF => 8 KHKGT
        """.trimIndent().split('\n')

        // Act
        val orePerFuelUnit = orePerFuelUnit(rawReactions)

        // Assert
        assertEquals(13312, orePerFuelUnit)
    }

    @Test
    fun `{ore per fuel unit} example 4`() {
        // Arrange
        val rawReactions = """
            2 VPVL, 7 FWMGM, 2 CXFTF, 11 MNCFX => 1 STKFG
            17 NVRVD, 3 JNWZP => 8 VPVL
            53 STKFG, 6 MNCFX, 46 VJHF, 81 HVMC, 68 CXFTF, 25 GNMV => 1 FUEL
            22 VJHF, 37 MNCFX => 5 FWMGM
            139 ORE => 4 NVRVD
            144 ORE => 7 JNWZP
            5 MNCFX, 7 RFSQX, 2 FWMGM, 2 VPVL, 19 CXFTF => 3 HVMC
            5 VJHF, 7 MNCFX, 9 VPVL, 37 CXFTF => 6 GNMV
            145 ORE => 6 MNCFX
            1 NVRVD => 8 CXFTF
            1 VJHF, 6 MNCFX => 4 RFSQX
            176 ORE => 6 VJHF
        """.trimIndent().split('\n')

        // Act
        val orePerFuelUnit = orePerFuelUnit(rawReactions)

        // Assert
        assertEquals(180697, orePerFuelUnit)
    }

    @Test
    fun `{ore per fuel unit} example 5`() {
        // Arrange
        val rawReactions = """
            171 ORE => 8 CNZTR
            7 ZLQW, 3 BMBT, 9 XCVML, 26 XMNCP, 1 WPTQ, 2 MZWV, 1 RJRHP => 4 PLWSL
            114 ORE => 4 BHXH
            14 VRPVC => 6 BMBT
            6 BHXH, 18 KTJDG, 12 WPTQ, 7 PLWSL, 31 FHTLT, 37 ZDVW => 1 FUEL
            6 WPTQ, 2 BMBT, 8 ZLQW, 18 KTJDG, 1 XMNCP, 6 MZWV, 1 RJRHP => 6 FHTLT
            15 XDBXC, 2 LTCX, 1 VRPVC => 6 ZLQW
            13 WPTQ, 10 LTCX, 3 RJRHP, 14 XMNCP, 2 MZWV, 1 ZLQW => 1 ZDVW
            5 BMBT => 4 WPTQ
            189 ORE => 9 KTJDG
            1 MZWV, 17 XDBXC, 3 XCVML => 2 XMNCP
            12 VRPVC, 27 CNZTR => 2 XDBXC
            15 KTJDG, 12 BHXH => 5 XCVML
            3 BHXH, 2 VRPVC => 7 MZWV
            121 ORE => 7 VRPVC
            7 XCVML => 6 RJRHP
            5 BHXH, 4 VRPVC => 5 LTCX
        """.trimIndent().split('\n')

        // Act
        val orePerFuelUnit = orePerFuelUnit(rawReactions)

        // Assert
        assertEquals(2210736, orePerFuelUnit)
    }

}
