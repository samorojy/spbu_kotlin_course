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
                "GeneratorTest1.kt",
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
                "GeneratorTest2.kt",
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
    fun getKotlinFile(expectedName: String, input: Config) {
        assertEquals(
            javaClass.getResource(expectedName).readText().replace("\r\n", "\n"),
            TestGenerator(input).kotlinFile.toString()
        )
    }
}
