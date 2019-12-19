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
 */

package aoc

import java.io.File
import kotlin.system.measureTimeMillis

class SpaceMap {

    private val _planets = mutableMapOf<Planet, Planet>()

    fun parseOrbit(input: String) {
        val (center, orbit) = input.split(")").map { Planet(it) }
        _planets.putIfAbsent(orbit, center)
    }

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

}

data class Planet(val id: String)

fun main() {
    val executionTime = measureTimeMillis {

        // Part One
        val spaceMap = SpaceMap()
        File("src/main/resources/day06/input.txt")
                .readLines()
                .forEach { spaceMap.parseOrbit(it) }
        println("Found ${spaceMap.totalOrbits()} orbits in total")

    }
    println("\n[elapsed time: $executionTime ms]")
}