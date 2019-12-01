package aoc

import kotlin.test.Test
import kotlin.test.assertEquals

class Day01Test {

    @Test fun `should simple convert mass of 12 to 2 fuel`() {
        // given
        val mass = 12
        val expected = 2

        // when
        val actual = massToFuel(mass)

        // then
        assertEquals(expected, actual)
    }

    @Test fun `should simple convert mass of 14 to 2 fuel`() {
        // given
        val mass = 14
        val expected = 2

        // when
        val actual = massToFuel(mass)

        // then
        assertEquals(expected, actual)
    }

    @Test fun `should simple convert mass of 1969 to 654 fuel`() {
        // given
        val mass = 1969
        val expected = 654

        // when
        val actual = massToFuel(mass)

        // then
        assertEquals(expected, actual)
    }

    @Test fun `should simple convert mass of 100756 to 33583 fuel`() {
        // given
        val mass = 100756
        val expected = 33583

        // when
        val actual = massToFuel(mass)

        // then
        assertEquals(expected, actual)
    }

    @Test fun `should complex convert mass of 14 to 2 fuel`() {
        // given
        val mass = 14
        val expected = 2

        // when
        val actual = massToFuel(mass, simple=false)

        // then
        assertEquals(expected, actual)
    }

    @Test fun `should complex convert mass of 1969 to 966 fuel`() {
        // given
        val mass = 1969
        val expected = 966

        // when
        val actual = massToFuel(mass, simple=false)

        // then
        assertEquals(expected, actual)
    }

    @Test fun `should complex convert mass of 100756 to 50346 fuel`() {
        // given
        val mass = 100756
        val expected = 50346

        // when
        val actual = massToFuel(mass, simple=false)

        // then
        assertEquals(expected, actual)
    }
}
