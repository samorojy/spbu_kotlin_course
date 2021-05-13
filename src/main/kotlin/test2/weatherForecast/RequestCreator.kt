package test2.weatherForecast

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RequestCreator {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(WEB_SERVICE_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiInterface: WeatherApiInterface = retrofit.create(WeatherApiInterface::class.java)

    fun getApi(): WeatherApiInterface {
        return apiInterface
    }

    companion object {
        const val WEB_SERVICE_BASE_URL = "http://api.openweathermap.org/data/2.5/"
    }
}
