package homework6

fun IntArray.binarySearch(value: Int, left: Int, right: Int): Int {
    var leftBound = left
    var rightBound = kotlin.math.max(left, right + 1)
    var middle: Int
    while (leftBound < rightBound) {
        middle = (leftBound + rightBound) / 2
        if (value <= this[middle]) {
            rightBound = middle
        } else {
            leftBound = middle + 1
        }
    }
    return rightBound
}

fun IntArray.merge(left: Int, middle: Int, right: Int) {
    var leftCounter = 0
    var rightCounter = 0
    val temporaryArray = Array(right - left) { 0 }

    while ((left + leftCounter) < middle && (middle + rightCounter) < right)
        if (this[left + leftCounter] < this[middle + rightCounter]) {
            temporaryArray[leftCounter + rightCounter] = this[left + leftCounter]
            leftCounter += 1
        } else {
            temporaryArray[leftCounter + rightCounter] = this[middle + rightCounter]
            rightCounter += 1
        }

    while (left + leftCounter < middle) {
        temporaryArray[leftCounter + rightCounter] = this[left + leftCounter]
        leftCounter += 1
    }
    while (middle + rightCounter < right) {
        temporaryArray[leftCounter + rightCounter] = this[middle + rightCounter]
        rightCounter += 1
    }

    for (i in 0 until (leftCounter + rightCounter)) {
        this[left + i] = temporaryArray[i]
    }
}

fun IntArray.mergeSortingSingleThread(left: Int = 0, right: Int = this.lastIndex + 1) {
    if (left + 1 >= right) return
    val middle = (left + right) / 2
    this.mergeSortingSingleThread(left, middle)
    this.mergeSortingSingleThread(middle, right)
    this.merge(left, middle, right)
}

fun IntArray.mergeSortingMainMultiThread(numberOfThreads: Int) {
    val mainThread = Thread { this.mergeSortingMultiThread(0, this.lastIndex + 1, numberOfThreads) }
    mainThread.run()
    mainThread.join()
}

fun IntArray.mergeSortingMultiThread(left: Int, right: Int, numberOfThreads: Int) {
    val middle = (left + right) / 2

    if (numberOfThreads > 1 && right - left >= 1) {
        val numberOfLeftThreads = numberOfThreads / 2
        val numberOfRightThreads = numberOfThreads - numberOfLeftThreads

        val leftThread = Thread { this.mergeSortingMultiThread(left, middle, numberOfLeftThreads) }
        val rightThread = Thread { this.mergeSortingMultiThread(middle, right, numberOfRightThreads) }
        leftThread.run()
        rightThread.run()
        leftThread.join()
        rightThread.join()
        this.merge(left, middle, right)
    } else {
        this.mergeSortingSingleThread(left, right)
    }
}
