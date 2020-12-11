@file:JvmName("AdventOfCode2020Application")

package adventofcode2020

import adventofcode2020.resource.PuzzleInput
import kotlin.reflect.full.primaryConstructor
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    try {
        dispatch(args)
    } catch (e: Error) {
        println("Error: ${e.message}")
        exitProcess(1)
    }
}

private fun dispatch(args: Array<String>) {
    val day = args.firstOrNull()
        ?.run { trim().toIntOrNull() }
        ?: error("No, empty or invalid day given")

    val solutionClassName = "adventofcode2020.solution.Day$day"
    val application = try {
        Class.forName(solutionClassName).kotlin.primaryConstructor?.call(PuzzleInput.of(day))
    } catch (e: ClassNotFoundException) {
        error("Soution for day $day does not exist")
    }

    if (application is Solution<*, *>) {
        println("Running solutions for day $day:")
        println("=".repeat(20))
        listOf(application.resultPart1, application.resultPart2).forEachIndexed { num, res ->
            println()
            println("Part ${num + 1}:")
            println(res?.run { toString().indent() } ?: "\t[FAILURE]")
        }
    } else {
        error("Class $solutionClassName is not a instance of Solution")
    }
}

private fun String.indent(): String = split("\n").joinToString("\n") { "\t$it" }
