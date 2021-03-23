package homework3

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ClassName

import java.io.File

/**
 * Generates a test class with the required functions.
 * @param config Config object.
 * @property kotlinFile File kotlin format
 */
class TestGenerator(private val config: Config) {
    private fun createFunction(function: FunctionsName) = FunSpec.builder(function.name)
        .addAnnotation(ClassName("org.junit.jupiter.api", "Test"))
        .build()

    private val createClass = TypeSpec.classBuilder(config.className + "Test")
        .addModifiers(KModifier.INTERNAL)
        .addFunctions(config.functions.map { createFunction(it) })
        .build()

    val kotlinFile: FileSpec = FileSpec.builder(config.packageName, config.className + "Test")
        .addType(createClass)
        .build()

    companion object {
        /**
         * Function which generate tests and put it on Kt file.
         * @param inputFilePath Path to input Yaml format file.
         * @param outputFilePath Path to output Kt format file.
         */
        fun generate(inputFilePath: String, outputFilePath: String) {
            val yamlText: String = File(inputFilePath).readText()
            val config = Config.getFromYaml(yamlText)
            val file = TestGenerator(config).kotlinFile
            file.writeTo(File(outputFilePath))
        }
    }
}

