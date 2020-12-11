package adventofcode2020.solution

import adventofcode2020.Solution
import adventofcode2020.resource.PuzzleInput

class Day8(puzzleInput: PuzzleInput<String>) : Solution<String, Int>(puzzleInput) {
    override fun runPart1() =
        CPU(puzzleInput).run {
            runCPU()
            check(state == CPU.State.HALTED)
            accumulator.apply { check(this == 1941) }
        }

    override fun runPart2() =
        puzzleInput.indices
            .mapNotNull {
                when (puzzleInput[it].take(3)) {
                    "jmp" -> it to puzzleInput[it].replace("jmp", "nop")
                    "nop" -> it to puzzleInput[it].replace("nop", "jmp")
                    else -> null
                }
            }
            .map { (pos, instruction) ->
                CPU(puzzleInput.toMutableList().apply {
                    this[pos] = instruction
                }.toList()).run { runCPU() to accumulator }
            }
            .firstOrNull { it.first == CPU.State.DONE }
            .run {
                check(this != null)
                check(first == CPU.State.DONE)
                check(second == 2096)
                second
            }

    class CPU(private val instructions: List<String>) : Iterable<CPU.State> {
        init {
            check(instructions.isNotEmpty())
        }

        enum class State {
            IDLE,
            HALTED,
            RUNNING,
            DONE
        }

        private abstract class Instruction {
            abstract fun runInstruction(cpu: CPU): Int?

            class Acc(private val value: Int) : Instruction() {
                override fun runInstruction(cpu: CPU): Int? {
                    cpu.accumulator += value
                    return null
                }
            }

            class Jmp(private val value: Int) : Instruction() {
                override fun runInstruction(cpu: CPU) = value
            }

            class Nop() : Instruction() {
                override fun runInstruction(cpu: CPU): Int? = null
            }

            companion object {
                fun of(command: String, argument: Int?) = when (command) {
                    "acc" -> argument?.run(::Acc) ?: error("acc needs one argument")
                    "jmp" -> argument?.run(::Jmp) ?: error("jmp needs one argument")
                    "nop" -> Nop()
                    else -> error("unknown command $command")
                }
            }
        }

        private var _state = State.IDLE
        private var _accumulator = 0
        private val executed = mutableSetOf<Int>()
        private var instructionPointer: Int = 0

        var accumulator: Int
            get() = _accumulator
            private set(value) {
                _accumulator = value
            }

        var state: State
            get() = _state
            private set(state) {
                _state = state
            }

        override fun iterator(): Iterator<State> = object : Iterator<State> {
            override fun hasNext() = instructionPointer == 0 || state == State.RUNNING
            override fun next(): State = run()
        }

        private fun run(): State {
            if (state in listOf(State.IDLE, State.RUNNING)) {
                state = if (executed.add(instructionPointer)) {
                    instructions[instructionPointer]
                        .run(::decode)
                        .runInstruction(this)
                        .let { (it ?: 1) + instructionPointer }
                        .takeIf { it <= instructions.size }
                        ?.let {
                            if (it == instructions.size) State.DONE
                            else {
                                instructionPointer = it
                                State.RUNNING
                            }
                        }
                        ?: error("instruction no. $instructionPointer leads to invalid position")
                } else State.HALTED
            }

            return state
        }

        private fun decode(input: String): Instruction =
            input.splitAtIndex(3).let { (instruction, argument) ->
                Instruction.of(instruction, argument?.toInt())
            }

        private fun String.splitAtIndex(index: Int) = take(index) to if (index + 1 in 0..length) substring(index + 1) else null
    }

    private fun CPU.runCPU(): CPU.State = first { it != CPU.State.RUNNING }
}

