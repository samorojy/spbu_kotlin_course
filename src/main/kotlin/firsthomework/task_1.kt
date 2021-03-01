package firsthomework

fun getFactorialIterative(number: Int): Int {
    var factorial = 1
    for (factor in 2..number) {
        factorial *= factor
    }
    return factorial
}

fun getFactorialRecursive(number: Int): Int {
    if (number == 0) {
        return 1
    }
    return number * getFactorialRecursive(number - 1)
}

fun getUserInput(): Int {
    val scan = java.util.Scanner(System.`in`)
    print("Please enter a natural number: ")
    if (!scan.hasNextInt()) {
        error("ERROR! String cannot be converted to INT")
    }
    val input: Int = scan.nextInt()
    if (input < 0) {
        error("ERROR! Number must be greater than or equal to 0")
    }
    return input
}

fun main() {
    val number: Int = getUserInput()
    print("Iterative: factorial of $number is ${getFactorialIterative(number)}\n")
    print("Recursive: factorial of $number is ${getFactorialRecursive(number)}")
}
