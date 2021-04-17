package homework6

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MergeSortTest {
    @Test
    fun mergeSortingSingleThread() {
        val arrayToSort = intArrayOf(5, 10, 1, 4, 7)
        arrayToSort.mergeSortingSingleThread()
        assertEquals(intArrayOf(1, 4, 5, 7, 10).toList(), arrayToSort.toList())
    }

    @Test
    fun mergeSortingMultiThread() {
        for (i in 1..10) {
            val arrayToSort = intArrayOf(7, 4, 5, 6, 3, 22, 16, 31, 17)
            arrayToSort.mergeSortingMainMultiThread(i)
            assertEquals(intArrayOf(3, 4, 5, 6, 7, 16, 17, 22, 31).toList(), arrayToSort.toList())
        }
    }

    @Test
    fun merge() {
        val arrayToMerge = intArrayOf(5, 6, 7, 8, 9, 1, 2, 3, 4)
        arrayToMerge.merge(0, 5, arrayToMerge.lastIndex + 1)
        assertEquals(intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9).toList(), arrayToMerge.toList())
    }
}