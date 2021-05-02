package test2.weatherForecast

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class RequestCreatorTest {

    @Test
    fun createRequest() {
        val apiKey = System.getenv("OPENWEATHER_API")
        val weatherData = RequestCreator()
            .getApi()
            .getWeatherData("metric", "Moscow", apiKey)
            .execute()
            .body()
        println(apiKey)
        assertEquals("Moscow", "Moscow")
    }
}
