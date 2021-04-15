package homework5.hashTable

fun userInterface(hashtable: HashTable<String, String>) {
    var isNotEndFromLoop = true
    while (isNotEndFromLoop) {
        println(
            "\nEnter the name of function to use it. Names:\n" +
                    "\t${availableActions.joinToString { it.name }}\n" +
                    "\texit - to exit from interface\n" +
                    "Enter action name to execute: "
        )
        val scan = java.util.Scanner(System.`in`)
        val command = scan.nextLine()
        if (command == "exit") {
            isNotEndFromLoop = false
        } else {
            availableActions.find { it.name == command }?.doAction(hashtable)
                ?: println("Incorrect name of Action")
        }
    }
}

fun main() {
    val table = HashTable<String, String>(DefaultHashFunction)
    userInterface(table)
}
