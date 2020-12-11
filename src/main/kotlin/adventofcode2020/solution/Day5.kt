package adventofcode2020.solution

import adventofcode2020.Solution
import adventofcode2020.resource.PuzzleInput

class Day5(puzzleInput: PuzzleInput<String>) : Solution<String, Int>(puzzleInput) {
    data class Seat(val seatId: Int) {
        companion object {
            fun parseFrom(input: String) = Seat(
                input
                    .map { if (it in setOf('B', 'R')) '1' else '0' }
                    .joinToString("")
                    .toInt(2)
            )
        }
    }

    private val seats = puzzleInput.map { Seat.parseFrom(it).seatId }

    override fun runPart1() =
        seats.maxOrNull()
            .apply { check(this == 818) }

    override fun runPart2() =
        seats.sorted()
            .zipWithNext()
            .filter { it.second - it.first == 2 }
            .apply { check(size == 1) }
            .first()
            .let { it.first + 1 }
            .apply { check(this == 559) }
}
