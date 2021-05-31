@file:Suppress("LongParameterList")

package homework7.first

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Suppress("LongMethod")
class MergeSorterCoroutine : MergeSorter() {
    override fun IntArray.mergeSorting(mergingPart: MergingPart, temporaryArray: IntArray, numberOfThreads: Int) {
        runBlocking {
            this@mergeSorting.mergeSortingMultiCoroutine(
                MergingPart(0, this@mergeSorting.lastIndex),
                sortedArray = temporaryArray,
                numberOfThreads = numberOfThreads
            )
        }
    }

    private suspend fun IntArray.mergeMultiCoroutine(
        firstPart: MergingPart,
        secondPart: MergingPart,
        arrayMergeTo: IntArray,
        leftBoundOfArrayToPaste: Int,
        numberOfThreads: Int = 1
    ) {
        val firstMergingPartSize = firstPart.rightBound - firstPart.leftBound + 1
        val secondMergingPartSize = secondPart.rightBound - secondPart.leftBound + 1
        if (firstMergingPartSize < secondMergingPartSize) {
            this.mergeMultiCoroutine(
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
        if (numberOfThreads > 1) {
            val numberOfLeftThreads = numberOfThreads / 2
            val numberOfRightThreads = numberOfThreads - numberOfLeftThreads
            val currentArray = this
            coroutineScope {
                val leftCoroutine =
                    launch {
                        currentArray.mergeMultiCoroutine(
                            MergingPart(firstPart.leftBound, middleOfFirstPart - 1),
                            MergingPart(secondPart.leftBound, middleOfSecondPart - 1),
                            arrayMergeTo,
                            leftBoundOfArrayToPaste,
                            numberOfLeftThreads
                        )
                    }
                val rightCoroutine =
                    launch {
                        currentArray.mergeMultiCoroutine(
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
        } else {
            this.mergeMultiCoroutine(
                MergingPart(firstPart.leftBound, middleOfFirstPart - 1),
                MergingPart(secondPart.leftBound, middleOfSecondPart - 1),
                arrayMergeTo,
                leftBoundOfArrayToPaste,
            )
            this.mergeMultiCoroutine(
                MergingPart(middleOfFirstPart + 1, firstPart.rightBound),
                MergingPart(middleOfSecondPart, secondPart.rightBound),
                arrayMergeTo,
                middleOfArrayToPaste + 1,
            )
        }
    }

    private suspend fun IntArray.mergeSortingMultiCoroutine(
        sortingPart: MergingPart,
        sortedArray: IntArray,
        leftBoundOfArrayToPaste: Int = 0,
        numberOfThreads: Int = 1
    ) {
        val sortingPartSize = sortingPart.rightBound - sortingPart.leftBound + 1
        if (sortingPartSize == 1) {
            sortedArray[leftBoundOfArrayToPaste] = this[sortingPart.leftBound]
        } else {
            val temporaryArray = IntArray(sortingPartSize) { 0 }
            val middle = (sortingPart.leftBound + sortingPart.rightBound) / 2
            val newMiddle = middle - sortingPart.leftBound
            if (numberOfThreads == 1) {
                this.mergeSortingMultiCoroutine(
                    MergingPart(sortingPart.leftBound, middle),
                    temporaryArray, 0
                )
                this.mergeSortingMultiCoroutine(
                    MergingPart(middle + 1, sortingPart.rightBound),
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
                            currentArray.mergeSortingMultiCoroutine(
                                MergingPart(sortingPart.leftBound, middle),
                                temporaryArray, 0,
                                numberOfLeftThreads
                            )
                        }
                    val rightCoroutine =
                        launch {
                            currentArray.mergeSortingMultiCoroutine(
                                MergingPart(middle + 1, sortingPart.rightBound),
                                temporaryArray, newMiddle + 1,
                                numberOfRightThreads
                            )
                        }
                    leftCoroutine.join()
                    rightCoroutine.join()
                }
            }
            temporaryArray.mergeMultiCoroutine(
                MergingPart(0, newMiddle),
                MergingPart(newMiddle + 1, sortingPartSize - 1),
                sortedArray,
                leftBoundOfArrayToPaste,
                numberOfThreads
            )
        }
    }
}
