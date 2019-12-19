package aoc

import com.sun.imageio.plugins.jpeg.JPEG.COM
import org.junit.Test
import kotlin.test.assertEquals

class Day06Test {

    @Test
    fun `should match single total orbits`(){
        // given
        val input = "A)B"
        val spaceMap = SpaceMap()
        val expected = 1

        // when
        spaceMap.parseOrbit(input)
        val actual = spaceMap.totalOrbits()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should find three total orbits`(){
        // given
        val input = listOf("A)B", "B)C")
        val spaceMap = SpaceMap()
        val expected = 3

        // when
        input.forEach { spaceMap.parseOrbit(it) }
        val actual = spaceMap.totalOrbits()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should find 42 total orbits`(){
        // given
        val input = listOf(
                "COM)B",
                "B)C",
                "C)D",
                "D)E",
                "E)F",
                "B)G",
                "G)H",
                "D)I",
                "E)J",
                "J)K",
                "K)L"
                )
        val spaceMap = SpaceMap()
        val expected = 42

        // when
        input.forEach { spaceMap.parseOrbit(it) }
        val actual = spaceMap.totalOrbits()

        // then
        assertEquals(expected, actual)
    }

}