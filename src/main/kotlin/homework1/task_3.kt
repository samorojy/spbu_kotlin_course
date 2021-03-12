package homework1

import action.CommandStorage
import action.InsertAtStart
import action.InsertAtEnd
import action.Move

const val NUMBER0 = 0
const val NUMBER1 = 1
const val NUMBER2 = 2
const val NUMBER3 = 3
const val NUMBER4 = 4
const val NUMBER5 = 5
const val NAME = "ActionList.json"

fun main() {
    val storage = CommandStorage()
    InsertAtStart(NUMBER1).doAction(storage)
    InsertAtStart(NUMBER2).doAction(storage)
    InsertAtStart(NUMBER3).doAction(storage)
    InsertAtStart(NUMBER4).doAction(storage)
    storage.numberList.forEach { print(it) }
    println(" After InsertAtStart")
    storage.undoLastAction()
    storage.numberList.forEach { print(it) }
    println(" After Undo")
    InsertAtEnd(NUMBER5).doAction(storage)
    storage.numberList.forEach { print(it) }
    println(" After Insert At end")
    Move(NUMBER0, NUMBER3).doAction(storage)
    storage.numberList.forEach { print(it) }
    println(" After move")
    storage.undoLastAction()
    storage.numberList.forEach { print(it) }
    println(" After undo move")
    storage.serializeToFile(NAME)
    storage.deserializeFromFile(NAME)
}
