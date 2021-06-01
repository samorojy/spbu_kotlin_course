package homework7.first

abstract class MergeSorter : SorterInterface {

    override fun sort(arrayToSort: IntArray, numberOfThreads: Int) {
        if (arrayToSort.isEmpty()) return

        val temporaryArray = IntArray(arrayToSort.size) { 0 }
        arrayToSort.mergeSorting(
            MergingPart(0, arrayToSort.lastIndex),
            temporaryArray,
            numberOfThreads
        )
        temporaryArray.copyInto(arrayToSort)
    }

    abstract fun IntArray.mergeSorting(mergingPart: MergingPart, temporaryArray: IntArray, numberOfThreads: Int)

    @Suppress("LongParameterList")
    abstract fun IntArray.runParallelMerging(
        firstPart: MergingPart,
        middleOfFirstPart: Int,
        secondPart: MergingPart,
        middleOfSecondPart: Int,
        arrayMergeTo: IntArray,
        leftBoundOfArrayToPaste: Int,
        middleOfArrayToPaste: Int,
        numberOfThreads: Int = 1
    )

    @Suppress("LongParameterList")
    abstract fun IntArray.runParallelMergeSorting(
        sortingPart: MergingPart,
        sortedArray: IntArray,
        leftBoundOfArrayToPaste: Int = 0,
        middle: Int,
        newMiddle: Int,
        temporaryArray: IntArray,
        numberOfThreads: Int = 1
    )

    fun IntArray.mergeSortingMultiThread(
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
            if (numberOfThreads <= 1) {
                this.mergeSortingMultiThread(
                    MergingPart(sortingPart.leftBound, middle),
                    temporaryArray, 0
                )
                this.mergeSortingMultiThread(
                    MergingPart(middle + 1, sortingPart.rightBound),
                    temporaryArray,
                    newMiddle + 1
                )
            } else {
                runParallelMergeSorting(
                    sortingPart,
                    sortedArray,
                    leftBoundOfArrayToPaste,
                    middle,
                    newMiddle,
                    temporaryArray, numberOfThreads
                )
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

    fun IntArray.mergeMultiThread(
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
        if (numberOfThreads > 1) {
            runParallelMerging(
                firstPart,
                middleOfFirstPart,
                secondPart,
                middleOfSecondPart,
                arrayMergeTo,
                leftBoundOfArrayToPaste,
                middleOfArrayToPaste,
                numberOfThreads
            )
        } else {
            this.mergeMultiThread(
                MergingPart(firstPart.leftBound, middleOfFirstPart - 1),
                MergingPart(secondPart.leftBound, middleOfSecondPart - 1),
                arrayMergeTo,
                leftBoundOfArrayToPaste,
            )
            this.mergeMultiThread(
                MergingPart(middleOfFirstPart + 1, firstPart.rightBound),
                MergingPart(middleOfSecondPart, secondPart.rightBound),
                arrayMergeTo,
                middleOfArrayToPaste + 1,
            )
        }
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
}
