/**
 * Challenge:
 *
 * --- Part One ---
 *
 *
 * You're starting to sweat as the ship makes its way toward Mercury. The Elves suggest that you get the air conditioner
 * working by upgrading your ship computer to support the Thermal Environment Supervision Terminal.
 *
 * The Thermal Environment Supervision Terminal (TEST) starts by running a diagnostic program (your puzzle input). The
 * TEST diagnostic program will run on your existing Intcode computer after a few modifications:
 *
 * First, you'll need to add two new instructions:
 *
 * Opcode 3 takes a single integer as input and saves it to the position given by its only parameter. For example, the
 * instruction 3,50 would take an input value and store it at address 50.
 * Opcode 4 outputs the value of its only parameter. For example, the instruction 4,50 would output the value at address
 * 50. Programs that use these instructions will come with documentation that explains what should be connected to the
 * input and output. The program 3,0,4,0,99 outputs whatever it gets as input, then halts.
 *
 * Second, you'll need to add support for parameter modes:
 *
 * Each parameter of an instruction is handled based on its parameter mode. Right now, your ship computer already
 * understands parameter mode 0, position mode, which causes the parameter to be interpreted as a position - if the
 * parameter is 50, its value is the value stored at address 50 in memory. Until now, all parameters have been in
 * position mode.
 *
 * Now, your ship computer will also need to handle parameters in mode 1, immediate mode. In immediate mode, a parameter
 * is interpreted as a value - if the parameter is 50, its value is simply 50.
 *
 * Parameter modes are stored in the same value as the instruction'directOrbitsOf opcode. The opcode is a two-digit number based only
 * on the ones and tens digit of the value, that is, the opcode is the rightmost two digits of the first value in an
 * instruction. Parameter modes are single digits, one per parameter, read right-to-left from the opcode: the first
 * parameter'directOrbitsOf mode is in the hundreds digit, the second parameter'directOrbitsOf mode is in the thousands digit, the third
 * parameter'directOrbitsOf mode is in the ten-thousands digit, and so on. Any missing modes are 0.
 *
 * For example, consider the program 1002,4,3,4,33.
 *
 * The first instruction, 1002,4,3,4, is a multiply instruction - the rightmost two digits of the first value, 02,
 * indicate opcode 2, multiplication. Then, going right to left, the parameter modes are 0 (hundreds digit),
 * 1 (thousands digit), and 0 (ten-thousands digit, not present and therefore zero):
 *
 * ABCDE
 * 1002
 *
 * DE - two-digit opcode,      02 == opcode 2
 * C - mode of 1st parameter,  0 == position mode
 * B - mode of 2nd parameter,  1 == immediate mode
 * A - mode of 3rd parameter,  0 == position mode, omitted due to being a leading zero
 *
 * This instruction multiplies its first two parameters. The first parameter, 4 in position mode, works like it
 * did before - its value is the value stored at address 4 (33). The second parameter, 3 in immediate mode, simply
 * has value 3. The result of this operation, 33 * 3 = 99, is written according to the third parameter, 4 in position
 * mode, which also works like it did before - 99 is written to address 4.
 *
 * Parameters that an instruction writes to will never be in immediate mode.
 *
 * Finally, some notes:
 *
 * It is important to remember that the instruction pointer should increase by the number of values in the instruction
 * after the instruction finishes. Because of the new instructions, this amount is no longer always 4.
 * Integers can be negative: 1101,100,-1,4,0 is a valid program (find 100 + -1, store the result in position 4).
 * The TEST diagnostic program will start by requesting from the user the ID of the system to test by running an input
 * instruction - provide it 1, the ID for the ship'directOrbitsOf air conditioner unit.
 *
 * It will then perform a series of diagnostic tests confirming that various parts of the Intcode computer, like
 * parameter modes, function correctly. For each test, it will run an output instruction indicating how far the result
 * of the test was from the expected value, where 0 means the test was successful. Non-zero outputs mean that a function
 * is not working correctly; check the instructions that were run before the output instruction to see which one failed.
 *
 * Finally, the program will output a diagnostic code and immediately halt. This final output isn't an error; an output
 * followed immediately by a halt means the program finished. If all outputs were zero except the diagnostic code,
 * the diagnostic program ran successfully.
 *
 * After providing 1 to the only input instruction and passing all the tests, what diagnostic code does the program
 * produce?
 *
 */

package aoc

import java.io.File
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.system.measureTimeMillis

/**
 * Program state containing [memory], [running], [pointer], [input], [output] information
 */
data class ProgramState(
        val memory: List<Int> = listOf(),
        val running: Boolean = true,
        val pointer: Int = 0,
        val input: Stack<Int> = Stack(),
        val output: List<Int> = listOf())

/**
 * Program containing [state] and [running] information
 */
data class Program(var state: ProgramState, var verbose: Boolean = false) {

    /**
     * runs the program until [running] is false
     */
    fun run() {
        while(state.running){
            val instruction = Instruction.fromCode(state.memory[state.pointer])
            val newState = execute(instruction)
            updateState(newState)
        }
    }

    /**
     * updates the [state]
     */
    private fun updateState(newState: ProgramState) {
        if(this.verbose){ println("$state\n${"v".padEnd(8, ' ').repeat(state.toString().length / 8)}\n$newState\n") }
        state = newState
    }

    /**
     * Reads values based on [offset] and [mode]
     */
    private fun readValue(offset: Int, mode: ParameterMode) = when(mode) {
        ParameterMode.Pointer -> state.memory[state.memory[state.pointer + offset]]
        ParameterMode.Immediate -> state.memory[state.pointer + offset]
    }

    /**
     * Reads values based on [instruction]
     */
    private fun readValues(instruction: Instruction) = (0 until instruction.operation.numParams )
            .map { readValue(it + 1, instruction.parameterModes[it]) }

    /**
     * Executes a given [instruction]
     */
    private fun execute(instruction: Instruction) : ProgramState {
        val newMemory = state.memory.toMutableList()

        when(instruction.operation) {
            Operation.Addition -> {
                val (x, y, z) = readValues(instruction)
                newMemory[z] = x + y
            }
            Operation.Multiplication -> {
                val (x, y, z) = readValues(instruction)
                newMemory[z] = x * y
            }
            Operation.Read -> {
                val x = state.input.pop()
                val (z) = readValues(instruction)
                newMemory[z] = x

            }
            Operation.Write -> {
                val (z) = readValues(instruction)
                val newOutput = state.output.toMutableList()
                newOutput.add(state.memory[z])
                return state.copy(pointer = state.pointer + instruction.operation.numParams + 1, output = newOutput)
            }
            Operation.Termination -> {
                println("Final Ouput:\n${state.output}")
                return state.copy(running = false)
            }
        }

        return state.copy(memory = newMemory, pointer = state.pointer + instruction.operation.numParams + 1)
    }
}

/**
 * Parameter modes
 */
enum class ParameterMode {
    Pointer, Immediate;

    companion object{

        /**
         * Factory to parse [code]
         */
        fun fromCode(code: Int) : ParameterMode = when(code) {
            0 -> Pointer
            1 -> Immediate
            else -> throw IllegalArgumentException()
        }
    }
}

/**
 * Operations containing [numParams]
 */
enum class Operation(val numParams: Int) {
    Addition(3),
    Multiplication(3),
    Read(1),
    Write(1),
    Termination(0);

    companion object{

        /**
         * Factory to parse [code]
         */
        fun fromCode(code: Int) : Operation = when(code){
            1 -> Addition
            2 -> Multiplication
            3 -> Read
            4 -> Write
            99 -> Termination
            else -> throw IllegalArgumentException("$code is not a valid opcode")
        }
    }
}

/**
 * Instruction containing [operation] and [parameterModes]
 */
data class Instruction(val operation: Operation, val parameterModes: List<ParameterMode>){

    companion object{

        /**
         * Factory to parse [code]
         */
        fun fromCode(code: Int) : Instruction{
            // extract op code
            val operation: Operation = Operation.fromCode(code % 100)

            // extract param modes & interpolate missing ones
            val paramCodeRemainder = (code / 100)
                    .toDigits()
                    .reversed()
            val parameterModes = (0 until operation.numParams - 1)
                    .map { ParameterMode.fromCode(paramCodeRemainder.getOrNull(it) ?: 0) }
                    .toMutableList()

            // last digits always marks position (as immediate)
            parameterModes.add(ParameterMode.Immediate)


            return Instruction(operation, parameterModes)
        }
    }

}


/**
 * Applies the complete logic to Task 1
 *
 * Note: The current version of this program produces the correct output for Task 1
 * but seems to be flawed as the rest of the output does not only contain 0s
 */
fun main() {
    val executionTime = measureTimeMillis {

        val memory = File("src/main/resources/day05/input.txt")
                .readLines()
                .first()
                .split(",")
                .map { it.toInt() }
        val input = Stack<Int>().also { it.add(1) }
        val program = Program(ProgramState(memory = memory, input = input))
        program.run()
    }
    println("\n[elapsed time: $executionTime ms]")
}

