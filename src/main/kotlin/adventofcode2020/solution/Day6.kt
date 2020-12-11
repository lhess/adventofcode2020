package adventofcode2020.solution

import adventofcode2020.Solution
import adventofcode2020.resource.PuzzleInput
import adventofcode2020.resource.asBlocks

class Day6(puzzleInput: PuzzleInput<String>) : Solution<String, Int>(puzzleInput) {
    private val blocks = puzzleInput.asBlocks().map { it.lines().filter(String::isNotBlank) }

    override fun runPart1() =
        blocks
            .map { it.joinToString("").toSet().size }
            .sum()
            .apply { check(this == 7120) }

    override fun runPart2() =
        blocks
            .map { block ->
                block.map { line ->
                    line.toCharArray().toSet()
                }.reduce { acc, set -> acc.intersect(set) }.size
            }
            .sum()
            .apply { check(this == 3570) }
}
