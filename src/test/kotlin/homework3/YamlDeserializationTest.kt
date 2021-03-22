package homework3

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class YamlDeserializationTest {

    companion object {
        @JvmStatic
        fun inputData(): List<Arguments> = listOf(
            Arguments.of(
                Config(
                    "homework3",
                    "PerformedCommandStorage",
                    listOf(
                        FunctionsName("forwardApply"),
                        FunctionsName("backwardApply")
                    )
                ), YamlDeserializationTest::class.java.getResource("ConfigTest1.yaml").readText()
            ),
            Arguments.of(
                Config(
                    "homework3",
                    "CommandStorage",
                    listOf(
                        FunctionsName("forwardApply")
                    )
                ), YamlDeserializationTest::class.java.getResource("ConfigTest2.yaml").readText()
            )
        )
    }

    @MethodSource("inputData")
    @ParameterizedTest(name = "test{index}, {1}")
    fun getConfigFromYamlTest(expected: Config, input: String) {
        assertEquals(expected, getConfigFromYaml(input))
    }
}
