@file:Suppress("MagicNumber")

package homework1

import action.CommandStorage
import action.InsertAtStart
import action.InsertAtEnd
import action.Move

fun main() {
    val fileName = "src/main/resources/homework1/ActionList.json"
    val numbers: IntArray = intArrayOf(0, 1, 2, 3, 4, 5)
    val (startIndex, endIndex) = Pair(0, 3)
    val numberList: MutableList<Int> = mutableListOf()
    val storage = CommandStorage<Int>()
    InsertAtStart(numbers[1]).doAction(numberList, storage)
    InsertAtStart(numbers[2]).doAction(numberList, storage)
    InsertAtStart(numbers[3]).doAction(numberList, storage)
    InsertAtStart(numbers[4]).doAction(numberList, storage)
    numberList.forEach { print(it) }
    println(" After InsertAtStart")
    storage.undoLastAction(numberList)
    numberList.forEach { print(it) }
    println(" After Undo")
    InsertAtEnd(numbers[5]).doAction(numberList, storage)
    numberList.forEach { print(it) }
    println(" After Insert At end")
    Move<Int>(startIndex, endIndex).doAction(numberList, storage)
    numberList.forEach { print(it) }
    println(" After move")
    storage.undoLastAction(numberList)
    numberList.forEach { print(it) }
    println(" After undo move")
    storage.serializeToFile(fileName)
    storage.deserializeFromFile(numberList, fileName)
}
