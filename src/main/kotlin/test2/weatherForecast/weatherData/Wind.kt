package test2.weatherForecast.weatherData

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Wind(
    @SerialName("speed") val speed: Double,
    @SerialName("deg") val deg: Int
)
