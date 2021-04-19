package homework7.first

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class MergeCoroutineSortKtTest {

    @Test
    fun mergeSortingMainMultiCoroutines() {
        for (i in 1..10) {
            val arrayToSort = intArrayOf(7, 4, 5, 6, 3, 22, 16, 31, 17)
            arrayToSort.mergeSortingMainMultiCoroutines(i)
            assertEquals(intArrayOf(3, 4, 5, 6, 7, 16, 17, 22, 31).toList(), arrayToSort.toList())
        }
    }
}
