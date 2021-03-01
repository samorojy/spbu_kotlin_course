package action

import action.Action
import action.InsertAtStart

/*enum class Actions{
    INSERT_AT_END, INSERT_AT_START, MOVE
}*/

class PerformedCommandStorage() {
    var numberList = mutableListOf<Int>()
    var actionList = mutableListOf<Action>()

    fun undoActionList() {
        if (actionList.isEmpty()) {
            error("ERROR! Action list is empty. Nothing to undo")
        }
        actionList[0].undoAction()
        actionList.removeAt(0)
    }
}