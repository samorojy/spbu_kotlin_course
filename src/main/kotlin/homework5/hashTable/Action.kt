package homework5.hashTable

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import utils.getStringData
import java.io.File

val availableActions = listOf(Add(), Remove(), Find(), PrintStatistic(), FillFromFile(), ChangHashFunction())

interface Action {
    val name: String
    fun doAction(hashTable: HashTable<String, String>)
}

class Add : Action {
    override val name = "Add"
    override fun doAction(hashTable: HashTable<String, String>) {
        val key = getStringData("key")
        val value = getStringData("value")
        hashTable.add(key, value)
    }
}

class Remove : Action {
    override val name = "Remove"
    override fun doAction(hashTable: HashTable<String, String>) {
        val key = getStringData("key")
        hashTable.remove(key)
    }
}

class Find : Action {
    override val name = "Find"
    override fun doAction(hashTable: HashTable<String, String>) {
        val key = getStringData("key")
        println(hashTable[key] ?: "No items with this key were found")
    }
}

class PrintStatistic : Action {
    override val name = "Print statistic"
    override fun doAction(hashTable: HashTable<String, String>) {
        hashTable.getStatistic().forEach { println("${it.key}: ${it.value}") }
    }
}

class FillFromFile : Action {
    override val name = "Fill from file"
    override fun doAction(hashTable: HashTable<String, String>) {
        println("Enter path to file: ")
        val filePath = java.util.Scanner(System.`in`).next()
        val inputText = File(filePath).readText()
        val mapFromFile = Json.decodeFromString<HashMap<String, String>>(inputText)
        mapFromFile.forEach { hashTable.add(it.key, it.value) }
    }
}

class ChangHashFunction : Action {
    override val name = "Change hash function"
    override fun doAction(hashTable: HashTable<String, String>) {
        val scan = java.util.Scanner(System.`in`)
        val availableHashFunctions = listOf("DefaultHash", "OwnHash")
        println("Hash functions: " + availableHashFunctions.joinToString())
        when (scan.next()) {
            "DefaultHash" -> hashTable.changeHashFunction(DefaultHashFunction)
            "OwnHash" -> hashTable.changeHashFunction(OwnHashFunction)
            else -> println("Incorrect entered name of hash function")
        }
    }
}
