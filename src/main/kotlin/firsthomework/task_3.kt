package firsthomework

import action.PerformedCommandStorage
import action.InsertAtStart
import action.Action

fun main() {
    val storage = PerformedCommandStorage()
    storage.actionList
    InsertAtStart(5, storage).doAction()
    InsertAtStart(3, storage).doAction()
    InsertAtStart(6, storage).doAction()
    InsertAtStart(7, storage).doAction()
    storage.numberList.forEach { println(it) }
    storage.undoActionList()
    storage.numberList.forEach { println(it) }
}