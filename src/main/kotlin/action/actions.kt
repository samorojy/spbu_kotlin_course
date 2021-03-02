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

class InsertAtEnd(private val number: Int, override val storage: PerformedCommandStorage) : Action {
    override fun doAction() {
        storage.numberList.add(number)
        storage.actionList.add(0, this)
    }

    override fun undoAction() {
        storage.numberList.removeAt(0)
    }
}

class Move(private val startIndex: Int, private val endIndex: Int, override val storage: PerformedCommandStorage) :
    Action {
    override fun doAction() {
        if (startIndex > storage.numberList.size || endIndex > storage.numberList.size) {
            error("ERROR! Out of List bounds")
        }
        val value: Int = storage.numberList[startIndex]
        storage.numberList.removeAt(startIndex)
        storage.numberList.add(endIndex, value)
        storage.actionList.add(0, this)
    }

    override fun undoAction() {
        val value: Int = storage.numberList[endIndex]
        storage.numberList.removeAt(endIndex)
        storage.numberList.add(startIndex, value)
    }
}
