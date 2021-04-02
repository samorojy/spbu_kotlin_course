package action

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Path

internal class CommandStorageTest {
    private val testNumberList: MutableList<Int> = mutableListOf()

    @Test
    fun doUndoAction() {
        val testStorage = CommandStorage<Int>()
        InsertAtStart(1).doAction(testNumberList, testStorage)
        InsertAtEnd(2).doAction(testNumberList, testStorage)
        Move<Int>(0, 1).doAction(testNumberList, testStorage)
        InsertAtStart(5).doAction(testNumberList, testStorage)
        testStorage.undoLastAction(testNumberList)
        assertEquals(listOf(2, 1), testNumberList)
    }

    @Test
    fun serializeToFile(@TempDir directory: Path) {
        val testStorage = CommandStorage<Int>()
        val file = directory.resolve("testFile.json")
        InsertAtStart(1).doAction(testNumberList, testStorage)
        InsertAtStart(2).doAction(testNumberList, testStorage)
        InsertAtStart(3).doAction(testNumberList, testStorage)
        InsertAtEnd(5).doAction(testNumberList, testStorage)
        testStorage.serializeToFile(file.toString())
        assertEquals(javaClass.getResource("ActionList.json").readText(), File(file.toString()).readText())
    }

    @Test
    fun deserializeFromFile() {
        val testStorage = CommandStorage<Int>()
        testStorage.deserializeFromFile(testNumberList, javaClass.getResource("ActionList.json").path)
        assertEquals(listOf(3, 2, 1, 5), testNumberList)
    }
}
