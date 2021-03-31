package action

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * interface for action classes
 */
@Serializable
sealed class Action<K> {
    abstract fun doAction(numberList: MutableList<K>, storage: CommandStorage<K>)
    abstract fun undoAction(numberList: MutableList<K>, storage: CommandStorage<K>)
}

/**
 * Class for adding a number to the beginning of the list
 * @param number Value to add
 */
@Serializable
@SerialName("Insert at start")
class InsertAtStart<K>(private val number: K) : Action<K>() {
    /**
     * Adds the value to the beginning of the list
     * @property numberList The list of numbers with whom we work
     * @property storage CommandStorage with whom we work
     */
    override fun doAction(numberList: MutableList<K>, storage: CommandStorage<K>) {
        numberList.add(0, number)
        storage.doAction(this)
    }

    /**
     * Removes the value at the beginning of the list
     * @property numberList The list of numbers with whom we work
     * @property storage CommandStorage with whom we work
     */
    override fun undoAction(numberList: MutableList<K>, storage: CommandStorage<K>) {
        numberList.removeFirst()
    }
}

/**
 * Class for adding a number to the end of the list
 * @param number Value to add
 */
@Serializable
@SerialName("Insert at end")
class InsertAtEnd<K>(private val number: K) : Action<K>() {
    /**
     * Adds the value to the end of the list
     * @property numberList The list of numbers with whom we work
     * @property storage CommandStorage with whom we work
     */
    override fun doAction(numberList: MutableList<K>, storage: CommandStorage<K>) {
        numberList.add(number)
        storage.doAction(this)
    }

    /**
     * Removes the value at the end of the list
     * @property numberList The list of numbers with whom we work
     * @property storage CommandStorage with whom we work
     */
    override fun undoAction(numberList: MutableList<K>, storage: CommandStorage<K>) {
        numberList.removeLast()
    }
}

/**
 * Rearranges a list item from one place to another
 * @param startIndex The index from which to move the element
 * @param endIndex The index where to arrange the element
 * @property numberList The list of numbers with whom we work
 */
private fun <K> moveElement(startIndex: Int, endIndex: Int, numberList: MutableList<K>) {
    val value: K = numberList[startIndex]
    numberList.removeAt(startIndex)
    numberList.add(endIndex, value)
}

/**
 * Class which rearranges a list item from one place to another
 * @param startIndex The index from which to move the element
 * @param endIndex The index where to arrange the element
 */
@Serializable
@SerialName("Move")
class Move<K>(private val startIndex: Int, private val endIndex: Int) : Action<K>() {

    /**
     * Rearranges element from StartIndex to EndIndex
     * @property numberList The list of numbers with whom we work
     * @property storage CommandStorage with whom we work
     */
    override fun doAction(numberList: MutableList<K>, storage: CommandStorage<K>) {
        if ((startIndex !in numberList.indices) || (endIndex !in numberList.indices)) {
            error("ERROR! Out of List bounds")
        }
        moveElement(startIndex, endIndex, numberList)
        storage.doAction(this)
    }

    /**
     * Rearranges element from EndIndex to StartIndex
     * @property numberList The list of numbers with whom we work
     * @property storage CommandStorage with whom we work
     */
    override fun undoAction(numberList: MutableList<K>, storage: CommandStorage<K>) {
        moveElement(endIndex, startIndex, numberList)
    }
}
