package firsthomework

fun getNumberOfOccurrence(firstString: String, secondString: String): Int {
    if (kotlin.math.min(firstString.length, secondString.length) == 0 || firstString.length < secondString.length) {
        return 0
    }
    return firstString.windowed(secondString.length) {
        if (it.equals(secondString))
            1
        else
            0
    }.sum()
}

fun main() {
    val input = java.util.Scanner(System.`in`)
    println("Enter first string: ")
    val firstString: String = input.nextLine()
    println("Enter second string: ")
    val secondString: String = input.nextLine()
    println("Number of occurrence is ${getNumberOfOccurrence(firstString, secondString)}")

}
