package homework6

fun IntArray.merge(left: Int, middle: Int, right: Int) {
    var it1 = 0
    var it2 = 0
    val temporaryArray = Array(right - left) { 0 }

    while ((left + it1) < middle && (middle + it2) < right)
        if (this[left + it1] < this[middle + it2]) {
            temporaryArray[it1 + it2] = this[left + it1]
            it1 += 1
        } else {
            temporaryArray[it1 + it2] = this[middle + it2]
            it2 += 1
        }

    while (left + it1 < middle) {
        temporaryArray[it1 + it2] = this[left + it1]
        it1 += 1
    }
    while (middle + it2 < right) {
        temporaryArray[it1 + it2] = this[middle + it2]
        it2 += 1
    }

    for (i in 0 until (it1 + it2)) {
        this[left + i] = temporaryArray[i]
    }
}

fun IntArray.mergeSortingMT(left: Int = 0, right: Int = this.lastIndex + 1) {
    if (left + 1 >= right) return
    val middle = (left + right) / 2
    this.mergeSortingMT(left, middle)
    this.mergeSortingMT(middle, right)
    this.merge(left, middle, right)
}

fun main() {
    val array = intArrayOf(10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
    array.mergeSortingMT()
    array.forEach { print(it) }
}