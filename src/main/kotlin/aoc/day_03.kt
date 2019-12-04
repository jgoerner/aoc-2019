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
 */

package aoc

import kotlin.math.pow
import kotlin.math.sqrt

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
fun Point.distance(point: Point) = sqrt((this.x.toDouble() + point.x).pow(2.0) + (this.y.toDouble() + point.y).pow(2.0))

/**
 *
 */
fun Point.move(direction: Direction): Path = when(direction.orientation) {
        Orientation.UP -> Path((0..direction.magnitude).map { Point(this.x, this.y + it) }.toList())
        Orientation.DOWN -> Path((0..direction.magnitude).map { Point(this.x, this.y - it) }.toList())
        Orientation.LEFT -> Path((0..direction.magnitude).map { Point(this.x - it, this.y) }.toList())
        Orientation.RIGHT -> Path((0..direction.magnitude).map { Point(this.x + it, this.y) }.toList())
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
fun findDistance(instructions: List<String>): Double {
    // list to collect paths
    val paths = mutableListOf<Path>()

    // process each set of instructions
    instructions.map {
        var path = Path(listOf(Point(0, 0)))
        Direction.fromInstructions(it).forEach { d ->
            path = path.append(path.head.move(d))
        }
        paths.add(path)
    }

    // find intersections
    val intersections = mutableSetOf<Point>()
    for (i in 0 until paths.size){
        for (j in i+1 until paths.size)
            intersections.addAll(paths[i].findIntersections(paths[j]))
    }

    // find closest point (dismissing origin itself)
    return intersections
            .filterNot { it == Point(0, 0) }
            .minBy { it.distance(Point(0, 0)) }
            ?.distance(Point(0, 0)) ?: -1.0
}


/**
 *
 */
fun main() {
    println(findDistance(listOf("U3,R5", "R3,U5")))
}