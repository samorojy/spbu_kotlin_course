package action

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Path

internal class CommandStorageTest {

    @Test
    fun doAction() {
        val testStorage = CommandStorage()
        InsertAtStart(1).doAction(testStorage)
        InsertAtEnd(2).doAction(testStorage)
        Move(0, 1).doAction(testStorage)
        assertEquals(listOf(2, 1), testStorage.numberList)
    }

    @Test
    fun undoAction() {
        val testStorage = CommandStorage()
        InsertAtStart(1).doAction(testStorage)
        InsertAtEnd(2).doAction(testStorage)
        Move(0, 1).doAction(testStorage)
        testStorage.undoLastAction()
        assertEquals(listOf(1, 2), testStorage.numberList)
    }

    @Test
    fun serializeToFile(@TempDir directory: Path) {
        val testStorage = CommandStorage()
        val file: Path = directory.resolve("testFile.json")
        InsertAtStart(1).doAction(testStorage)
        InsertAtStart(2).doAction(testStorage)
        InsertAtStart(3).doAction(testStorage)
        InsertAtEnd(5).doAction(testStorage)
        testStorage.serializeToFile(file.toString())
        assertEquals(javaClass.getResource("ActionList.json").readText(), File(file.toString()).readText())
    }

    @Test
    fun deserializeFromFile() {
        val testStorage = CommandStorage()
        testStorage.deserializeFromFile(javaClass.getResource("ActionList.json").path)
        assertEquals(listOf(3, 2, 1, 5), testStorage.numberList)
    }
}
