package exam


import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class BubbleSorterKtTest {

    companion object {
        @JvmStatic
        fun intData(): List<Arguments> = listOf(
            Arguments.of(
                listOf(173, 225, 229, 254, 386, 418, 424, 429, 466, 473, 854, 857, 873, 892, 904, 913),
                listOf(424, 229, 466, 225, 913, 904, 254, 854, 873, 429, 418, 386, 473, 173, 892, 857)
            ),
            Arguments.of(
                listOf(170, 207, 299, 392, 393, 438, 460, 566, 633, 742, 749, 776, 824, 848, 863),
                listOf(566, 207, 749, 299, 170, 438, 742, 776, 848, 824, 863, 460, 633, 392, 393)
            ),
            Arguments.of(
                listOf(133, 261, 267, 297, 310, 413, 416, 485, 505, 527, 636, 652, 735, 750, 933, 937),
                listOf(505, 750, 261, 133, 485, 937, 652, 310, 416, 527, 267, 297, 413, 735, 636, 933)
            ),
            Arguments.of(
                listOf(-92, -88, -63, -33, -27, -22, -5, -2, 5, 7, 12, 15, 39, 54, 87, 99),
                listOf(99, -27, 12, -92, -5, 87, 39, 54, 7, -88, 5, 15, -63, -22, -2, -33)
            ),
            Arguments.of(
                listOf(173, 225, 229, 254, 386, 418, 424, 429, 466, 473, 854, 857, 873, 892, 904, 913),
                setOf(424, 229, 466, 225, 913, 904, 254, 854, 873, 429, 418, 386, 473, 173, 892, 857)
            ),
            Arguments.of(
                listOf(170, 207, 299, 392, 393, 438, 460, 566, 633, 742, 749, 776, 824, 848, 863),
                setOf(566, 207, 749, 299, 170, 438, 742, 776, 848, 824, 863, 460, 633, 392, 393)
            ),
            Arguments.of(
                listOf(133, 261, 267, 297, 310, 413, 416, 485, 505, 527, 636, 652, 735, 750, 933, 937),
                setOf(505, 750, 261, 133, 485, 937, 652, 310, 416, 527, 267, 297, 413, 735, 636, 933)
            ),
            Arguments.of(
                listOf(-92, -88, -63, -33, -27, -22, -5, -2, 5, 7, 12, 15, 39, 54, 87, 99),
                setOf(99, -27, 12, -92, -5, 87, 39, 54, 7, -88, 5, 15, -63, -22, -2, -33)
            )
        )

        @JvmStatic
        fun floatData(): List<Arguments> = listOf(
            Arguments.of(
                listOf(-1.521f, 1.115f, 1.924f, 14.5f, 100.01f),
                listOf(1.115f, 1.924f, -1.521f, 100.01f, 14.5f)
            ),
            Arguments.of(
                listOf(-1050.1f, 567.151f, 1050.0f, 2411.015f, 98715.016f),
                listOf(-1050.10f, 1050.0f, 567.151f, 98715.0159f, 2411.015f)
            ),
            Arguments.of(
                listOf(-1.521f, 1.115f, 1.924f, 14.5f, 100.01f),
                setOf(1.115f, 1.924f, -1.521f, 100.01f, 14.5f)
            ),
            Arguments.of(
                listOf(-1050.1f, 567.151f, 1050.0f, 2411.015f, 98715.016f),
                setOf(-1050.10f, 1050.0f, 567.151f, 98715.0159f, 2411.015f)
            )
        )

        @JvmStatic
        fun stringData(): List<Arguments> = listOf(
            Arguments.of(
                listOf("Egor", "Alena", "String", "Letter", "Bubble", "Kotlin", "Timofey", "Anastasia"),
                listOf("String", "Letter", "Egor", "Anastasia", "Alena", "Timofey", "Bubble", "Kotlin")
            ),
            Arguments.of(
                listOf("fafaffa", "AFIFUAUFUFUA", "AFOOFFOAIFKVMMVAK", "AFUFUUAFYFYAFNVBBV", "AFJAFHAUVJBNNBMAKJ"),
                listOf("AFOOFFOAIFKVMMVAK", "AFUFUUAFYFYAFNVBBV", "fafaffa", "AFIFUAUFUFUA", "AFJAFHAUVJBNNBMAKJ")
            ),
            Arguments.of(
                listOf("Math", "French", "German", "Physics", "Russian", "British", "Prussian", "Brazilian"),
                listOf("Math", "Physics", "Russian", "British", "French", "German", "Brazilian", "Prussian")
            ),
            Arguments.of(
                listOf("Egor", "Alena", "String", "Letter", "Bubble", "Kotlin", "Timofey", "Anastasia"),
                setOf("String", "Letter", "Egor", "Anastasia", "Alena", "Timofey", "Bubble", "Kotlin")
            ),
            Arguments.of(
                listOf("fafaffa", "AFIFUAUFUFUA", "AFOOFFOAIFKVMMVAK", "AFUFUUAFYFYAFNVBBV", "AFJAFHAUVJBNNBMAKJ"),
                setOf("AFOOFFOAIFKVMMVAK", "AFUFUUAFYFYAFNVBBV", "fafaffa", "AFIFUAUFUFUA", "AFJAFHAUVJBNNBMAKJ")
            ),
            Arguments.of(
                listOf("Math", "French", "German", "Physics", "Russian", "British", "Prussian", "Brazilian"),
                setOf("Math", "Physics", "Russian", "British", "French", "German", "Brazilian", "Prussian")
            )
        )
    }

    @MethodSource("intData")
    @ParameterizedTest(name = "testIntData{index}, {1}")
    fun intDataTest(expectedList: List<Int>, inputIterable: Iterable<Int>) {
        assertEquals(expectedList, inputIterable.bubbleSort(naturalOrder()))
    }

    @MethodSource("floatData")
    @ParameterizedTest(name = "testFloatData{index}, {1}")
    fun floatDataTest(expectedList: List<Float>, inputIterable: Iterable<Float>) {
        assertEquals(expectedList, inputIterable.bubbleSort(naturalOrder()))
    }

    @MethodSource("stringData")
    @ParameterizedTest(name = "testStringData{index}, {1}")
    fun stringDataTest(expectedList: List<String>, inputIterable: Iterable<String>) {
        assertEquals(expectedList, inputIterable.bubbleSort(compareBy { it.length }))
    }
}