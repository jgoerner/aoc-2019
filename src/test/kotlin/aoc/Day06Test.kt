package aoc

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

    @Test
    fun `should find path to root`(){
        // given
        val input = listOf(
                "A)B",
                "B)C",
                "B)D",
                "C)E")
        val expected = listOf("E", "C", "B", "A").map { Planet(it) }

        // when
        val spaceMap = SpaceMap()
        input.forEach { spaceMap.parseOrbit(it) }
        val actual = spaceMap.pathToRoot(Planet("E"))

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should find simple path length between planets`(){
        // given
        val input = listOf(
                "A)B",
                "B)C",
                "B)D",
                "C)E")
        val expected = 1

        // when
        val spaceMap = SpaceMap()
        input.forEach { spaceMap.parseOrbit(it) }
        val actual = spaceMap.stepsBetween(Planet("E"), Planet("D"))

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should find advanced path length between planets`(){
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
                "K)L",
                "K)YOU",
                "I)SAN")
        val expected = 4

        // when
        val spaceMap = SpaceMap()
        input.forEach { spaceMap.parseOrbit(it) }
        val actual = spaceMap.stepsBetween(Planet("YOU"), Planet("SAN"))

        // then
        assertEquals(expected, actual)
    }
}