package exam

import java.lang.ArithmeticException

/**
 * Sorts the Iterable using the resulting comparator
 * returns a sorted list
 */
fun <T> Iterable<T>.bubbleSort(comparator: Comparator<T>): List<T> {
    val list = this.toMutableList()
    var swap = true
    while (swap) {
        swap = false
        for (i in 0 until list.lastIndex) {
            val compareResult = try {
                comparator.compare(list[i], list[i + 1])
            } catch (e: ArithmeticException) {
                println("comparison error: ${e.message}")
                return emptyList()
            }
            if (compareResult > 0) {
                list[i] = list[i + 1].also { list[i + 1] = list[i] }
                swap = true
            }
        }
    }
    return list
}
