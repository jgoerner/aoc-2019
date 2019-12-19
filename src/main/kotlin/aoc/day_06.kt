/**
 * Challenge:
 *
 * --- Part One ---
 *
 *
 * You've landed at the Universal Orbit Map facility on Mercury. Because navigation in space often involves transferring
 * between orbits, the orbit maps here are useful for finding efficient routes between, for example, you and Santa.
 * ou download a map of the local orbits (your puzzle input).
 *
 * Before you use your map data to plot a course, you need to make sure it wasn't corrupted during the download. To
 * verify maps, the Universal Orbit Map facility uses orbit count checksums - the total number of direct orbits
 * and indirect orbits.
 *
 * Whenever A orbits B and B orbits C, then A indirectly orbits C. This chain can be any number of objects long: if A
 * orbits B, B orbits C, and C orbits D, then A indirectly orbits D.
 *
 * For example, suppose you have the following map:
 *
 * COM)B
 * B)C
 * C)D
 * D)E
 * E)F
 * B)G
 * G)H
 * D)I
 * E)J
 * J)K
 * K)L
 *
 * Visually, the above map of orbits looks like this:
 *
 *         G - H       J - K - L
 *         /           /
 * COM - B - C - D - E - F
 *                 \
 *                 I
 * In this visual representation, when two objects are connected by a line, the one on the right directly orbits the
 * one on the left.
 *
 * Here, we can count the total number of orbits as follows:
 *
 * D directly orbits C and indirectly orbits B and COM, a total of 3 orbits.
 * L directly orbits K and indirectly orbits J, E, D, C, B, and COM, a total of 7 orbits.
 * COM orbits nothing.
 * The total number of direct and indirect orbits in this example is 42.
 *
 * What is the total number of direct and indirect orbits in your map data?
 *
 *
 * --- Part Two ---
 *
 *
 * Now, you just need to figure out how many orbital transfers you (YOU) need to take to get to Santa (SAN).
 *
 * You start at the object YOU are orbiting; your destination is the object SAN is orbiting. An orbital transfer lets
 * you move from any object to an object orbiting or orbited by that object.
 *
 * For example, suppose you have the following map:
 *
 * COM)B
 * B)C
 * C)D
 * D)E
 * E)F
 * B)G
 * G)H
 * D)I
 * E)J
 * J)K
 * K)L
 * K)YOU
 * I)SAN
 * Visually, the above map of orbits looks like this:
 *
 *                            YOU
 *                           /
 *          G - H       J - K - L
 *        /           /
 * COM - B - C - D - E - F
 *                \
 *                I - SAN
 *
 * In this example, YOU are in orbit around K, and SAN is in orbit around I. To move from K to I, a minimum of 4 orbital
 * transfers are required:
 *
 * K to J
 * J to E
 * E to D
 * D to I
 *
 * Afterward, the map of orbits looks like this:
 *
 *         G - H       J - K - L
 *        /           /
 * COM - B - C - D - E - F
 *               \
 *               I - SAN
 *                \
 *                YOU
 *
 * What is the minimum number of orbital transfers required to move from the object YOU are orbiting to the object SAN
 * is orbiting? (Between the objects they are orbiting - not between YOU and SAN.)
 */

package aoc

import java.io.File
import kotlin.system.measureTimeMillis

/**
 * Map holding information about relationships between [Planet]
 */
class SpaceMap {

    private val _planets = mutableMapOf<Planet, Planet>()

    /**
     * Parses [input] and links planets
     */
    fun parseOrbit(input: String) {
        val (center, orbit) = input.split(")").map { Planet(it) }
        _planets.putIfAbsent(orbit, center)
    }

    /**
     * Returns the number of direct and indirect orbits
     */
    fun totalOrbits() : Int {
        var orbitCounter = 0

        // for each k, check if v is present and increment counter
        for (k in _planets.keys) {
            var current : Planet? = k.copy()
            while (_planets[current] != null) {
                orbitCounter++
                current = _planets[current]
            }
        }
        return orbitCounter
    }

    /**
     * Calculates the path from a given [input] to the "root" [Planet]
     */
    fun pathToRoot(input: Planet) : List<Planet>{
        val path = mutableListOf(input)
        var current: Planet = input.copy()
        while (_planets[current] != null) {
            path.add(_planets[current]!!)
            current = _planets[current]!!
        }
        return path
    }

    /**
     * Calculates the steps between [from] and [to]
     */
    fun stepsBetween(from: Planet, to: Planet): Int {
        val pathFrom = pathToRoot(from).toMutableList()
        val pathTo = pathToRoot(to).toMutableList()
        pathFrom.reversed()
                .zip(pathTo.reversed())
                .forEach { (a, b) ->  if (a == b) { pathFrom.remove(a); pathTo.remove(b) }
                }
        return pathFrom.size + pathTo.size - 2
    }
}

/**
 * Planet identified by an [id]
 */
data class Planet(val id: String)

/**
 * Applies the [SpaceMap.totalOrbits] and [SpaceMap.stepsBetween] functions to the given input
 */
fun main() {
    val executionTime = measureTimeMillis {

        // General Setup
        val spaceMap = SpaceMap()
        File("src/main/resources/day06/input.txt")
                .readLines()
                .forEach { spaceMap.parseOrbit(it) }

        // Part One
        println("Found ${spaceMap.totalOrbits()} orbits in total")

        // Part Two
        println("You are ${spaceMap.stepsBetween(Planet("YOU"), Planet("SAN"))} steps apart from Santa")

    }
    println("\n[elapsed time: $executionTime ms]")
}