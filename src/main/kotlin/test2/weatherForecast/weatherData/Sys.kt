package test2.weatherForecast.weatherData

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Sys(
    @SerialName("type") val type: Int,
    @SerialName("id") val id: Int,
    @SerialName("message") val message: Double,
    @SerialName("country") val country: String,
    @SerialName("sunrise") val sunrise: Int,
    @SerialName("sunset") val sunset: Int
)
