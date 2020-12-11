package adventofcode2020.solution

import adventofcode2020.Solution
import adventofcode2020.resource.PuzzleInput
import adventofcode2020.resource.asNumbers

class Day9(puzzleInput: PuzzleInput<String>) : Solution<Int, Int>(puzzleInput.asNumbers()) {
    override fun runPart1() =
        puzzleInput.windowed(26, 1)
            .mapNotNull { window ->
                window.last().takeIf { res ->
                    window.dropLast(1).run {
                        firstOrNull { contains(res - it) }
                    } == null
                }
            }.firstOrNull().run {
                check(this == 138879426)
                this
            }

    override fun runPart2() =
        puzzleInput.indices.toList().dropLast(1).mapNotNull seek@{ pos ->
            val foundNumbers = mutableListOf(puzzleInput[pos])
            puzzleInput.drop(pos + 1).forEach { number ->
                val sum = foundNumbers.apply { add(number) }.sum()
                if (sum > resultPart1!!) return@seek null
                else if (sum == resultPart1) return@seek foundNumbers.sorted().run { listOf(first(), last()) }.sum()
            }
        }.firstOrNull().run {
            check(this == 23761694)
            this as Int
        }
}
