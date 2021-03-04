package firsthomework

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

fun main() {
    val storage = CommandStorage()
    storage.doAction(InsertAtStart(NUMBER1, storage))
    storage.doAction(InsertAtStart(NUMBER2, storage))
    storage.doAction(InsertAtStart(NUMBER3, storage))
    storage.doAction(InsertAtStart(NUMBER4, storage))
    storage.numberList.forEach { print(it) }
    println(" After InsertAtStart")
    storage.undoLastAction()
    storage.numberList.forEach { print(it) }
    println(" After Undo")
    storage.doAction(InsertAtEnd(NUMBER5, storage))
    storage.numberList.forEach { print(it) }
    println(" After Insert At end")
    storage.doAction(Move(NUMBER0, NUMBER3, storage))
    storage.numberList.forEach { print(it) }
    println(" After move")
    storage.undoLastAction()
    storage.numberList.forEach { print(it) }
    println(" After undo move")
}
