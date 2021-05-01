package test2.weatherForecast

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import test2.weatherForecast.weatherData.WeatherData

class RequestExecutor {

    private fun printWeatherInfo(weatherData: WeatherData) {
        println(
            "City: ${weatherData.name}\n" +
                    "Temperature: ${weatherData.main.temp}\n" +
                    "Wind: ${weatherData.wind.speed}\n" +
                    "Pressure: ${weatherData.main.pressure}\n" +
                    "Description: ${weatherData.weather.first().description}\n\n"
        )
    }

    fun executeCall(call: Call<WeatherData>) {
        call.enqueue(
            object : Callback<WeatherData> {
                override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                    if (response.body() != null) {
                        printWeatherInfo(response.body()!!)
                    } else {
                        println("ERROR: ${response.code()}, Message: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                    throw(t)
                }
            }
        )
    }
}
