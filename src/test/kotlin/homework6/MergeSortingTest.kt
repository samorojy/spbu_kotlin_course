package homework6

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class MergeSortingTest{

    companion object {
        @JvmStatic
        fun inputData(): List<Arguments> = listOf(
            Arguments.of(
                intArrayOf(1, 4, 5, 7, 10), intArrayOf(5, 10, 1, 4, 7)
            ),
            Arguments.of(
                intArrayOf(0, 1, 3, 15, 25, 29), intArrayOf(3, 15,25,  0, 1,29)
            ),
            Arguments.of(
                intArrayOf(4, 17, 22, 100, 9151, 15000), intArrayOf(100, 9151, 15000, 4, 17, 22)
            )
        )
    }

    @MethodSource("inputData")
    @ParameterizedTest(name = "test{index}, {1}")
    fun getKotlinFile(expectedArray: IntArray, arrayToSort: IntArray) {
        arrayToSort.merge(0, (arrayToSort.lastIndex + 1) / 2, arrayToSort.lastIndex + 1)
        assertEquals(expectedArray.toList(), arrayToSort.toList())
    }
}