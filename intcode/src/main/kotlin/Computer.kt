import extensions.digits

class Computer {

    sealed class Input {
        class Value(val rawValue: Long) : Input()
        object Terminate : Input()
    }

    /**
     * Runs the given [program] and returns the final memory state. Any inputs needed are requested
     * from the [source]. Any outputs produced are provided to the [sink].
     */
    fun execute(
        program: List<Long>,
        source: (() -> Input)? = null,
        sink: ((Long) -> Unit)? = null
    ): Map<Long, Long> {

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

        fun nextArg(mode: ParamMode): Long {
            val rawArg = memory.getValue(ip++)
            return when (mode) {
                ParamMode.Position -> memory.getValue(rawArg)
                ParamMode.Immediate -> rawArg
                ParamMode.Relative -> memory.getValue(relativeBase + rawArg)
            }
        }

        fun nextWriteAddress(mode: ParamMode): Long {
            val rawWriteAddress = memory.getValue(ip++)
            return when (mode) {
                ParamMode.Position -> rawWriteAddress
                ParamMode.Immediate -> throw IllegalStateException("Invalid write address mode.")
                ParamMode.Relative -> relativeBase + rawWriteAddress
            }
        }

        while (run) {
            val (opcode, modes) = Instruction(memory.getValue(ip)).also { ip++ }

            when (opcode) {
                Opcode.Add -> {
                    val arg1 = nextArg(modes(0))
                    val arg2 = nextArg(modes(1))
                    memory[nextWriteAddress(modes(2))] = arg1 + arg2
                }

                Opcode.Multiply -> {
                    val arg1 = nextArg(modes(0))
                    val arg2 = nextArg(modes(1))
                    memory[nextWriteAddress(modes(2))] = arg1 * arg2
                }

                Opcode.Read -> {
                    when (val input = source?.invoke()) {
                        is Input.Value -> memory[nextWriteAddress(modes(0))] = input.rawValue
                        Input.Terminate -> run = false
                        null -> throw IllegalStateException("No source to read from.")
                    }
                }

                Opcode.Write -> sink?.invoke(nextArg(modes(0)))

                Opcode.JumpIfTrue -> {
                    val arg1 = nextArg(modes(0))
                    val arg2 = nextArg(modes(1))
                    if (arg1 != 0L) ip = arg2
                }

                Opcode.JumpIfFalse -> {
                    val arg1 = nextArg(modes(0))
                    val arg2 = nextArg(modes(1))
                    if (arg1 == 0L) ip = arg2
                }

                Opcode.LessThan -> {
                    val arg1 = nextArg(modes(0))
                    val arg2 = nextArg(modes(1))
                    memory[nextWriteAddress(modes(2))] = if (arg1 < arg2) 1 else 0L
                }

                Opcode.Equals -> {
                    val arg1 = nextArg(modes(0))
                    val arg2 = nextArg(modes(1))
                    memory[nextWriteAddress(modes(2))] = if (arg1 == arg2) 1 else 0L
                }

                Opcode.AddToRelativeBase -> relativeBase += nextArg(modes(0))
                Opcode.Halt -> run = false
            }
        }

        return memory
    }

}

private data class Instruction(val opCode: Opcode, val modes: (Int) -> ParamMode) {
    companion object {
        operator fun invoke(rawInstruction: Long): Instruction {
            val rawOpcode = (rawInstruction % 100)
            val opcode = Opcode.fromLong(rawOpcode) ?: run {
                throw IllegalStateException("Unexpected instruction: $rawInstruction.")
            }

            val paramModes = (rawInstruction / 100).digits()
                .reversed()
                .map { index -> ParamMode.values()[index] }

            return Instruction(opcode) { index ->
                paramModes.getOrElse(index) { ParamMode.Position }
            }
        }
    }
}

private enum class Opcode {
    Add,
    Multiply,
    Read,
    Write,
    JumpIfTrue,
    JumpIfFalse,
    LessThan,
    Equals,
    AddToRelativeBase,
    Halt;

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
