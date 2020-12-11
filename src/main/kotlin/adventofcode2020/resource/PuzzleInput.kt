package adventofcode2020.resource

import java.io.BufferedReader
import java.io.File

class PuzzleInput<T> internal constructor(
    internal val file: File,
    private val initializer: File.() -> List<T>
) : List<T> by initializer(file) {
    companion object {
        private fun of(filename: String): PuzzleInput<String> {
            return ClassLoader.getSystemResource(filename)
                ?.run {
                    toURI().run(::File).run { PuzzleInput(this) { readLines().filter { it.isNotBlank() } } }
                } ?: error("Could not load file $filename")
        }

        fun of(day: Int): PuzzleInput<String> = of("puzzle_input_day$day.txt")
    }
}

fun PuzzleInput<String>.asNumbers(): PuzzleInput<Int> =
    PuzzleInput(file) { mapNotNull { it.toIntOrNull() } }

fun PuzzleInput<String>.asBlocks(delimiter: String = "\n\n"): PuzzleInput<String> =
    PuzzleInput(file) { file.bufferedReader().use(BufferedReader::readText).split(delimiter) }
