package homework3

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class TestGeneratorTest {

    companion object {
        @JvmStatic
        fun inputData(): List<Arguments> = listOf(
            Arguments.of(
                TestGeneratorTest::class.java.getResource("GeneratorTest1.kt").readText().replace("\r\n", "\n"),
                Config(
                    "homework3",
                    "PerformedCommandStorage",
                    listOf(
                        FunctionsName("forwardApply"),
                        FunctionsName("backwardApply")
                    )
                )
            ),
            Arguments.of(
                TestGeneratorTest::class.java.getResource("GeneratorTest2.kt").readText().replace("\r\n", "\n"),
                Config(
                    "homework3",
                    "CommandStorage",
                    listOf(
                        FunctionsName("forwardApply")
                    )
                )
            )
        )
    }

    @MethodSource("inputData")
    @ParameterizedTest(name = "test{index}, {1}")
    fun getKotlinFile(expected: String, input: Config) {
        assertEquals(expected, TestGenerator(input).kotlinFile.toString())
    }
}
