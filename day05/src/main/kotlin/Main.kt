fun main() {
    val inputProgram = resourceFile("input.txt")
        .readLines()
        .first()
        .split(',')
        .map(String::toLong)

    val computer = Computer()

    // Part 1
    val outputs = mutableListOf<Long>()
    computer.execute(
        program = inputProgram,
        source = { Computer.Input.Value(1) },
        sink = { output -> if (output > 0) outputs += output }
    )

    if (outputs.size == 1) {
        println("Part 1 solution: ${outputs.first()}")
    } else {
        println("Part 1 checks failed with codes ${outputs.dropLast(1).joinToString(",")}")
    }

    // Part 2
    computer.execute(
        program = inputProgram,
        source = { Computer.Input.Value(5) },
        sink = { output -> println("Part 2 solution: $output") }
    )
}
