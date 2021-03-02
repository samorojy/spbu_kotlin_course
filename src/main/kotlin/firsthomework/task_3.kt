package firsthomework

import action.commandStorage
import action.InsertAtStart
import action.InsertAtEnd
import action.Move

fun main() {
    val storage = commandStorage()
    storage.actionList
    InsertAtStart(5, storage).doAction()
    InsertAtStart(3, storage).doAction()
    InsertAtStart(6, storage).doAction()
    InsertAtStart(7, storage).doAction()
    storage.numberList.forEach { println(it) }
    storage.undoLastAction()
    println("After Undo")
    storage.numberList.forEach { println(it) }
    InsertAtEnd(10, storage).doAction()
    println("After Insert At end")
    storage.numberList.forEach { println(it) }
    Move(0, 3, storage).doAction()
    storage.numberList.forEach { println(it) }
    println("After move")
    storage.undoLastAction()
    storage.numberList.forEach { println(it) }
    InsertAtEnd(0, storage).doAction()
    storage.numberList.forEach { println(it) }
    storage.undoLastAction()
    storage.numberList.forEach { println(it) }
}
