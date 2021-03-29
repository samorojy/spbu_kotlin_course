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
                ), "ConfigTest1.yaml"
            ),
            Arguments.of(
                Config(
                    "homework3",
                    "CommandStorage",
                    listOf(
                        FunctionsName("forwardApply")
                    )
                ), "ConfigTest2.yaml"
            )
        )
    }

    @MethodSource("inputData")
    @ParameterizedTest(name = "test{index}, {1}")
    fun getConfigFromYamlTest(expected: Config, inputName: String) {
        assertEquals(expected, Config.getFromYaml(javaClass.getResource(inputName).readText()))
    }
}
