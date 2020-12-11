package adventofcode2020.solution

import adventofcode2020.Solution
import adventofcode2020.resource.PuzzleInput

class Day2(puzzleInput: PuzzleInput<String>) : Solution<String, Int>(puzzleInput) {
    private val passwords = puzzleInput.map(Password::of)

    override fun runPart1() =
        passwords.count { (range, char, password) ->
            password.count { it == char } in range
        }
            .apply { check(this == 586) }

    override fun runPart2() =
        passwords.count { (range, char, password) ->
            (password[range.first - 1] == char) xor (password[range.last - 1] == char)
        }
            .apply { check(this == 352) }

    private data class Password(val range: IntRange, val char: Char, val value: String) {
        companion object {
            private val pattern = """^(\d+)-(\d+) (\w): (\w+)$""".toRegex()

            fun of(input: String): Password {
                val (min, max, char, value) = pattern.find(input)!!.destructured
                return Password(min.toInt()..max.toInt(), char.first(), value)
            }
        }
    }
}



