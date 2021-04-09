package action

import action.CommandStorage.IntJson.deserializeFromFile
import action.CommandStorage.IntJson.serializeToFile
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Path

internal class CommandStorageTest {

    @Test
    fun doUndoAction() {
        val testStorage = CommandStorage<Int>()
        InsertAtStart(1).doAction(testStorage)
        InsertAtEnd(2).doAction(testStorage)
        Move<Int>(0, 1).doAction(testStorage)
        InsertAtStart(5).doAction(testStorage)
        testStorage.undoLastAction()
        assertEquals(listOf(2, 1), testStorage.numberList)
    }

    @Test
    fun serializeToFile(@TempDir directory: Path) {
        val testStorage = CommandStorage<Int>()
        val file = directory.resolve("testFile.json")
        InsertAtStart(1).doAction(testStorage)
        InsertAtStart(2).doAction(testStorage)
        InsertAtStart(3).doAction(testStorage)
        InsertAtEnd(5).doAction(testStorage)
        testStorage.serializeToFile(file.toString())
        assertEquals(javaClass.getResource("ActionList.json").readText(), File(file.toString()).readText())
    }

    @Test
    fun deserializeFromFile() {
        val testStorage = CommandStorage<Int>()
        testStorage.deserializeFromFile(javaClass.getResource("ActionList.json").path)
        assertEquals(listOf(3, 2, 1, 5), testStorage.numberList)
    }
}
