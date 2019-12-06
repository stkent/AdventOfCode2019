import Computer.Opcode.*
import Computer.ParamMode.Immediate
import Computer.ParamMode.Position
import extensions.digits

fun main() {
    val inputProgram = resourceFile("input.txt")
        .readLines()
        .first()
        .split(',')
        .map(String::toInt)

    val computer = Computer()

    // Part 1
    val outputs = mutableListOf<Int>()
    computer.execute(
        program = inputProgram,
        source = { 1 },
        sink = { output -> if (output > 0) outputs += output }
    )

    if (outputs.size == 1) {
        println("Part 1 solution: ${outputs.first()}")
    } else {
        println("Tests failed with codes ${outputs.dropLast(1).joinToString(",")}")
    }

    // Part 2
    computer.execute(
        program = inputProgram,
        source = { 5 },
        sink = { output -> println("Part 2 solution: $output") }
    )
}

class Computer {

    fun execute(
        program: List<Int>,
        source: (() -> Int)? = null,
        sink: ((Int) -> Unit)? = null
    ): List<Int> {

        val maxIp = program.size - 1

        val memory: MutableList<Int> = program.toMutableList()
        var ip = 0
        var run = true

        fun resolve(param: Int, mode: ParamMode, memory: List<Int>) = when (mode) {
            Position -> memory[param]
            Immediate -> param
        }

        while (ip <= maxIp && run) {
            val (opcode, paramModes) = parseInstruction(memory[ip])
            val rawParams = (1..opcode.paramCount).map { offset -> memory[ip + offset] }
            val resParams = paramModes.zip(rawParams).map { (paramMode, rawParam) ->
                resolve(rawParam, paramMode, memory)
            }

            var didSetIp = false

            when (opcode) {
                Add -> memory[rawParams[2]] = resParams[0] + resParams[1]
                Multiply -> memory[rawParams[2]] = resParams[0] * resParams[1]

                Read -> {
                    memory[rawParams[0]] = source?.invoke() ?: run {
                        throw IllegalStateException("No source to read from")
                    }
                }

                Write -> sink?.invoke(resParams[0])

                JumpIfTrue -> if (resParams[0] != 0) {
                    ip = resParams[1]
                    didSetIp = true
                }

                JumpIfFalse -> if (resParams[0] == 0) {
                    ip = resParams[1]
                    didSetIp = true
                }

                LessThan -> memory[rawParams[2]] = if (resParams[0] < resParams[1]) 1 else 0
                Equals -> memory[rawParams[2]] = if (resParams[0] == resParams[1]) 1 else 0
                Halt -> run = false
            }

            if (!didSetIp) ip += opcode.paramCount + 1
        }

        return memory
    }

    private fun parseInstruction(rawInstruction: Int): Instruction {
        val rawOpcode = (rawInstruction % 100)
        val opcode = Opcode.fromInt(rawOpcode)!!

        val rawParamModes = (rawInstruction / 100).digits().reversed()
        val paramModes = (0 until opcode.paramCount).map { offset ->
            ParamMode.values()[rawParamModes.getOrElse(offset) { 0 }]
        }

        return Instruction(opcode, paramModes)
    }

    private data class Instruction(val opCode: Opcode, val paramModes: List<ParamMode>)

    private enum class Opcode(val paramCount: Int) {
        Add(3),
        Multiply(3),
        Read(1),
        Write(1),
        JumpIfTrue(2),
        JumpIfFalse(2),
        LessThan(3),
        Equals(3),
        Halt(0);

        companion object {
            fun fromInt(int: Int): Opcode? = when (int) {
                1 -> Add
                2 -> Multiply
                3 -> Read
                4 -> Write
                5 -> JumpIfTrue
                6 -> JumpIfFalse
                7 -> LessThan
                8 -> Equals
                99 -> Halt
                else -> null
            }
        }
    }

    private enum class ParamMode {
        Position, Immediate
    }

}
