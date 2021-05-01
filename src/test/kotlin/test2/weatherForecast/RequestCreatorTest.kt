package test2.weatherForecast

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class RequestCreatorTest {

    @Test
    fun createRequest() {
        val apiKey = System.getenv("openWeatherApi")
        val weatherData = RequestCreator()
            .getApi()
            .getWeatherData("metric", "Moscow", apiKey)
            .execute()
            .body()
        assertEquals("Moscow", weatherData?.name)
    }
}
