package aoc

import org.junit.Test
import kotlin.test.assertEquals

class Day06Test {

    @Test
    fun `should add 2 planets to map`(){
        // given
        val input = "A)B"
        val spaceMap= SpaceMap
        val expected = 2

        // when
        spaceMap.parseOrbit(input)
        val actual = spaceMap.planets

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should only add distinct planets to map`(){
        // given
        val input = listOf("A)B", "A)C")
        val spaceMap= SpaceMap
        val expected = 3

        // when
        input.forEach { spaceMap.parseOrbit(it) }
        val actual = spaceMap.planets

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should add orbit to planet`(){
        // given
        val input = "A)B"
        val spaceMap= SpaceMap
        val expected = listOf(Planet("B"))

        // when
        spaceMap.parseOrbit(input)
        val actual = spaceMap.orbitOf("A")

        // then
        assertEquals(expected, actual)
    }
}