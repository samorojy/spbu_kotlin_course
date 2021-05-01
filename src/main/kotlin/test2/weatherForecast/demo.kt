package test2.weatherForecast

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

fun main() {

    val apiKey = System.getenv("openWeatherApi")

    val cities = Json.decodeFromString<List<String>>(
        File(
            "src/main/resources/test2.weatherForecast/cities.json"
        ).readText()
    )
    cities.forEach {
        RequestExecutor().executeCall(RequestCreator().getApi().getWeatherData("metric", it, apiKey))
    }
}
