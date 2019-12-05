/**
 * Challenge:
 *
 * --- Part One ---
 *
 *
 * The gravity assist was successful, and you're well on your way to the Venus refuelling station. During the rush back
 * on Earth, the fuel management system wasn't completely installed, so that's next on the priority list.
 *
 * Opening the front panel reveals a jumble of wires. Specifically, two wires are connected to a central port and extend
 * outward on a grid. You trace the path each wire takes as it leaves the central port, one wire per line of text
 * (your puzzle input).
 *
 * The wires twist and turn, but the two wires occasionally cross paths. To fix the circuit, you need to find the
 * intersection point closest to the central port. Because the wires are on a grid, use the Manhattan distance for this
 * measurement. While the wires do technically cross right at the central port where they both start, this point does
 * not count, nor does a wire count as crossing with itself.
 *
 * For example, if the first wire's path is R8,U5,L5,D3, then starting from the central port (o), it goes right 8, up 5,
 * left 5, and finally down 3:
 *
 * ...........
 * ...........
 * ...........
 * ....+----+.
 * ....|....|.
 * ....|....|.
 * ....|....|.
 * .........|.
 * .o-------+.
 * ...........
 * Then, if the second wire's path is U7,R6,D4,L4, it goes up 7, right 6, down 4, and left 4:
 *
 * ...........
 * .+-----+...
 * .|.....|...
 * .|..+--X-+.
 * .|..|..|.|.
 * .|.-X--+.|.
 * .|..|....|.
 * .|.......|.
 * .o-------+.
 * ...........
 * These wires cross at two locations (marked X), but the lower-left one is closer to the central port: its distance
 * is 3 + 3 = 6.
 *
 * Here are a few more examples:
 *
 * R75,D30,R83,U83,L12,D49,R71,U7,L72
 * U62,R66,U55,R34,D71,R55,D58,R83 = distance 159
 *
 * R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51
 * U98,R91,D20,R16,D67,R40,U7,R15,U6,R7 = distance 135
 *
 * What is the Manhattan distance from the central port to the closest intersection?
 *
 *
 * --- Part Two ---
 *
 *
 * It turns out that this circuit is very timing-sensitive; you actually need to minimize the signal delay.
 *
 * To do this, calculate the number of steps each wire takes to reach each intersection; choose the intersection where
 * the sum of both wires' steps is lowest. If a wire visits a position on the grid multiple times, use the steps value
 * from the first time it visits that position when calculating the total value of a specific intersection.
 *
 * The number of steps a wire takes is the total number of grid squares the wire has entered to get to that location,
 * including the intersection being considered. Again consider the example from above:
 *
 * ...........
 * .+-----+...
 * .|.....|...
 * .|..+--X-+.
 * .|..|..|.|.
 * .|.-X--+.|.
 * .|..|....|.
 * .|.......|.
 * .o-------+.
 * ...........
 * In the above example, the intersection closest to the central port is reached after 8+5+5+2 = 20 steps by the first
 * wire and 7+6+4+3 = 20 steps by the second wire for a total of 20+20 = 40 steps.
 *
 * However, the top-right intersection is better: the first wire takes only 8+5+2 = 15 and the second wire takes only
 * 7+6+2 = 15, a total of 15+15 = 30 steps.
 *
 * Here are the best steps for the extra examples from above:
 *
 * R75,D30,R83,U83,L12,D49,R71,U7,L72
 * U62,R66,U55,R34,D71,R55,D58,R83 = 610 steps
 *
 * R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51
 * U98,R91,D20,R16,D67,R40,U7,R15,U6,R7 = 410 steps
 *
 * What is the fewest combined steps the wires must take to reach an intersection?
 *
 */

package aoc

import java.io.File
import kotlin.math.abs
import kotlin.system.measureTimeMillis

/**
 *
 */
enum class Orientation { UP, DOWN, LEFT, RIGHT }

/**
 *
 */
data class Direction(val orientation: Orientation, val magnitude: Int) {
    companion object {
        fun fromInstructions(instructions: String) : List<Direction> {
            return instructions.split(",").mapNotNull { instruction ->
                when (instruction.first()) {
                    'U' -> Direction(Orientation.UP, instruction.drop(1).toInt())
                    'D' -> Direction(Orientation.DOWN, instruction.drop(1).toInt())
                    'L' -> Direction(Orientation.LEFT, instruction.drop(1).toInt())
                    'R' -> Direction(Orientation.RIGHT, instruction.drop(1).toInt())
                    else -> null
                }
            }
        }
    }
}

/**
 *
 */
data class Point(val x: Int, val y: Int)

/**
 *
 */
fun Point.distance(point: Point) = abs(this.x - point.x) + abs(this.y - point.y)

/**
 *
 */
fun Point.move(vararg directions: Direction): Path {
    var result = Path(listOf(this))
    var head = this.copy()

    directions.forEach {dir ->
        result = result.append(when(dir.orientation) {
            Orientation.UP -> Path((0..dir.magnitude).map { Point(head.x, head.y + it) }.toList())
            Orientation.DOWN -> Path((0..dir.magnitude).map { Point(head.x, head.y - it) }.toList())
            Orientation.LEFT -> Path((0..dir.magnitude).map { Point(head.x - it, head.y) }.toList())
            Orientation.RIGHT -> Path((0..dir.magnitude).map { Point(head.x + it, head.y) }.toList())
        })
        head = result.head.copy()
    }

    return result
}


/**
 *
 */
data class Path(val points: List<Point>)

/**
 *
 */
val Path.head: Point
        get() = this.points.last()

/**
 *
 */
fun Path.append(path: Path) = Path(this.points + path.points.drop(1))

/**
 *
 */
fun Path.findIntersections(path: Path) : Set<Point> = this.points.toSet().intersect(path.points.toSet())


/**
 *
 */
fun findDistance(instructions: List<String>): Int {
    // list to collect paths
    val paths = mutableListOf<Path>()

    // process each set of instructions
    instructions.forEach {
        paths.add(Point(0, 0).move(*Direction.fromInstructions(it).toTypedArray()))
    }


    // find intersections
    val intersections = mutableSetOf<Point>()
    for (i in 0 until paths.size){
        for (j in i+1 until paths.size)
            intersections.addAll(paths[i].findIntersections(paths[j]))
    }

    // find closest point (dismissing origin itself)
    return intersections
            .filter { it != Point(0, 0) }
            .minBy { it.distance(Point(0, 0)) }
            ?.distance(Point(0, 0)) ?: -1
}


/**
 *
 */
fun main() {
    val executionTime = measureTimeMillis {
        val result = findDistance(File("src/main/resources/day03/input.txt").readLines())
        println("Minimal distance from central port to intersection is $result")
    }
    println("\n[elapsed time: $executionTime ms]")
}