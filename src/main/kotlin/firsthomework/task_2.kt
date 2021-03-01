package firsthomework

fun String.getNumberOfOccurrence(searchedString: String): Int {
    if (kotlin.math.min(this.length, searchedString.length) == 0 || this.length < searchedString.length) {
        return 0
    }
    return this.windowed(searchedString.length) { if (it == searchedString) 1 else 0 }.sum()
}

fun main() {
    val input = java.util.Scanner(System.`in`)
    println("Enter the search string: ")
    val firstString: String = input.nextLine()
    println("Enter the string to be found: ")
    val secondString: String = input.nextLine()
    println("Number of occurrence is ${firstString.getNumberOfOccurrence(secondString)}")
}
