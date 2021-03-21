package homework3

import com.squareup.kotlinpoet.*
import java.io.File

class TestGenerator(private val config: Config) {
    private val testAnnotation = ClassName("org.junit.jupiter.api", "Test")
    private val name: String = config.className + "Test"

    private fun createFunction(function: FunctionsName) = FunSpec.builder(function.name)
        .addAnnotation(testAnnotation)
        .build()

    private val createClass = TypeSpec.classBuilder(name)
        .addModifiers(KModifier.INTERNAL)
        .addFunctions(config.functions.map { createFunction(it) })
        .build()

    val kotlinFile: FileSpec
        get() = FileSpec.builder(config.packageName, name)
            .addType(createClass)
            .build()
}

fun generateTest(inputFilePath: String, outputFilePath: String) {
    val yamlText: String = File(inputFilePath).readText()
    val config = getConfigFromYaml(yamlText)
    val file = TestGenerator(config).kotlinFile
    file.writeTo(File(outputFilePath))
}
