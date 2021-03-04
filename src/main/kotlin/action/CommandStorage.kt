package action

class CommandStorage {
    var numberList = mutableListOf<Int>()
    private var actionList = mutableListOf<Action>()

    fun doAction(action: Action) {
        action.doAction()
        actionList.add(0, action)
    }

    fun undoLastAction() {
        if (actionList.isEmpty()) {
            println("ERROR! Action list is empty. Nothing to undo")
            return
        }
        actionList[0].undoAction()
        actionList.removeAt(0)
    }
}
