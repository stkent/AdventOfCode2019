import Computer.Opcode.*
import Computer.ParamMode.*
import extensions.digits

fun main() {
    val inputProgram = resourceFile("input.txt")
        .readLines()
        .first()
        .split(',')
        .map(String::toLong)

    val computer = Computer()

    val outputs = mutableListOf<Long>()
    computer.execute(
        program = inputProgram,
        source = { 2 },
        sink = { output -> outputs += output }
    )

    println(outputs)
}

class Computer {

    fun execute(
        program: List<Long>,
        source: (() -> Long)? = null,
        sink: ((Long) -> Unit)? = null
    ): Map<Long, Long> {

        val maxIp = program.size - 1

        val memory: MutableMap<Long, Long> = mutableMapOf<Long, Long>()
            .also { memory ->
                program
                    .withIndex()
                    .forEach { (index, value) -> memory[index.toLong()] = value }
            }
            .withDefault { 0 }

        var relativeBase: Long = 0
        var ip: Long = 0
        var run = true

        fun resolve(param: Long, mode: ParamMode): Long = when (mode) {
            Position -> memory.getValue(param)
            Immediate -> param
            Relative -> memory.getValue(relativeBase + param)
        }

        fun resolveRead(param: Long, mode: ParamMode): Long = when (mode) {
            Position -> param
            Immediate -> throw IllegalStateException()
            Relative -> relativeBase + param
        }

        while (ip <= maxIp && run) {
            val (opcode, paramModes) = parseInstruction(memory.getValue(ip))
            val rawParams = (1..opcode.paramCount)
                .map { offset -> memory.getValue(ip + offset.toLong()) }

            val resParams = paramModes.zip(rawParams).map { (paramMode, rawParam) ->
                resolve(rawParam, paramMode)
            }

            var didSetIp = false

            when (opcode) {
                Add -> memory[resolveRead(rawParams[2], paramModes[2])] = resParams[0] + resParams[1]
                Multiply -> memory[resolveRead(rawParams[2], paramModes[2])] = resParams[0] * resParams[1]

                Read -> {
                    memory[resolveRead(rawParams[0], paramModes[0])] = source?.invoke() ?: run {
                        throw IllegalStateException("No source to read from")
                    }
                }

                Write -> sink?.invoke(resParams[0])

                JumpIfTrue -> if (resParams[0] != 0L) {
                    ip = resParams[1]
                    didSetIp = true
                }

                JumpIfFalse -> if (resParams[0] == 0L) {
                    ip = resParams[1]
                    didSetIp = true
                }

                LessThan -> memory[resolveRead(rawParams[2], paramModes[2])] = if (resParams[0] < resParams[1]) 1 else 0L
                Equals -> memory[resolveRead(rawParams[2], paramModes[2])] = if (resParams[0] == resParams[1]) 1 else 0L
                AddToRelativeBase -> relativeBase += resParams[0]
                Halt -> run = false
            }

            if (!didSetIp) ip += opcode.paramCount + 1
        }

        return memory
    }

    private fun parseInstruction(rawInstruction: Long): Instruction {
        val rawOpcode = (rawInstruction % 100)
        val opcode = Opcode.fromLong(rawOpcode)!!

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
        AddToRelativeBase(1),
        Halt(0);

        companion object {
            fun fromLong(long: Long): Opcode? = when (long) {
                1L -> Add
                2L -> Multiply
                3L -> Read
                4L -> Write
                5L -> JumpIfTrue
                6L -> JumpIfFalse
                7L -> LessThan
                8L -> Equals
                9L -> AddToRelativeBase
                99L -> Halt
                else -> null
            }
        }
    }

    private enum class ParamMode {
        Position, Immediate, Relative
    }

}
