package action

interface Action {
    val storage: CommandStorage
    fun doAction()
    fun undoAction()
}

class InsertAtStart(private val number: Int, override val storage: CommandStorage) : Action {
    override fun doAction() {
        storage.numberList.add(0, number)
    }

    override fun undoAction() {
        storage.numberList.removeFirst()
    }
}

class InsertAtEnd(private val number: Int, override val storage: CommandStorage) : Action {
    override fun doAction() {
        storage.numberList.add(number)
    }

    override fun undoAction() {
        storage.numberList.removeLast()
    }
}

private fun moveElement(startIndex: Int, endIndex: Int, storage: CommandStorage) {
    val value: Int = storage.numberList[startIndex]
    storage.numberList.removeAt(startIndex)
    storage.numberList.add(endIndex, value)
}

class Move(private val startIndex: Int, private val endIndex: Int, override val storage: CommandStorage) : Action {

    override fun doAction() {
        if (startIndex < 0 || endIndex < 0 || startIndex > storage.numberList.size || endIndex > storage.numberList.size) {
            error("ERROR! Out of List bounds")
        }
        moveElement(startIndex, endIndex, storage)
    }

    override fun undoAction() {
        moveElement(endIndex, startIndex, storage)
    }
}
