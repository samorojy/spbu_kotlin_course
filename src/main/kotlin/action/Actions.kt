package action

/**
 * interface for action classes
 */
interface Action {
    val storage: CommandStorage
    fun doAction()
    fun undoAction()
}

/**
 * Class for adding a number to the beginning of the list
 * @param number Value to add
 * @property storage CommandStorage with whom we work
 */
class InsertAtStart(private val number: Int, override val storage: CommandStorage) : Action {
    /**
     * Adds the value to the beginning of the list
     */
    override fun doAction() {
        storage.numberList.add(0, number)
        storage.doAction(this)
    }

    /**
     * Removes the value at the beginning of the list
     */
    override fun undoAction() {
        storage.numberList.removeFirst()
    }
}

/**
 * Class for adding a number to the end of the list
 * @param number Value to add
 * @property storage CommandStorage with whom we work
 */
class InsertAtEnd(private val number: Int, override val storage: CommandStorage) : Action {
    /**
     * Adds the value to the end of the list
     */
    override fun doAction() {
        storage.numberList.add(number)
        storage.doAction(this)
    }

    /**
     * Removes the value at the end of the list
     */
    override fun undoAction() {
        storage.numberList.removeLast()
    }
}

/**
 * Rearranges a list item from one place to another
 * @param startIndex The index from which to move the element
 * @param endIndex The index where to arrange the element
 * @property storage CommandStorage with whom we work
 */
private fun moveElement(startIndex: Int, endIndex: Int, storage: CommandStorage) {
    val value: Int = storage.numberList[startIndex]
    storage.numberList.removeAt(startIndex)
    storage.numberList.add(endIndex, value)
}

/**
 * Class which rearranges a list item from one place to another
 * @param startIndex The index from which to move the element
 * @param endIndex The index where to arrange the element
 * @property storage CommandStorage with whom we work
 */
class Move(private val startIndex: Int, private val endIndex: Int, override val storage: CommandStorage) : Action {

    /**
     * Rearranges element from StartIndex to EndIndex
     */
    override fun doAction() {
        if ((startIndex !in storage.numberList.indices) || (endIndex !in storage.numberList.indices)) {
            error("ERROR! Out of List bounds")
        }
        moveElement(startIndex, endIndex, storage)
        storage.doAction(this)
    }

    /**
     * Rearranges element from EndIndex to StartIndex
     */
    override fun undoAction() {
        moveElement(endIndex, startIndex, storage)
    }
}
