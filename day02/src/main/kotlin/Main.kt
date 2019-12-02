import Opcode.*

fun main() {
    val inputProgram = resourceFile("input.txt")
        .readLines()
        .first()
        .split(',')
        .map(String::toInt)

    val computer = Computer()

    // Part 1
    inputProgram
        .toMutableList()
        .apply {
            set(1, 12)
            set(2, 2)
        }
        .let(computer::execute)
        .also { output -> println("Part 1 solution: ${output[0]}") }

    // Part 2
    val targetOutput = 19690720

    for (noun in 0..99) {
        for (verb in 0..99) {
            val output = inputProgram
                .toMutableList()
                .apply {
                    set(1, noun)
                    set(2, verb)
                }
                .let(computer::execute)

            if (output[0] == targetOutput) println("Part 2 solution: ${100 * noun + verb}.")
        }
    }
}

class Computer {

    fun execute(program: List<Int>): List<Int> {
        val maxIp = program.size - 1

        val memory = program.toMutableList()
        var ip = 0
        var run = true

        while (ip <= maxIp && run) {
            val opcode = Opcode.fromInt(memory[ip])!!
            val args = (1..opcode.argCount).map { offset -> memory[ip + offset] }

            when (opcode) {
                Add -> memory[args[2]] = memory[args[0]] + memory[args[1]]
                Mul -> memory[args[2]] = memory[args[0]] * memory[args[1]]
                End -> run = false
            }

            if (run) ip += opcode.argCount + 1
        }

        return memory
    }

}

enum class Opcode(val argCount: Int) {
    Add(3), Mul(3), End(0);

    companion object {
        fun fromInt(int: Int): Opcode? = when (int) {
               1 -> Add
               2 -> Mul
              99 -> End
            else -> null
        }
    }
}
