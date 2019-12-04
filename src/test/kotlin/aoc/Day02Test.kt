package aoc

import org.junit.Test
import kotlin.test.assertEquals

class Day02Test {

    @Test
    fun `should convert 1,0,0,0,99 to 2,0,0,0,99`() {
        // given
        val intcode = "1,0,0,0,99".split(",").map{ it.toInt() }
        val expected = "2,0,0,0,99".split(",").map{ it.toInt()}

        // when
        val actual = processIntcode(intcode)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should convert 2,3,0,3,99 to 2,3,0,6,99`() {
        // given
        val intcode = "2,3,0,3,99".split(",").map{ it.toInt() }
        val expected = "2,3,0,6,99".split(",").map{ it.toInt() }

        // when
        val actual = processIntcode(intcode)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should convert 2,4,4,5,99,0 to 2,4,4,5,99,9801`() {
        // given
        val intcode = "2,4,4,5,99,0".split(",").map{ it.toInt() }
        val expected = "2,4,4,5,99,9801".split(",").map{ it.toInt() }

        // when
        val actual = processIntcode(intcode)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should convert 1,1,1,4,99,5,6,0,99 to 30,1,1,4,2,5,6,0,99`() {
        // given
        val intcode = "1,1,1,4,99,5,6,0,99".split(",").map{ it.toInt() }
        val expected = "30,1,1,4,2,5,6,0,99".split(",").map{ it.toInt() }

        // when
        val actual = processIntcode(intcode)

        // then
        assertEquals(expected, actual)
    }
}