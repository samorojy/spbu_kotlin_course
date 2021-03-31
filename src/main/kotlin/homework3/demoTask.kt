package homework3

fun main() {
    val scanner = java.util.Scanner(System.`in`)
    println("Please enter path to input file")
    val inputFileName = scanner.nextLine()
    println("Please enter path to output file")
    val outputFileName = scanner.nextLine()
    TestGenerator.generate(inputFileName, outputFileName)
}
