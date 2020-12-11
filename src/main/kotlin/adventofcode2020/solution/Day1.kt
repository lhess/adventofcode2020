package adventofcode2020.solution

import adventofcode2020.Solution
import adventofcode2020.resource.PuzzleInput
import adventofcode2020.resource.asNumbers

class Day1(puzzleInput: PuzzleInput<String>) : Solution<String, Any>(puzzleInput) {
    private val numbers = puzzleInput.asNumbers()

    override fun runPart1() = numbers
        .first { numbers.contains(MAGIC_NUMBER - it) }
        .let { it * (MAGIC_NUMBER - it) }

    override fun runPart2() = sequence {
        for (x in numbers.indices) {
            for (y in x + 1 until numbers.size) {
                numbers.indexOf(MAGIC_NUMBER - numbers[x] - numbers[y])
                    .takeIf { it > -1 }
                    ?.let { listOf(numbers[x], numbers[y], numbers[it]).sorted() }
                    ?.apply { yield(this) }
            }
        }
    }.toSet().takeIf { it.isNotEmpty() }?.run {
        first()
            .fold(1L, { total, next -> total * next })
    }

    companion object {
        private const val MAGIC_NUMBER = 2020
    }
}
