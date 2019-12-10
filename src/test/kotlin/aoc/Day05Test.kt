package aoc

import java.lang.IllegalArgumentException
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class Day05Test {

    @Test
    fun `should parse addition`(){
        // given
        val input = 1
        val expected = Operation.Addition

        // when
        val actual = Operation.fromCode(input)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should parse multiplication`(){
        // given
        val input = 2
        val expected = Operation.Multiplication

        // when
        val actual = Operation.fromCode(input)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should parse read`(){
        // given
        val input = 3
        val expected = Operation.Read

        // when
        val actual = Operation.fromCode(input)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should parse write`(){
        // given
        val input = 4
        val expected = Operation.Write

        // when
        val actual = Operation.fromCode(input)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should parse termination`(){
        // given
        val input = 99
        val expected = Operation.Termination

        // when
        val actual = Operation.fromCode(input)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should throw error for unsupported OpCode`(){
        // given
        val input = 42

        // then
        assertFailsWith(IllegalArgumentException::class) {

            // when
            Operation.fromCode(input)
        }
    }

    @Test
    fun `should parse pointer mode`(){
        // given
        val input = 0
        val expected = ParameterMode.Pointer

        // when
        val actual = ParameterMode.fromCode(input)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should parse immediate mode`(){
        // given
        val input = 1
        val expected = ParameterMode.Immediate

        // when
        val actual = ParameterMode.fromCode(input)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should throw error for unsupported paramCode`(){
        // given
        val input = 9

        // then
        assertFailsWith(IllegalArgumentException::class) {

            // when
            ParameterMode.fromCode(input)
        }
    }

    @Test
    fun `should parse instruction code`(){
        // given
        val input = 1101
        val expected = Instruction(
                Operation.Addition,
                listOf(ParameterMode.Immediate, ParameterMode.Immediate, ParameterMode.Immediate)
        )

        // when
        val actual = Instruction.fromCode(input)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should interpolate missing parameter modes`(){
        // given
        val input = 2
        val expected = Instruction(
                Operation.Multiplication,
                listOf(ParameterMode.Pointer, ParameterMode.Pointer, ParameterMode.Immediate)
        )
        // when
        val actual = Instruction.fromCode(input)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should evaluate 1002,4,3,4,33 to 1002,4,3,4,99`(){
        // given
        val program = Program(ProgramState(memory=listOf(1002,4,3,4,33)))
        val expected = listOf(1002,4,3,4,99)

        // when
        program.run()
        val actual = program.state.memory

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should evaluate 1101,100,-1,4,0 to 1101,100,-1,4,99`(){
        // given
        val program = Program(ProgramState(memory=listOf(1101,100,-1,4,0)))
        val expected = listOf(1101,100,-1,4,99)

        // when
        program.run()
        val actual = program.state.memory

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should evaluate 3, 0, 99 with input 42 to 42, 0, 99`(){
        // given
        val input = Stack<Int>().also { it.add(42) }
        val program = Program(ProgramState(memory=listOf(3, 0, 99), input=input))
        val expected = listOf(42, 0, 99)

        // when
        program.run()
        val actual = program.state.memory

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should write output`(){
        // given
        val program = Program(ProgramState(memory=listOf(4, 0, 99)))
        val expected = listOf(4)

        // when
        program.run()
        val actual = program.state.output

        // then
        assertEquals(expected, actual)
    }
}