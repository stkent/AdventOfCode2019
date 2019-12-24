fun main() {
    val inputProgram = Computer.parseProgramFile("input.txt")

    val computer = Computer()

    // Part 1
    val outputs1 = mutableListOf<Long>()
    computer.execute(
        program = inputProgram,
        source = { Computer.Input.Value(1) },
        sink = { output -> if (output > 0) outputs1 += output }
    )

    if (outputs1.size == 1) {
        println("Part 1 solution: ${outputs1.first()}")
    } else {
        println("Part 1 checks failed with codes ${outputs1.dropLast(1).joinToString(",")}")
    }

    // Part 2
    val outputs2 = mutableListOf<Long>()
    computer.execute(
        program = inputProgram,
        source = { Computer.Input.Value(2) },
        sink = { output -> if (output > 0) outputs2 += output }
    )

    if (outputs2.size == 1) {
        println("Part 2 solution: ${outputs2.first()}")
    } else {
        println("Part 2 checks failed with codes ${outputs2.dropLast(1).joinToString(",")}")
    }

}
