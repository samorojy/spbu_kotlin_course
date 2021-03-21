package homework3

import com.charleskorn.kaml.Yaml
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Config(
    @SerialName("package name")
    val packageName: String,
    @SerialName("class name")
    val className: String,
    val functions: List<FunctionsName>
)

@Serializable
data class FunctionsName(val name: String)

fun getConfigFromYaml(yamlText: String) = Yaml.default.decodeFromString(Config.serializer(), yamlText)
