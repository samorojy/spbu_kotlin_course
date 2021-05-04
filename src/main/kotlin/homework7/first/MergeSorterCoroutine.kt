@file:Suppress("LongParameterList")

package homework7.first

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MergeSorterCoroutine {
    fun sort(arrayToSort: IntArray, numberOfThreads: Int) {
        if (arrayToSort.isEmpty()) return
        val temporaryArray = IntArray(arrayToSort.size) { 0 }
        runBlocking {
            arrayToSort.mergeSortingMultiThread(
                MergingPart(0, arrayToSort.lastIndex),
                sortedArray = temporaryArray,
                numberOfThreads = numberOfThreads
            )
        }
        for (i in arrayToSort.indices) arrayToSort[i] = temporaryArray[i]
    }

    private fun IntArray.binarySearch(valueToFind: Int, left: Int, right: Int): Int {
        var leftBound = left
        var rightBound = kotlin.math.max(left, right + 1)
        var middle: Int
        while (leftBound < rightBound) {
            middle = (leftBound + rightBound) / 2
            if (valueToFind <= this[middle]) {
                rightBound = middle
            } else {
                leftBound = middle + 1
            }
        }
        return rightBound
    }

    data class MergingPart(val leftBound: Int, val rightBound: Int)

    private suspend fun IntArray.mergeMultiThread(
        firstPart: MergingPart,
        secondPart: MergingPart,
        arrayMergeTo: IntArray,
        leftBoundOfArrayToPaste: Int,
        numberOfThreads: Int = 1
    ) {
        val firstMergingPartSize = firstPart.rightBound - firstPart.leftBound + 1
        val secondMergingPartSize = secondPart.rightBound - secondPart.leftBound + 1
        if (firstMergingPartSize < secondMergingPartSize) {
            this.mergeMultiThread(
                secondPart,
                firstPart,
                arrayMergeTo,
                leftBoundOfArrayToPaste
            )
            return
        }
        if (firstMergingPartSize == 0) {
            return
        }
        val middleOfFirstPart =
            (firstPart.leftBound + firstPart.rightBound) / 2
        val middleOfSecondPart =
            this.binarySearch(this[middleOfFirstPart], secondPart.leftBound, secondPart.rightBound)
        val sizeHalfPartFirst = middleOfFirstPart - firstPart.leftBound
        val sizeHalfPartSecond = middleOfSecondPart - secondPart.leftBound
        val middleOfArrayToPaste = leftBoundOfArrayToPaste + sizeHalfPartFirst + sizeHalfPartSecond

        arrayMergeTo[middleOfArrayToPaste] = this[middleOfFirstPart]
        val numberOfLeftThreads = numberOfThreads / 2
        val numberOfRightThreads = numberOfThreads - numberOfLeftThreads
        val currentArray = this
        coroutineScope {
            val leftCoroutine =
                launch {
                    currentArray.mergeMultiThread(
                        MergingPart(firstPart.leftBound, middleOfFirstPart - 1),
                        MergingPart(secondPart.leftBound, middleOfSecondPart - 1),
                        arrayMergeTo,
                        leftBoundOfArrayToPaste,
                        numberOfLeftThreads
                    )
                }
            val rightCoroutine =
                launch {
                    currentArray.mergeMultiThread(
                        MergingPart(middleOfFirstPart + 1, firstPart.rightBound),
                        MergingPart(middleOfSecondPart, secondPart.rightBound),
                        arrayMergeTo,
                        middleOfArrayToPaste + 1,
                        numberOfRightThreads
                    )
                }
            leftCoroutine.join()
            rightCoroutine.join()
        }
    }

    private suspend fun IntArray.mergeSortingMultiThread(
        mergingPart: MergingPart,
        sortedArray: IntArray,
        leftBoundOfArrayToPaste: Int = 0,
        numberOfThreads: Int = 1
    ) {
        val sortingPartSize = mergingPart.rightBound - mergingPart.leftBound + 1
        if (sortingPartSize == 1) {
            sortedArray[leftBoundOfArrayToPaste] = this[mergingPart.leftBound]
        } else {
            val temporaryArray = IntArray(sortingPartSize) { 0 }
            val middle = (mergingPart.leftBound + mergingPart.rightBound) / 2
            val newMiddle = middle - mergingPart.leftBound
            if (numberOfThreads == 1) {
                this.mergeSortingMultiThread(MergingPart(mergingPart.leftBound, middle), temporaryArray, 0)
                this.mergeSortingMultiThread(
                    MergingPart(middle + 1, mergingPart.rightBound),
                    temporaryArray,
                    newMiddle + 1
                )
            } else {
                val numberOfLeftThreads = numberOfThreads / 2
                val numberOfRightThreads = numberOfThreads - numberOfLeftThreads
                val currentArray = this
                coroutineScope {
                    val leftCoroutine =
                        launch {
                            currentArray.mergeSortingMultiThread(
                                MergingPart(mergingPart.leftBound, middle),
                                temporaryArray, 0,
                                numberOfLeftThreads
                            )
                        }
                    val rightCoroutine =
                        launch {
                            currentArray.mergeSortingMultiThread(
                                MergingPart(middle + 1, mergingPart.rightBound),
                                temporaryArray, newMiddle + 1,
                                numberOfRightThreads
                            )
                        }
                    leftCoroutine.join()
                    rightCoroutine.join()
                }
            }
            temporaryArray.mergeMultiThread(
                MergingPart(0, newMiddle),
                MergingPart(newMiddle + 1, sortingPartSize - 1),
                sortedArray,
                leftBoundOfArrayToPaste,
                numberOfThreads
            )
        }
    }
}
