package action

class PerformedCommandStorage {
    var numberList= mutableListOf<Int>()
    var actionList = mutableListOf<Action>()

    fun undoLastAction() {
        if (actionList.isEmpty()) {
            error("ERROR! Action list is empty. Nothing to undo")
        }
        actionList[0].undoAction()
        actionList.removeAt(0)
    }
}
