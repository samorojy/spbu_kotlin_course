package utils

fun getStringData(message: String): String {
    val scan = java.util.Scanner(System.`in`)
    println("Enter $message: ")
    return scan.next()
}
