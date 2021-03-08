package homework_2

const val NUMBER0 = 0
const val NUMBER1 = 1
const val NUMBER2 = 2
const val NUMBER3 = 3

fun IntArray.removeRepeats(): IntArray {
    val set: MutableSet<Int> = mutableSetOf()
    for (i in this.indices.reversed()) {
        if (!set.contains(this[i])) {
            set.add(this[i])
        }
    }
    return set.toIntArray().reversedArray()
}

fun main() {
    val array: IntArray = intArrayOf(NUMBER0, NUMBER1, NUMBER2, NUMBER3, NUMBER3, NUMBER2, NUMBER0, NUMBER1)
    array.forEach { print(it) }
    print("\nAfter remove\n")
    array.removeRepeats().forEach { print(it) }
}