package homework3

import com.charleskorn.kaml.Yaml
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Class storing config for test generator.
 * @param packageName Package name where the test will be created.
 * @param className The name of the class to be tested.
 * @param functions List of functions for which to create a test
 */
@Serializable
data class Config(
    @SerialName("package name")
    val packageName: String,
    @SerialName("class name")
    val className: String,
    val functions: List<FunctionsName>
)

/**
 * Class storing function name.
 * @param name Name of the function.
 */
@Serializable
data class FunctionsName(val name: String)

/**
 * Function to decode yaml text to config.
 * @param yamlText Yaml text data.
 * @return Config object.
 */
fun getConfigFromYaml(yamlText: String) = Yaml.default.decodeFromString(Config.serializer(), yamlText)
