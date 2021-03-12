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
        actionList.add(action)
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
        actionList.last().undoAction(this)
        actionList.removeLast()
    }

    /**
     * Serialize actionList and put it to file
     * @param name path to put file with serialization of actionList
     */
    fun serializeToFile(name: String) {
        val file = FileWriter(name)
        file.write(Json.encodeToString(actionList))
        file.flush()
    }

    /**
     * Deserialize Json and performs actionList's actions from file
     * @param name path to get the file for deserialization
     */
    fun deserializeFromFile(name: String) {
        val inputStream: InputStream = File(name).inputStream()
        val inputString = inputStream.bufferedReader().use { it.readText() }
        val actionListFromFile: MutableList<Action> = Json.decodeFromString(inputString)
        for (action in actionListFromFile) {
            action.doAction(this)
        }
    }
}
