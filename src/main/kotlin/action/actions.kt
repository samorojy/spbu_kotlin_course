package action

import action.PerformedCommandStorage

interface Action {
    val storage: PerformedCommandStorage
    fun doAction()
    fun undoAction()
}

class InsertAtStart(private val number: Int, override val storage: PerformedCommandStorage) : Action {
    override fun doAction() {
        storage.numberList.add(0, number)
        storage.actionList.add(0, this)
    }

    override fun undoAction() {
        storage.numberList.removeAt(0)
    }
}