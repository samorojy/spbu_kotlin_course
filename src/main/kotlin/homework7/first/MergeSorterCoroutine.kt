package homework7.first

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Suppress("LongMethod")
class MergeSorterCoroutine : MergeSorter() {

    override fun IntArray.mergeSorting(mergingPart: MergingPart, temporaryArray: IntArray, numberOfThreads: Int) {
        runBlocking {
            this@mergeSorting.mergeSortingMultiThread(
                MergingPart(0, this@mergeSorting.lastIndex),
                sortedArray = temporaryArray,
                numberOfThreads = numberOfThreads
            )
        }
    }

    override fun IntArray.runParallelMergeSorting(
        sortingPart: MergingPart,
        sortedArray: IntArray,
        leftBoundOfArrayToPaste: Int,
        middle: Int,
        newMiddle: Int,
        temporaryArray: IntArray,
        numberOfThreads: Int
    ) {
        val numberOfLeftThreads = numberOfThreads / 2
        val numberOfRightThreads = numberOfThreads - numberOfLeftThreads
        runBlocking {
            launch {
                this@runParallelMergeSorting.mergeSortingMultiThread(
                    MergingPart(sortingPart.leftBound, middle),
                    temporaryArray, 0,
                    numberOfLeftThreads
                )
            }
            launch {
                this@runParallelMergeSorting.mergeSortingMultiThread(
                    MergingPart(middle + 1, sortingPart.rightBound),
                    temporaryArray, newMiddle + 1,
                    numberOfRightThreads
                )
            }
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
            launch {
                this@runParallelMerging.mergeMultiThread(
                    MergingPart(firstPart.leftBound, middleOfFirstPart - 1),
                    MergingPart(secondPart.leftBound, middleOfSecondPart - 1),
                    arrayMergeTo,
                    leftBoundOfArrayToPaste,
                    numberOfLeftThreads
                )
            }
            launch {
                this@runParallelMerging.mergeMultiThread(
                    MergingPart(middleOfFirstPart + 1, firstPart.rightBound),
                    MergingPart(middleOfSecondPart, secondPart.rightBound),
                    arrayMergeTo,
                    middleOfArrayToPaste + 1,
                    numberOfRightThreads
                )
            }
        }
    }
}
