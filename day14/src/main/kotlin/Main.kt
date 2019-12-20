fun main() {
    val inputRawReactions = resourceFile("input.txt").readLines()

    println("Part 1 solution: ${orePerFuelUnit(inputRawReactions)}")
}

fun orePerFuelUnit(rawReactions: List<String>): Int {
    val reactions = rawReactions
        .map { line ->
            val (inputs, output) = Regex("^(.*) => (.*)$")
                .matchEntire(line)!!
                .destructured

            Reaction(
                inputs = inputs.split(", ").map(::parseComponent),
                output = parseComponent(output)
            )
        }

    val directProducts = reactions
        .filter { reaction ->
            reaction.inputs.size == 1 && reaction.inputs.first().chemical == Chemical("ORE")
        }
        .map { reaction -> reaction.output.chemical }
        .also { println(it) }



    return 0
}

private fun parseComponent(rawComponent: String): ReactionComponent {
    val (quantity, chemical) = Regex("^(\\d+) (\\w+)$").matchEntire(rawComponent)!!.destructured
    return ReactionComponent(Chemical(chemical), quantity.toInt())
}

private inline class Chemical(val name: String)

private data class ReactionComponent(val chemical: Chemical, val quantity: Int)

private data class Reaction(val inputs: List<ReactionComponent>, val output: ReactionComponent)
