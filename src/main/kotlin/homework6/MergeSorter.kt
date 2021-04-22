@file:Suppress("LongParameterList")

package homework6

class MergeSorter {
    fun sort(arrayToSort: IntArray, numberOfThreads: Int) {
        if (arrayToSort.isEmpty()) return
        val temporaryArray = IntArray(arrayToSort.size) { 0 }
        arrayToSort.mergeSortingMultiThread(sortedArray = temporaryArray, numberOfThreads = numberOfThreads)
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

    private fun IntArray.mergeMultiThread(
        leftBoundFirstMergingPart: Int,
        rightBoundFirstMergingPart: Int,
        leftBoundSecondMergingPart: Int,
        rightBoundSecondMergingPart: Int,
        arrayMergeTo: IntArray,
        leftBoundOfArrayToPaste: Int,
        numberOfThreads: Int = 1
    ) {
        val firstMergingPartSize = rightBoundFirstMergingPart - leftBoundFirstMergingPart + 1
        val secondMergingPartSize = rightBoundSecondMergingPart - leftBoundSecondMergingPart + 1
        if (firstMergingPartSize < secondMergingPartSize) {
            this.mergeMultiThread(
                leftBoundSecondMergingPart,
                rightBoundSecondMergingPart,
                leftBoundFirstMergingPart,
                rightBoundFirstMergingPart,
                arrayMergeTo,
                leftBoundOfArrayToPaste
            )
            return
        }
        if (firstMergingPartSize == 0) {
            return
        }
        val middleOfFirstPart =
            (leftBoundFirstMergingPart + rightBoundFirstMergingPart) / 2
        val middleOfSecondPart =
            this.binarySearch(this[middleOfFirstPart], leftBoundSecondMergingPart, rightBoundSecondMergingPart)
        val sizeHalfPartFirst = middleOfFirstPart - leftBoundFirstMergingPart
        val sizeHalfPartSecond = middleOfSecondPart - leftBoundSecondMergingPart
        val middleOfArrayToPaste = leftBoundOfArrayToPaste + sizeHalfPartFirst + sizeHalfPartSecond

        arrayMergeTo[middleOfArrayToPaste] = this[middleOfFirstPart]
        val numberOfLeftThreads = numberOfThreads / 2
        val numberOfRightThreads = numberOfThreads - numberOfLeftThreads
        val leftThread =
            Thread {
                this.mergeMultiThread(
                    leftBoundFirstMergingPart,
                    middleOfFirstPart - 1,
                    leftBoundSecondMergingPart,
                    middleOfSecondPart - 1,
                    arrayMergeTo,
                    leftBoundOfArrayToPaste,
                    numberOfLeftThreads
                )
            }
        val rightThread =
            Thread {
                this.mergeMultiThread(
                    middleOfFirstPart + 1,
                    rightBoundFirstMergingPart,
                    middleOfSecondPart,
                    rightBoundSecondMergingPart,
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

    private fun IntArray.mergeSortingMultiThread(
        leftBoundFirstSortingPart: Int = 0,
        rightBoundSecondSortingPart: Int = this.lastIndex,
        sortedArray: IntArray,
        leftBoundOfArrayToPaste: Int = 0,
        numberOfThreads: Int = 1
    ) {
        val sortingPartSize = rightBoundSecondSortingPart - leftBoundFirstSortingPart + 1
        if (sortingPartSize == 1) {
            sortedArray[leftBoundOfArrayToPaste] = this[leftBoundFirstSortingPart]
        } else {
            val temporaryArray = IntArray(sortingPartSize) { 0 }
            val middle = (leftBoundFirstSortingPart + rightBoundSecondSortingPart) / 2
            val newMiddle = middle - leftBoundFirstSortingPart
            if (numberOfThreads == 1) {
                this.mergeSortingMultiThread(leftBoundFirstSortingPart, middle, temporaryArray, 0)
                this.mergeSortingMultiThread(middle + 1, rightBoundSecondSortingPart, temporaryArray, newMiddle + 1)
            } else {
                val numberOfLeftThreads = numberOfThreads / 2
                val numberOfRightThreads = numberOfThreads - numberOfLeftThreads
                val leftThread =
                    Thread {
                        this.mergeSortingMultiThread(
                            leftBoundFirstSortingPart, middle,
                            temporaryArray, 0,
                            numberOfLeftThreads
                        )
                    }
                val rightThread =
                    Thread {
                        this.mergeSortingMultiThread(
                            middle + 1, rightBoundSecondSortingPart,
                            temporaryArray, newMiddle + 1,
                            numberOfRightThreads
                        )
                    }
                leftThread.start()
                rightThread.start()
                leftThread.join()
                rightThread.join()
            }
            temporaryArray.mergeMultiThread(
                0,
                newMiddle,
                newMiddle + 1,
                sortingPartSize - 1,
                sortedArray,
                leftBoundOfArrayToPaste,
                numberOfThreads
            )
        }
    }
}
