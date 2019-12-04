import extensions.allMatch
import extensions.digits

fun main() {
//    // Part 1
//    (273025..767253)
//        .map(Int::digits)
//        .filter { digits ->
//            digits
//                .windowed(size = 2, step = 1)
//                .any { pair -> pair.allMatch() }
//        }
//        .filter(Iterable<Int>::isNonDecreasing)
//        .count()
//        .also { println(it) }

    println(111122.digits().runs())
    println(112233.digits().runs())
    println(221133.digits().runs())

    // Part 2
    (273025..767253)
        .map(Int::digits)
        .filter { digits ->
            digits
                .runs()
                .any { it.size == 2 }
        }
        .filter(Iterable<Int>::isNonDecreasing)
        .count()
        .also { println(it) }
}

fun <T : Comparable<T>> Iterable<T>.isNonDecreasing(): Boolean {
    return windowed(size = 2, step = 1)
        .all { pair -> pair[0] <= pair[1] }
}

fun <T: Comparable<T>> Iterable<T>.runs(): List<List<T>> {
    val allRuns = mutableListOf<List<T>>()
    var currentValue: T? = null
    var currentRunLength = 0

    val iterator = iterator()
    while (iterator.hasNext()) {
        val nextValue = iterator.next()

        if (currentRunLength == 0) {
            currentValue = nextValue
            currentRunLength = 1
        } else {
            if (nextValue == currentValue) {
                currentRunLength += 1
            } else {
                allRuns.add(List(currentRunLength) { currentValue!! })
                currentValue = nextValue
                currentRunLength = 1
            }
        }
    }

    if (currentRunLength > 0) {
        allRuns.add(List(currentRunLength) { currentValue!! })
    }

    return allRuns
}