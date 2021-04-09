package action

import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import java.io.File

/**
 * Stores a list of numbers and a list of actions on them
 * @property numberList stores numbers
 * @property actionList stores actions on numbers
 */
class CommandStorage<T> {
    val numberList = mutableListOf<T>()
    private var actionList = mutableListOf<Action<T>>()

    /**
     * add action to actionList
     */
    fun doAction(action: Action<T>) {
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

    companion object IntJson {
        private val json = Json {
            useArrayPolymorphism = true
            serializersModule = SerializersModule {
                polymorphic(Any::class) {
                    subclass(IntAsObjectSerializer)
                }
            }
        }

        /**
         * Serialize actionList and put it to file
         * @param name path to put file with serialization of actionList
         */
        fun CommandStorage<Int>.serializeToFile(name: String) {
            File(name).writeText(json.encodeToString(actionList))
        }

        /**
         * Deserialize Json and performs actionList's actions from file
         * @param name path to get the file for deserialization
         */
        fun CommandStorage<Int>.deserializeFromFile(name: String) {
            val inputString = File(name).readText()
            val actionListFromFile: MutableList<Action<Int>> = json.decodeFromString(inputString)
            for (action in actionListFromFile) {
                action.doAction(this)
            }
        }
    }
}
