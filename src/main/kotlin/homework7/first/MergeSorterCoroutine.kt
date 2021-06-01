package homework7.first

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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
        sortedArrayProperties: SortedArrayProperties,
        temporaryArray: IntArray,
        numberOfThreads: Int
    ) {
        val numberOfLeftThreads = numberOfThreads / 2
        val numberOfRightThreads = numberOfThreads - numberOfLeftThreads
        runBlocking {
            launch {
                this@runParallelMergeSorting.mergeSortingMultiThread(
                    MergingPart(sortedArrayProperties.sortingPart.leftBound, sortedArrayProperties.middle),
                    temporaryArray, 0,
                    numberOfLeftThreads
                )
            }
            launch {
                this@runParallelMergeSorting.mergeSortingMultiThread(
                    MergingPart(sortedArrayProperties.middle + 1, sortedArrayProperties.sortingPart.rightBound),
                    temporaryArray, sortedArrayProperties.newMiddle + 1,
                    numberOfRightThreads
                )
            }
        }
    }

    override fun IntArray.runParallelMerging(
        partProperties: SortingPartsProperties,
        arrayToPasteProperties: ArrayToPasteProperties,
        numberOfThreads: Int
    ) {
        val numberOfLeftThreads = numberOfThreads / 2
        val numberOfRightThreads = numberOfThreads - numberOfLeftThreads
        runBlocking {
            launch {
                this@runParallelMerging.mergeMultiThread(
                    MergingPart(partProperties.firstPart.leftBound, partProperties.middleOfFirstPart - 1),
                    MergingPart(partProperties.secondPart.leftBound, partProperties.middleOfSecondPart - 1),
                    arrayToPasteProperties.arrayMergeTo,
                    arrayToPasteProperties.leftBoundOfArrayToPaste,
                    numberOfLeftThreads
                )
            }
            launch {
                this@runParallelMerging.mergeMultiThread(
                    MergingPart(partProperties.middleOfFirstPart + 1, partProperties.firstPart.rightBound),
                    MergingPart(partProperties.middleOfSecondPart, partProperties.secondPart.rightBound),
                    arrayToPasteProperties.arrayMergeTo,
                    arrayToPasteProperties.middleOfArrayToPaste + 1,
                    numberOfRightThreads
                )
            }
        }
    }
}
