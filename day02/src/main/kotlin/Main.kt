fun main() {
    val inputProgram = Computer.parseProgramFile("input.txt")

    val computer = Computer()

    // Part 1
    inputProgram
        .toMutableList()
        .apply {
            set(1, 12)
            set(2, 2)
        }
        .let { computer.execute(it) }
        .also { output -> println("Part 1 solution: ${output[0]}") }

    // Part 2
    val targetOutput = 19690720L

    for (noun in 0..99) {
        for (verb in 0..99) {
            val output = inputProgram
                .toMutableList()
                .apply {
                    set(1, noun.toLong())
                    set(2, verb.toLong())
                }
                .let { computer.execute(it) }

            if (output[0] == targetOutput) println("Part 2 solution: ${100 * noun + verb}.")
        }
    }
}
