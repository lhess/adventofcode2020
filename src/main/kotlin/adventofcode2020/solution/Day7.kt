package adventofcode2020.solution

import adventofcode2020.Solution
import adventofcode2020.resource.PuzzleInput

class Day7(puzzleInput: PuzzleInput<String>) : Solution<String, Int>(puzzleInput) {
    private val bags = puzzleInput.intoBags()

    override fun runPart1() =
        bags
            .findParents()
            .size.run { this - 1 }
            .apply { check(this == 197) }

    override fun runPart2() =
        bags
            .findCosts()
            .run { this - 1 }
            .apply { check(this == 85324) }

    private fun Set<Bag>.findCosts(bag: String = "shiny gold"): Int = let { bags ->
        bags.filter { it.parent == bag }.sumBy { it.cost * bags.findCosts(it.child) } + 1
    }

    private fun Set<Bag>.findParents(bag: String = "shiny gold"): Set<String> =
        filter { it.child == bag }.flatMap { findParents(it.parent) }.toSet() + bag

    private fun List<String>.intoBags(): Set<Bag> =
        filterNot { it.contains("no other") }
            .map {
                it.replace("""bags?|contain|,|\.""".toRegex(), "").trimEnd()
                    .replace("""\s{2,}""".toRegex(), " ")
            }
            .flatMap { line ->
                line.split("""\s(?=[0-9?])""".toRegex()).run { first() to drop(1) }.let { (parent, others) ->
                    others.map {
                        it.split("""\s""".toRegex(), 2).let { (cost, child) ->
                            Bag(parent, child, cost.toInt())
                        }
                    }
                }
            }.toSet()

    private data class Bag(val parent: String, val child: String, val cost: Int)
}
