package homework5

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.lang.IllegalArgumentException

internal class ParseTreeTest {

    companion object {
        @JvmStatic
        fun inputData(): List<Arguments> = listOf(
            Arguments.of(14, "expected1.txt"),
            Arguments.of(4, "expected2.txt"),
            Arguments.of(261, "expected3.txt")
        )
    }

    @MethodSource("inputData")
    @ParameterizedTest(name = "test{index}, {1}")
    fun getValueTest(expectedValue: Int, fileName: String) {
        val tree = ParseTree(javaClass.getResource(fileName).path.toString())
        assertEquals(tree.getValue(), expectedValue)
    }

    @Test
    fun getStringCorrect() {
        val tree = ParseTree(javaClass.getResource("expected1.txt").path.toString())
        val expected = javaClass.getResource("correct.txt").readText().replace("\r\n", "\n")
        assertEquals(expected, tree.getString())
    }

    @Test
    fun incorrectExpression() {
        assertThrows(IllegalArgumentException("Incorrect operator or Incorrect expresion")::class.java) {
            ParseTree(javaClass.getResource("incorrect.txt").path.toString())
        }
    }
}
