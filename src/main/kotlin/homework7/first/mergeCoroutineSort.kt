package homework7.first

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.launch

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

fun IntArray.mergeSortingMainMultiCoroutines(numberOfThreads: Int) {
    val arrayToSort = this
    runBlocking {
        launch {
            arrayToSort.mergeSortingMultiCoroutines(
                0,
                arrayToSort.lastIndex + 1,
                numberOfThreads
            )
        }
    }
}

suspend fun IntArray.mergeSortingMultiCoroutines(left: Int, right: Int, numberOfCoroutines: Int) {
    val middle = (left + right) / 2
    val arrayToSort = this

    if (numberOfCoroutines > 1 && right - left >= 1) {
        val numberOfLeftCoroutines = numberOfCoroutines / 2
        val numberOfRightCoroutines = numberOfCoroutines - numberOfLeftCoroutines

        coroutineScope {
            launch {
                arrayToSort.mergeSortingMultiCoroutines(left, middle, numberOfLeftCoroutines)
            }
            launch {
                arrayToSort.mergeSortingMultiCoroutines(middle, right, numberOfRightCoroutines)
            }
        }
        this.merge(left, middle, right)
    } else {
        this.mergeSortingSingleThread(left, right)
    }
}
