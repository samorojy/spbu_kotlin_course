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

    override fun IntArray.runParallelMerging(
        firstPart: MergingPart,
        middleOfFirstPart: Int,
        secondPart: MergingPart,
        middleOfSecondPart: Int,
        arrayMergeTo: IntArray,
        leftBoundOfArrayToPaste: Int,
        middleOfArrayToPaste: Int,
        numberOfThreads: Int
    ) {
        val numberOfLeftThreads = numberOfThreads / 2
        val numberOfRightThreads = numberOfThreads - numberOfLeftThreads
        runBlocking {
            val leftCoroutine =
                launch {
                    this@runParallelMerging.mergeMultiThread(
                        MergingPart(firstPart.leftBound, middleOfFirstPart - 1),
                        MergingPart(secondPart.leftBound, middleOfSecondPart - 1),
                        arrayMergeTo,
                        leftBoundOfArrayToPaste,
                        numberOfLeftThreads
                    )
                }
            val rightCoroutine =
                launch {
                    this@runParallelMerging.mergeMultiThread(
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
