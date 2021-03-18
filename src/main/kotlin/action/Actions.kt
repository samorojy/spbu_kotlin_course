package action

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * interface for action classes
 */
@Serializable
sealed class Action {
    abstract fun doAction(storage: CommandStorage)
    abstract fun undoAction(storage: CommandStorage)
}

/**
 * Class for adding a number to the beginning of the list
 * @param number Value to add
 */
@Serializable
@SerialName("Insert at start")
class InsertAtStart(private val number: Int) : Action() {
    /**
     * Adds the value to the beginning of the list
     * @property storage CommandStorage with whom we work
     */
    override fun doAction(storage: CommandStorage) {
        storage.numberList.add(0, number)
        storage.doAction(this)
    }

    /**
     * Removes the value at the beginning of the list
     * @property storage CommandStorage with whom we work
     */
    override fun undoAction(storage: CommandStorage) {
        storage.numberList.removeFirst()
    }
}

/**
 * Class for adding a number to the end of the list
 * @param number Value to add
 */
@Serializable
@SerialName("Insert at end")
class InsertAtEnd(private val number: Int) : Action() {
    /**
     * Adds the value to the end of the list
     * @property storage CommandStorage with whom we work
     */
    override fun doAction(storage: CommandStorage) {
        storage.numberList.add(number)
        storage.doAction(this)
    }

    /**
     * Removes the value at the end of the list
     * @property storage CommandStorage with whom we work
     */
    override fun undoAction(storage: CommandStorage) {
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
 */
@Serializable
@SerialName("Move")
class Move(private val startIndex: Int, private val endIndex: Int) : Action() {

    /**
     * Rearranges element from StartIndex to EndIndex
     * @property storage CommandStorage with whom we work
     */
    override fun doAction(storage: CommandStorage) {
        if ((startIndex !in storage.numberList.indices) || (endIndex !in storage.numberList.indices)) {
            error("ERROR! Out of List bounds")
        }
        moveElement(startIndex, endIndex, storage)
        storage.doAction(this)
    }

    /**
     * Rearranges element from EndIndex to StartIndex
     * @property storage CommandStorage with whom we work
     */
    override fun undoAction(storage: CommandStorage) {
        moveElement(endIndex, startIndex, storage)
    }
}
