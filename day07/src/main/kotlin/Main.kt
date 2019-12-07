import Amplifier.Opcode.*
import Amplifier.ParamMode.Immediate
import Amplifier.ParamMode.Position
import extensions.digits
import extensions.permutations
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    val inputProgram = resourceFile("input.txt")
        .readLines()
        .first()
        .split(',')
        .map(String::toInt)

    println("Part 1 solution: ${computeMaxSignal(inputProgram)}")
}

fun computeMaxSignal(program: List<Int>): Int {
    return (0..4).toList()
        .permutations()
        .map { phases -> computeSignal(program, phases) }
        .max()!!
}

fun computeSignal(program: List<Int>, phases: List<Int>): Int {
    val amplifierA = Amplifier("A")
    val amplifierB = Amplifier("B")
    val amplifierC = Amplifier("C")
    val amplifierD = Amplifier("D")
    val amplifierE = Amplifier("E")

    val sourceA = Channel<Int>()
    val channelAB = Channel<Int>()
    val channelBC = Channel<Int>()
    val channelCD = Channel<Int>()
    val channelDE = Channel<Int>()
    val sinkE = Channel<Int>()

    return runBlocking {
        launch {
            println("Setting Amplifier A phase to ${phases[0]}")
            sourceA.send(phases[0])
        }
        launch {
            println("Setting Amplifier A input to 0")
            sourceA.send(0)
        }
        launch {
            println("Setting Amplifier B phase to ${phases[1]}")
            channelAB.send(phases[1])
        }
        launch {
            println("Setting Amplifier C phase to ${phases[2]}")
            channelBC.send(phases[2])
        }
        launch {
            println("Setting Amplifier D phase to ${phases[3]}")
            channelCD.send(phases[3])
        }
        launch {
            println("Setting Amplifier E phase to ${phases[4]}")
            channelDE.send(phases[4])
        }

        launch {
            println("Running Amplifier A")
            amplifierA.execute(program, sourceA, channelAB)
            println("Amplifier A finished running")
        }

        launch {
            println("Running Amplifier B")
            amplifierB.execute(program, channelAB, channelBC)
            println("Amplifier B finished running")
        }

        launch {
            println("Running Amplifier C")
            amplifierC.execute(program, channelBC, channelCD)
            println("Amplifier C finished running")
        }

        launch {
            println("Running Amplifier D")
            amplifierD.execute(program, channelCD, channelDE)
            println("Amplifier D finished running")
        }

        launch {
            println("Running Amplifier E")
            amplifierE.execute(program, channelDE, sinkE)
            println("Amplifier E finished running")
        }

        return@runBlocking sinkE.receive()
    }
}

private class Amplifier(val name: String) {

    suspend fun execute(
        program: List<Int>,
        source: Channel<Int>,
        sink: Channel<Int>
    ): List<Int> {
        println("Amplifier $name beginning execution.")

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
                    println("Amplifier $name requesting input.")
                    memory[rawParams[0]] = source.receive()
                    println("Amplifier $name received input.")
                }

                Write -> {
                    println("Amplifier $name writing output.")
                    if (!source.isClosedForSend) {
                        sink.send(resParams[0])
                    }
                    println("Amplifier $name wrote output.")
                }

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
                Halt -> {
                    source.close()
                    run = false
                }
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
