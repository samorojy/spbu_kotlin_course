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
    print("Please enter a natural number: ")
    var input: Int = readLine()!!.toInt()
    while (input < 0) {
        print("Please enter a number greater than 0: ")
        input = readLine()!!.toInt()
    }
    return input
}

fun main() {
    val number = getUserInput()
    print("Iterative: factorial of $number is ${getFactorialIterative(number)}\n")
    print("Recursive: factorial of $number is ${getFactorialRecursive(number)}")
}
