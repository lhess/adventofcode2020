package adventofcode2020.solution

import adventofcode2020.Solution
import adventofcode2020.resource.PuzzleInput

class Day3(puzzleInput: PuzzleInput<String>) : Solution<String, Any>(puzzleInput) {
    override fun runPart1() =
        puzzleInput
            .traverse(stepsRight = 3, stepsDown = 1)
            .apply { check(this == 280) }

    override fun runPart2() =
        puzzleInput.run {
            listOf(
                traverse(stepsRight = 1, stepsDown = 1),
                traverse(stepsRight = 3, stepsDown = 1),
                traverse(stepsRight = 5, stepsDown = 1),
                traverse(stepsRight = 7, stepsDown = 1),
                traverse(stepsRight = 1, stepsDown = 2)
            )
        }
            .fold(1L, { total, next -> total * next })
            .apply { check(this == 4355551200L) }

    companion object {
        private fun List<String>.traverse(stepsRight: Int, stepsDown: Int): Int {
            var cnt = 0
            var xPos = 0

            for (yPos in stepsDown until count() step stepsDown) {
                xPos += stepsRight
                if (get(yPos).isTree(xPos)) ++cnt
            }

            return cnt
        }

        private fun String.isTree(xPos: Int): Boolean = this[xPos % length] == '#'
    }
}
