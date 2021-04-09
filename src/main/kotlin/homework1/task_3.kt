@file:Suppress("MagicNumber")

package homework1

import action.CommandStorage
import action.CommandStorage.IntJson.deserializeFromFile
import action.CommandStorage.IntJson.serializeToFile
import action.InsertAtStart
import action.InsertAtEnd
import action.Move

fun main() {
    val fileName = "src/main/resources/homework1/ActionList.json"
    val numbers: IntArray = intArrayOf(0, 1, 2, 3, 4, 5)
    val (startIndex, endIndex) = Pair(0, 3)
    val storage = CommandStorage<Int>()
    InsertAtStart(numbers[1]).doAction(storage)
    InsertAtStart(numbers[2]).doAction(storage)
    InsertAtStart(numbers[3]).doAction(storage)
    InsertAtStart(numbers[4]).doAction(storage)
    storage.numberList.forEach { print(it) }
    println(" After InsertAtStart")
    storage.undoLastAction()
    storage.numberList.forEach { print(it) }
    println(" After Undo")
    InsertAtEnd(numbers[5]).doAction(storage)
    storage.numberList.forEach { print(it) }
    println(" After Insert At end")
    Move<Int>(startIndex, endIndex).doAction(storage)
    storage.numberList.forEach { print(it) }
    println(" After move")
    storage.undoLastAction()
    storage.numberList.forEach { print(it) }
    println(" After undo move")
    storage.serializeToFile(fileName)
    storage.deserializeFromFile(fileName)
}
