package aoc

import org.junit.Test
import kotlin.test.assertEquals

class Day03Test {

    @Test
    fun `should move single point up 5`() {
        // given
        val start = Point(0, 0)
        val direction = Direction(Orientation.UP, 5)
        val expected = Point(0, 5)

        // when
        val actual = (start.move(direction)).head

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should move single point down 5`() {
        // given
        val start = Point(5, 5)
        val direction = Direction(Orientation.DOWN, 5)
        val expected = Point(5, 0)

        // when
        val actual = (start.move(direction)).head

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should move single point left 5`() {
        // given
        val start = Point(5, 5)
        val direction = Direction(Orientation.LEFT, 5)
        val expected = Point(0, 5)

        // when
        val actual = (start.move(direction)).head

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should move single point right 5`() {
        // given
        val start = Point(0, 0)
        val direction = Direction(Orientation.RIGHT, 5)
        val expected = Point(5, 0)

        // when
        val actual = (start.move(direction)).head

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should move single point up 8 and right 5`() {
        // given
        var start = Point(0, 0)
        val directions = listOf(Direction(Orientation.UP, 8), Direction(Orientation.RIGHT, 5))
        val expected = Point(5, 8)

        // when
        var actual = start.copy()
        directions.forEach{
            actual = actual.move(it).head
        }

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should move single point up 7 and right 5 down 2 and left 1`() {
        // given
        val start = Point(0, 0)
        val directions = listOf(
                Direction(Orientation.UP, 7),
                Direction(Orientation.RIGHT, 5),
                Direction(Orientation.DOWN, 2),
                Direction(Orientation.LEFT, 1)
        )
        val expected = Point(4, 5)

        // when
        var actual = start.copy()
        directions.forEach{
            actual = actual.move(it).head
        }

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should create path from point and direction`() {
        // given
        val start = Point(0, 0)
        val direction = Direction(Orientation.UP, 3)
        val expected = Path((0..3).map { Point(0, it) })

        // when
        val actual = start.move(direction)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should create path from point and multiple directions`() {
        // given
        val start = Point(0, 0)
        val directions = arrayOf(
                Direction(Orientation.RIGHT, 2),
                Direction(Orientation.UP, 3)
        )
        val expected = Path(listOf(
                Point(0, 0),
                Point(1, 0),
                Point(2, 0),
                Point(2, 1),
                Point(2, 2),
                Point(2, 3)
        ))

        // given
        val actual = start.move(*directions)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should append two paths`() {
        // given
        val firstPath = Point(0, 0).move(Direction(Orientation.UP, 3))
        val secondPath = firstPath.head.move(Direction(Orientation.RIGHT, 2))
        val expected = Path(listOf(
                Point(0, 0),
                Point(0, 1),
                Point(0, 2),
                Point(0, 3),
                Point(1, 3),
                Point(2, 3)
        ))

        // when
        val actual = firstPath.append(secondPath)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should append three paths`() {
        // given
        val firstPath = Point(0, 0).move(Direction(Orientation.UP, 3))
        val secondPath = firstPath.head.move(Direction(Orientation.RIGHT, 2))
        val thirdPath = secondPath.head.move(Direction(Orientation.DOWN, 2))
        val expected = Path(listOf(
                Point(0, 0),
                Point(0, 1),
                Point(0, 2),
                Point(0, 3),
                Point(1, 3),
                Point(2, 3),
                Point(2, 2),
                Point(2, 1)
        ))

        // when
        val actual = firstPath.append(secondPath).append(thirdPath)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should find zero intersections`() {
        // given
        val firstPath = Point(0, 0).move(Direction(Orientation.UP, 5))
        val secondPath = Point(1, 0).move(Direction(Orientation.UP, 5))
        val expected = setOf<Point>()

        // when
        val actual = firstPath.findIntersections(secondPath)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should find single intersection`() {
        // given
        val firstPath = Point(5, 0).move(Direction(Orientation.UP, 10))
        val secondPath = Point(0, 5).move(Direction(Orientation.RIGHT, 10))
        val expected = setOf(Point(5, 5))

        // when
        val actual = firstPath.findIntersections(secondPath)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should find multiple intersections`() {
        // given
        val firstPath = Point(5, 0).move(Direction(Orientation.UP, 10))
        val secondPath = Point(0, 5).move(Direction(Orientation.RIGHT, 10))
        val thirdPath = Point(0, 4).move(Direction(Orientation.RIGHT, 10))
        val expected = setOf(Point(5, 5), Point(5, 4))

        // when
        val actual = firstPath.findIntersections(secondPath.append(thirdPath))

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should calculate distance between points`() {
        // given
        val p1 = Point(0, 0)
        val p2 = Point(3, 4)
        val expected= 7

        // when
        val actual = p1.distance(p2)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should convert well formed instructions`() {
        // given
        val input = "U10,R2,L1,D2,D4"
        val expected= listOf(
                Direction(Orientation.UP, 10),
                Direction(Orientation.RIGHT, 2),
                Direction(Orientation.LEFT, 1),
                Direction(Orientation.DOWN, 2),
                Direction(Orientation.DOWN, 4)
        )

        // when
        val actual = Direction.fromInstructions(input)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should convert malformed instructions`() {
        // given
        val input = "U10,X20,D2"
        val expected= listOf(
                Direction(Orientation.UP, 10),
                Direction(Orientation.DOWN, 2)
        )

        // when
        val actual = Direction.fromInstructions(input)

        // then
        assertEquals(expected, actual)
    }



    @Test
    fun `should find intersections between R8,U5,L5,D3 and U7,R6,D4,L4`(){
        // given
        val start = Point(0, 0)
        val (p1, p2) = listOf("R8,U5,L5,D3", "U7,R6,D4,L4")
                .map{ Direction.fromInstructions(it) }
                .map{ start.move(*it.toTypedArray()) }
        val expected = setOf(Point(3,3), Point(6, 5))

        // when
        val actual = p1.findIntersections(p2).filter{ it != start }.toSet()

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should convert R75,D30,R83,U83,L12,D49,R71,U7,L72 & U62,R66,U55,R34,D71,R55,D58,R83 to 159`() {
        // given
        val instructions = listOf("R75,D30,R83,U83,L12,D49,R71,U7,L72", "U62,R66,U55,R34,D71,R55,D58,R83")
        val expected = 159

        // when
        val actual = findDistance(instructions)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should convert R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51 & U98,R91,D20,R16,D67,R40,U7,R15,U6,R7 to 135`() {
        // given
        val instructions = listOf("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51", "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7")
        val expected = 135

        // when
        val actual = findDistance(instructions)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should calculate distance of single Path`(){
        // given
        val (start, intersection) = listOf(Point(0, 0), Point(5, 0))
        val path = start.move(Direction(Orientation.RIGHT, 10))
        val expected = 5

        // when
        val actual = stepsAlongPath(intersection, path)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should calculate distance of multiple Paths`(){
        // given
        val (start, intersection) = listOf(Point(0, 0), Point(2, 2))
        val paths = listOf("R2,U2","U2,R2")
                .map{ start.move(*Direction.fromInstructions(it).toTypedArray()) }
                .toTypedArray()
        val expected = 8

        // when
        val actual = stepsAlongPath(intersection, *paths)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should cache previous distance for single path`(){
        // given
        // given
        val (start, intersection) = listOf(Point(0, 0), Point(2, 2))
        val path = start.move(*Direction.fromInstructions("R2,U4,R2,D2,L4").toTypedArray())
        val expected = 4

        // when
        val actual = stepsAlongPath(intersection, path)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should cache previous distance for multiple paths`(){
        // given
        val (start, intersection) = listOf(Point(0, 0), Point(2, 2))
        val paths = listOf("R2,U4,R2,D2,L4", "U2,R4,U2,L2,D4")
                .map{ start.move(*Direction.fromInstructions(it).toTypedArray()) }
                .toTypedArray()
        val expected = 8

        // when
        val actual = stepsAlongPath(intersection, *paths)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should convert R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51 & U98,R91,D20,R16,D67,R40,U7,R15,U6,R7 to 410 steps`(){
        // given
        val instructions = listOf("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51", "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7")
        val expected = 410

        // when
        val actual = findSteps(instructions)

        // then
        assertEquals(expected, actual)
    }
}

