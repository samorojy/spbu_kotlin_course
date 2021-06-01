package homework6

import homework7.first.MergeSorter

class MergeSorterThread : MergeSorter() {
    override fun IntArray.mergeSorting(mergingPart: MergingPart, temporaryArray: IntArray, numberOfThreads: Int) {
        this.mergeSortingMultiThread(
            MergingPart(0, this.lastIndex),
            sortedArray = temporaryArray,
            numberOfThreads = numberOfThreads
        )
    }

    override fun IntArray.runParallelMergeSorting(
        sortedArrayProperties: SortedArrayProperties,
        temporaryArray: IntArray,
        numberOfThreads: Int
    ) {
        val numberOfLeftThreads = numberOfThreads / 2
        val numberOfRightThreads = numberOfThreads - numberOfLeftThreads
        val leftThread =
            Thread {
                this.mergeSortingMultiThread(
                    MergingPart(sortedArrayProperties.sortingPart.leftBound, sortedArrayProperties.middle),
                    temporaryArray, 0,
                    numberOfLeftThreads
                )
            }
        val rightThread =
            Thread {
                this.mergeSortingMultiThread(
                    MergingPart(sortedArrayProperties.middle + 1, sortedArrayProperties.sortingPart.rightBound),
                    temporaryArray, sortedArrayProperties.newMiddle + 1,
                    numberOfRightThreads
                )
            }
        leftThread.start()
        rightThread.start()
        leftThread.join()
        rightThread.join()
    }

    override fun IntArray.runParallelMerging(
        partProperties: SortingPartsProperties,
        arrayToPasteProperties: ArrayToPasteProperties,
        numberOfThreads: Int
    ) {
        val numberOfLeftThreads = numberOfThreads / 2
        val numberOfRightThreads = numberOfThreads - numberOfLeftThreads
        val leftThread =
            Thread {
                this@runParallelMerging.mergeMultiThread(
                    MergingPart(partProperties.firstPart.leftBound, partProperties.middleOfFirstPart - 1),
                    MergingPart(partProperties.secondPart.leftBound, partProperties.middleOfSecondPart - 1),
                    arrayToPasteProperties.arrayMergeTo,
                    arrayToPasteProperties.leftBoundOfArrayToPaste,
                    numberOfLeftThreads
                )
            }
        val rightThread =
            Thread {
                this@runParallelMerging.mergeMultiThread(
                    MergingPart(partProperties.middleOfFirstPart + 1, partProperties.firstPart.rightBound),
                    MergingPart(partProperties.middleOfSecondPart, partProperties.secondPart.rightBound),
                    arrayToPasteProperties.arrayMergeTo,
                    arrayToPasteProperties.middleOfArrayToPaste + 1,
                    numberOfRightThreads
                )
            }
        leftThread.start()
        rightThread.start()
        leftThread.join()
        rightThread.join()
    }
}
