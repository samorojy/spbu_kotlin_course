package homework2

fun Array<Int>.removeRepeats(): Array<Int> {
    val set: MutableSet<Int> = mutableSetOf()
    set.addAll(this.reversedArray())
    return set.toTypedArray().reversedArray()
}

fun getFilledArray(): Array<Int> {
    println("Enter array size: ")
    val scan = java.util.Scanner(System.`in`)
    if (!scan.hasNextInt()) {
        error("ERROR! String cannot be converted to INT")
    }
    val arraySize: Int = scan.nextInt()
    val array = IntArray(arraySize)
    println("Enter $arraySize values (elements of array): ")
    for (i in array.indices) {
        if (!scan.hasNextInt()) {
            error("ERROR! String cannot be converted to INT")
        }
        array[i] = scan.nextInt()
    }
    return array.toTypedArray()
}

fun main() {
    val array: Array<Int> = getFilledArray()
    array.forEach { print(it) }
    print("\nAfter remove\n")
    array.removeRepeats().forEach { print(it) }
}
