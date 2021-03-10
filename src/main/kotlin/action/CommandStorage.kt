package action

import kotlinx.serialization.Serializable

/**
 * Stores a list of numbers and a list of actions on them
 * @property numberList stores numbers
 * @property actionList stores actions on numbers
 */

class CommandStorage {
    var numberList = mutableListOf<Int>()
    private var actionList = mutableListOf<Action>()

    /**
     * add action to actionList
     */
    fun doAction(action: Action) {
        actionList.add(0, action)
    }

    /**
     * undo the last action on numberList
     * remove last action from actionList
     */
    fun undoLastAction() {
        if (actionList.isEmpty()) {
            println("ERROR! Action list is empty. Nothing to undo")
            return
        }
        actionList[0].undoAction()
        actionList.removeFirst()
    }
}
