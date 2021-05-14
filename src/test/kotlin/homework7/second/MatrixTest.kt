package homework7.second

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class MatrixTest {

    companion object {

        @JvmStatic
        fun getIncorrectData(): List<Arguments> = listOf(
            Arguments.of(
                Matrix(
                    arrayOf(
                        intArrayOf()
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf()
                    )
                )
            ),
            Arguments.of(
                Matrix(arrayOf(intArrayOf(1, 2), intArrayOf(3, 4))),
                Matrix(arrayOf(intArrayOf(1, 2, 3), intArrayOf(5, 6, 7)))
            )
        )

        @JvmStatic
        fun getCorrectData(): List<Arguments> = listOf(
            Arguments.of(
                Matrix(
                    arrayOf(
                        intArrayOf(7, 1, -3),
                        intArrayOf(9, 23, -9),
                        intArrayOf(3, 6, 5)
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(-1, 2, -5),
                        intArrayOf(3, 4, 1),
                        intArrayOf(0, 1, 2)
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(-1, 2, -5),
                        intArrayOf(3, 4, 1),
                        intArrayOf(0, 1, 2)
                    )
                )
            ),
            Arguments.of(
                Matrix(
                    arrayOf(
                        intArrayOf(1, 5),
                        intArrayOf(-2, 10),
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(1, 2),
                        intArrayOf(3, 1),
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(-1, 3),
                        intArrayOf(1, 1),
                    )
                )
            ),
            Arguments.of(
                Matrix(
                    arrayOf(
                        intArrayOf(46, 8, 7),
                        intArrayOf(26, 1, -5),
                        intArrayOf(-49, -2, 13)
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(9, 3, 5),
                        intArrayOf(2, 0, 3),
                        intArrayOf(0, 1, -6)
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(1, -1, -1),
                        intArrayOf(-1, 4, 7),
                        intArrayOf(8, 1, -1)
                    )
                )
            ),
            Arguments.of(
                Matrix(
                    arrayOf(
                        intArrayOf(344, 601, 4280),
                        intArrayOf(322, -122, -28),
                        intArrayOf(-13, 52, 351)
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(8, 159, 5),
                        intArrayOf(25, 0, 3),
                        intArrayOf(0, 13, 0)
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(1, -5, -1),
                        intArrayOf(-1, 4, 27),
                        intArrayOf(99, 1, -1)
                    )
                )
            ),
            Arguments.of(
                Matrix(
                    arrayOf(
                        intArrayOf(4522,727,157,3653),
                        intArrayOf(6150,997,137,2284),
                        intArrayOf(16191,2799,-179,-2824),
                        intArrayOf(2124,439,7,3630)
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(30, 15, 0, -23),
                        intArrayOf(40, 10, -1, 12),
                        intArrayOf(98, -9, 21, 34),
                        intArrayOf(12, 14, 15, -86)
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(157, 25, 0, -6),
                        intArrayOf(-11, 0, 12, 254),
                        intArrayOf(32, 15, -5, 4),
                        intArrayOf(1, 1, 1, -1)
                    )
                )
            )
        )
    }

    @MethodSource("getIncorrectData")
    @ParameterizedTest(name = "test{index}, {1}")
    fun matrixTimesIncorrectDataTest(firstMatrix: Matrix, secondMatrix: Matrix) {
        assertThrows<IllegalArgumentException> { firstMatrix * secondMatrix }
    }

    @MethodSource("getCorrectData")
    @ParameterizedTest(name = "test{index}, {1}")
    fun matrixTimesCorrectDataTest(expectedMatrix: Matrix, firstMatrix: Matrix, secondMatrix: Matrix) {
        assertEquals(expectedMatrix, firstMatrix * secondMatrix)
    }
}
