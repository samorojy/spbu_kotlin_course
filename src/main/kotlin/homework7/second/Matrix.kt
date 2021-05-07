package homework7.second

import java.lang.IllegalArgumentException
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

data class Matrix(private val matrix: Array<IntArray>) {

    @Suppress("ComplexCondition")
    private fun isCorrectMatrices(other: Matrix) {

        if (matrix.size != matrix[0].size &&
            other.matrix.size != other.matrix[0].size &&
            matrix[0].isEmpty() || other.matrix[0].isEmpty()
        ) throw IllegalArgumentException("The matrices has to be square and not empty")

        if (matrix[0].size != other.matrix[0].size
        ) throw IllegalArgumentException("The matrices must be the same size")
    }

    operator fun times(other: Matrix): Matrix {
        isCorrectMatrices(other)

        val resultArray = Array(matrix.size) { IntArray(matrix.size) { 0 } }
        runBlocking {
            for (i in matrix.indices) {
                for (j in matrix.indices) {
                    for (k in matrix.indices) {
                        launch {
                            resultArray[i][j] += matrix[i][k] * other.matrix[k][j]
                        }
                    }
                }
            }
        }
        return Matrix(resultArray)
    }

    override fun toString(): String {
        var string = ""
        matrix.forEach { string += it.joinToString(postfix = "\n") }
        return string
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Matrix

        if (!matrix.contentDeepEquals(other.matrix)) return false

        return true
    }

    override fun hashCode(): Int {
        return matrix.contentDeepHashCode()
    }
}
