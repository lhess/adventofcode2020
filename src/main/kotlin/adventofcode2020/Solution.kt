package adventofcode2020

import adventofcode2020.resource.PuzzleInput

abstract class Solution<T, R>(var puzzleInput: PuzzleInput<T>) {
    protected abstract fun runPart1(): R?
    protected abstract fun runPart2(): R?

    val resultPart1 by lazy { runPart1() }
    val resultPart2 by lazy { runPart2() }
}
