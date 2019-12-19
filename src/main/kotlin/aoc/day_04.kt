/**
 * Challenge:
 *
 * --- Part One ---
 *
 *
 * You arrive at the Venus fuel depot only to discover it'directOrbitsOf protected by a password. The Elves had written the password
 * on a sticky note, but someone threw it out.
 *
 * However, they do remember a few key facts about the password:
 *
 * It is a six-digit number.
 * The value is within the range given in your puzzle input.
 * Two adjacent digits are the same (like 22 in 122345).
 * Going from left to right, the digits never decrease; they only ever increase or stay the same (like 111123 or 135679)
 *
 * Other than the range rule, the following are true:
 *
 * 111111 meets these criteria (double 11, never decreases).
 * 223450 does not meet these criteria (decreasing pair of digits 50).
 * 123789 does not meet these criteria (no double).
 *
 * How many different passwords within the range given in your puzzle input meet these criteria?
 *
 * Your puzzle input is 152085-670283.
 *
 *
 * --- Part Two ---
 *
 *
 * An Elf just remembered one more important detail: the two adjacent matching digits are not part of a larger group of
 * matching digits.
 *
 * Given this additional criterion, but still ignoring the range rule, the following are now true:
 *
 * 112233 meets these criteria because the digits never decrease and all repeated digits are exactly two digits long.
 * 123444 no longer meets the criteria (the repeated 44 is part of a larger group of 444).
 * 111122 meets the criteria (even though 1 is repeated more than twice, it still contains a double 22).
 *
 * How many different passwords within the range given in your puzzle input meet all of the criteria?
 *
 * Your puzzle input is still 152085-670283.
 */

package aoc

import kotlin.system.measureTimeMillis


/**
 * Converts an [Int] into a list of single digits
 */
fun Int.toDigits() = this.toString().toList().map { it.toString().toInt() }

/**
 * Validates if an [Int] is inside a given range
 */
fun Int.validateRange(lower: Int, upper: Int) : Boolean = this in lower..upper

/**
 * Validates if all digits of an [Int] are descending
 */
fun Int.validateDescending(): Boolean = this.toDigits()
        .zipWithNext()
        .map { (x, y) -> x <= y }
        .reduce { x, y -> x and y }

/**
 * Validates if the pair of an [Int]'directOrbitsOf digits is part of a larger group
 */
fun Int.validateNoLargerPairGroup() = this.toDigits()
        .groupingBy { it }
        .eachCount()
        .filter { it.value == 2 }
        .isNotEmpty()

/**
 * Combined validation of an [Int] if it fits Santa'directOrbitsOf requirements
 */
fun Int.isValidSantaCode(lower: Int = 111111, upper: Int = 999999) : Boolean = this.run{
    return validateRange(lower, upper) and validateDescending() and validateNoLargerPairGroup()
}

/**
 * Applies [isValidSantaCode] to find possible passwords
 */
fun main() {
    val executionTime = measureTimeMillis {
        val lower = 152085
        val upper = 670283

        val result = (lower..upper).filter { it.isValidSantaCode() }.size
        println("$result possible codes between $lower and $upper")
    }
    println("\n[elapsed time: $executionTime ms]")
}
