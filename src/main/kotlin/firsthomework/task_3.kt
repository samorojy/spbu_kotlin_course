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
    storage.actionList
    InsertAtStart(NUMBER1, storage).doAction()
    InsertAtStart(NUMBER2, storage).doAction()
    InsertAtStart(NUMBER3, storage).doAction()
    InsertAtStart(NUMBER4, storage).doAction()
    storage.numberList.forEach { println(it) }
    storage.undoLastAction()
    println("After Undo")
    storage.numberList.forEach { println(it) }
    InsertAtEnd(NUMBER5, storage).doAction()
    println("After Insert At end")
    storage.numberList.forEach { println(it) }
    Move(NUMBER0, NUMBER3, storage).doAction()
    storage.numberList.forEach { println(it) }
    println("After move")
    storage.undoLastAction()
    storage.numberList.forEach { println(it) }
    InsertAtEnd(NUMBER0, storage).doAction()
    storage.numberList.forEach { println(it) }
    storage.undoLastAction()
    storage.numberList.forEach { println(it) }
}
