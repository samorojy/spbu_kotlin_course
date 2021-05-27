package homework7.second

import java.lang.IllegalArgumentException
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

data class Matrix(private val matrix: Array<IntArray>) {

    init {
        val matrixWidth = matrix[0].size
        matrix.forEach { require(it.size == matrixWidth) { IllegalArgumentException("Incorrect matrix") } }
    }

    private fun Matrix.isMatrixSquare() {
        require(this@Matrix.matrix.size == this@Matrix.matrix[0].size) {
            IllegalArgumentException("The matrix has to be square")
        }
    }

    private fun isCorrectMatrices(other: Matrix) {

        require(matrix[0].isNotEmpty() && other.matrix[0].isNotEmpty()) {
            IllegalArgumentException("The matrices has to be not empty")
        }

        require(matrix[0].size == other.matrix[0].size) {
            IllegalArgumentException("The matrices must be the same size")
        }

        this.isMatrixSquare()
        other.isMatrixSquare()
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

    override fun toString(): String =
        matrix.joinToString("\n") { it.joinToString() }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Matrix

        return matrix.contentDeepEquals(other.matrix)
    }

    override fun hashCode(): Int {
        return matrix.contentDeepHashCode()
    }
}
