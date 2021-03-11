package action

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileWriter
import java.io.InputStream

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
        actionList[0].undoAction(this)
        actionList.removeFirst()
    }

    fun pushSerializationToFile(name: String) {
        val file = FileWriter(name)
        file.write(Json.encodeToString(actionList))
        file.flush()
    }

    fun getSerializationFromFile(name: String) {
        val inputStream: InputStream = File(name).inputStream()
        val inputString = inputStream.bufferedReader().use { it.readText() }
        val actionListFromFile: MutableList<Action> = Json.decodeFromString(inputString)
        for (action in actionListFromFile) {
            action.doAction(this)
        }
    }
}
