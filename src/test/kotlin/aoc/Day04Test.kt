package aoc

import kotlin.test.Test
import kotlin.test.assertEquals

class Day04Test {

    @Test
    fun `should mark less than 6 digit number as invalid`(){
        // given
        val input = 12345
        val expected = false

        // when
        val actual = input.isValidSantaCode()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should mark more than 6 digit number as invalid`(){
        // given
        val input = 1234567
        val expected = false

        // when
        val actual = input.isValidSantaCode()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should mark numbers below lower bound of range as invalid`() {
        // given
        val input = 111111
        val expected = false

        // when
        val actual = input.isValidSantaCode(222222, 333333)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should mark numbers above upper bound of range as invalid`() {
        // given
        val input = 777777
        val expected = false

        // when
        val actual = input.isValidSantaCode(222222, 333333)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should mark 6 distinct numbers as invalid`(){
        // given
        val input = 123456
        val expected = false

        // when
        val actual = input.isValidSantaCode()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should mark non adjacent pair as invalid`(){
        // given
        val input = 123256
        val expected = false

        // when
        val actual = input.isValidSantaCode()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should mark decreasing sequence as invalid`() {
        // given
        val input = 664321
        val expected = false

        // when
        val actual = input.isValidSantaCode()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should mark 111111 as invalid`(){
        // given
        val input = 111111
        val expected = false

        // when
        val actual = input.isValidSantaCode()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should mark 223450 as invalid`(){
        // given
        val input = 223450
        val expected = false

        // when
        val actual = input.isValidSantaCode()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should mark 123789 as invalid`(){
        // given
        val input = 123789
        val expected = false

        // when
        val actual = input.isValidSantaCode()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should mark 112233 as valid`(){
        // given
        val input = 112233
        val expected = true

        // when
        val actual = input.isValidSantaCode()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should mark 123444 as invalid`(){
        // given
        val input = 123444
        val expected = false

        // when
        val actual = input.isValidSantaCode()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should mark 111122 as valid`(){
        // given
        val input = 112233
        val expected = true

        // when
        val actual = input.isValidSantaCode()

        // then
        assertEquals(expected, actual)
    }
}