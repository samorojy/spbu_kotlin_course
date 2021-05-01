package test2.weatherForecast

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import test2.weatherForecast.weatherData.WeatherData

interface WeatherApiInterface {
    @GET("weather?")
    fun getWeatherData(
        @Query("units") units: String,
        @Query("q") cityName: String,
        @Query("appid") appId: String
    ): Call<WeatherData>
}
