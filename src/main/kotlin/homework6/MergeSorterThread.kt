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
        val leftThread =
            Thread {
                this.mergeSortingMultiThread(
                    MergingPart(sortingPart.leftBound, middle),
                    temporaryArray, 0,
                    numberOfLeftThreads
                )
            }
        val rightThread =
            Thread {
                this.mergeSortingMultiThread(
                    MergingPart(middle + 1, sortingPart.rightBound),
                    temporaryArray, newMiddle + 1,
                    numberOfRightThreads
                )
            }
        leftThread.start()
        rightThread.start()
        leftThread.join()
        rightThread.join()
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
        val leftThread =
            Thread {
                this@runParallelMerging.mergeMultiThread(
                    MergingPart(firstPart.leftBound, middleOfFirstPart - 1),
                    MergingPart(secondPart.leftBound, middleOfSecondPart - 1),
                    arrayMergeTo,
                    leftBoundOfArrayToPaste,
                    numberOfLeftThreads
                )
            }
        val rightThread =
            Thread {
                this@runParallelMerging.mergeMultiThread(
                    MergingPart(middleOfFirstPart + 1, firstPart.rightBound),
                    MergingPart(middleOfSecondPart, secondPart.rightBound),
                    arrayMergeTo,
                    middleOfArrayToPaste + 1,
                    numberOfRightThreads
                )
            }
        leftThread.start()
        rightThread.start()
        leftThread.join()
        rightThread.join()
    }
}
