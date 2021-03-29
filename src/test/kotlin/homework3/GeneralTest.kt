package homework3

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.File
import java.nio.file.Path
import kotlin.io.path.readText

internal class GeneralTest {

    companion object {
        @JvmStatic
        fun inputData(): List<Arguments> = listOf(
            Arguments.of(
                "test1", "test1/JetBrainsInternshipTest.kt"
            ),
            Arguments.of(
                "test2", "test2/AvlTreeTest.kt"
            )
        )
    }

    @MethodSource("inputData")
    @ParameterizedTest(name = "test{index}, {1}")
    fun getKotlinFile(directoryName: String, tempFileName: String, @TempDir tempDirectory: Path) {
        val expectedCode = javaClass.getResource("generalTest/$directoryName/expected.kt").readText()
        val config = javaClass.getResource("generalTest/$directoryName/config.yaml").path
        val file = tempDirectory.resolve(tempFileName)
        TestGenerator.generate(config, tempDirectory.toString())
        assertEquals(expectedCode, file.toFile().readText().replace("\r\n", "\n"))
    }
}
