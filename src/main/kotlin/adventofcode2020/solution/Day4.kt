package adventofcode2020.solution

import adventofcode2020.Solution
import adventofcode2020.resource.PuzzleInput
import adventofcode2020.resource.asBlocks
import java.io.File

class Day4(puzzleInput: PuzzleInput<String>) : Solution<String, Int>(puzzleInput) {
    private val blocks = puzzleInput.asBlocks().map { it.replace("\n", " ") }

    override fun runPart1() = blocks
        .count { it.isValidPassport(validate = false) }
        .apply { check(this == 206) }

    override fun runPart2() = blocks
        .count { it.isValidPassport(validate = true) }
        .apply { check(this == 123) }

    companion object {
        private val fieldConfig = mapOf<String, (String) -> Boolean>(
            "byr" to { it.toIntOrNull() in 1920..2002 },
            "iyr" to { it.toIntOrNull() in 2010..2020 },
            "eyr" to { it.toIntOrNull() in 2020..2030 },
            "hgt" to {
                when {
                    it.endsWith("in") -> it.filter { it.isDigit() }.toInt() in 59..76
                    it.endsWith("cm") -> it.filter { it.isDigit() }.toInt() in 150..193
                    else -> false
                }
            },
            "hcl" to { Regex("""#[a-f0-9]{6}""").matches(it) },
            "ecl" to { setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(it) },
            "pid" to { Regex("""[0-9]{9}""").matches(it) },
        )

        private fun String.isValidPassport(validate: Boolean): Boolean =
            split(" ")
                .map { it.split(":", limit = 2).let { it[0] to it[1] } }
                .toMap()
                .let { keyValuePairs ->
                    fieldConfig.count { (fieldName, validator) ->
                        !keyValuePairs.containsKey(fieldName) || (validate && !validator(keyValuePairs[fieldName]!!))
                    } == 0
                }

        private fun File.parseAsBlocks(): List<String> =
            bufferedReader()
                .use { it.readText() }
                .split("\n\n")
                .map { it.replace("\n", " ") }
    }
}
