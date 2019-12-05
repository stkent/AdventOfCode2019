import Computer.Opcode.*
import Computer.ParamMode.*
import extensions.digits
import java.util.*

fun main() {
    val inputProgram = resourceFile("input.txt")
        .readLines()
        .first()
        .split(',')
        .map(String::toInt)

//    val inputProgram = listOf(3, 0, 4, 0, 99)
//    val inputProgram = listOf(1101, 100, -1, 4, 0)
//    val inputProgram = listOf(3, 3, 1107, -1, 8, 3, 4, 3, 99)
//    val inputProgram = listOf(3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99)

    val computer = Computer()

    computer.execute(
        inputProgram,
        object : Computer.Source {
            override fun read(): Int {
                print("Input number: ")
                return Scanner(System.`in`).nextLine()!!.toInt()
            }
        },
        object : Computer.Sink {
            override fun write(i: Int) = println(i)
        }
    )
}

class Computer {

    interface Source {
        fun read(): Int
    }

    interface Sink {
        fun write(i: Int)
    }


    fun execute(program: List<Int>, source: Source, sink: Sink): List<Int> {
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
                Put -> memory[rawParams[0]] = source.read()
                Out -> sink.write(resParams[0])

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
        Put(1),
        Out(1),
        JumpIfTrue(2),
        JumpIfFalse(2),
        LessThan(3),
        Equals(3),
        Halt(0);

        companion object {
            fun fromInt(int: Int): Opcode? = when (int) {
                1 -> Add
                2 -> Multiply
                3 -> Put
                4 -> Out
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
