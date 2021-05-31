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

    fun IntArray.binarySearch(valueToFind: Int, left: Int, right: Int): Int {
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
