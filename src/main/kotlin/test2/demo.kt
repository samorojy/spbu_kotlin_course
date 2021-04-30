package test2

import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Response
import retrofit2.http.GET
import java.net.URL
import retrofit2.http.POST


data class TemperatureData(var temp: String)
data class WeatherDataJson(var description: String)

data class WeatherData(
    var main: TemperatureData? = null,
    var weather: WeatherDataJson? = null
)

interface APIService {
    @get:GET("api.openweathermap.org/data/2.5/weather?id=2172797&appid=b334570486eb847c240ad3872a66c79c")
    fun load():
}

fun main() {

    @GET("api.openweathermap.org/data/2.5/weather?id=2172797&appid=b334570486eb847c240ad3872a66c79c")
    val weatherData: WeatherData

}
