package action

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class InsertAtStartTest {
    private val testStorage = CommandStorage()

    @Test
    fun doAction() {
        InsertAtStart(3).doAction(testStorage)
        InsertAtStart(2).doAction(testStorage)
        InsertAtStart(1).doAction(testStorage)
        assertEquals(listOf(1, 2, 3), testStorage.numberList)
    }

    @Test
    fun undoAction() {
        InsertAtStart(3).doAction(testStorage)
        InsertAtStart(2).doAction(testStorage)
        InsertAtStart(1).doAction(testStorage)
        testStorage.undoLastAction()
        assertEquals(listOf(2, 3), testStorage.numberList)
    }
}

internal class InsertAtStartEndTest {
    private val testStorage = CommandStorage()

    @Test
    fun doAction() {
        InsertAtEnd(3).doAction(testStorage)
        InsertAtEnd(2).doAction(testStorage)
        InsertAtEnd(1).doAction(testStorage)
        assertEquals(listOf(3, 2, 1), testStorage.numberList)
    }

    @Test
    fun undoAction() {
        InsertAtEnd(3).doAction(testStorage)
        InsertAtEnd(2).doAction(testStorage)
        InsertAtEnd(1).doAction(testStorage)
        testStorage.undoLastAction()
        assertEquals(listOf(3, 2), testStorage.numberList)
    }
}

internal class MoveTest {
    private val testStorage = CommandStorage()

    @Test
    fun doAction() {
        InsertAtEnd(3).doAction(testStorage)
        InsertAtEnd(2).doAction(testStorage)
        InsertAtEnd(1).doAction(testStorage)
        Move(0, 2).doAction(testStorage)
        assertEquals(listOf(2, 1, 3), testStorage.numberList)
    }

    @Test
    fun undoAction() {
        InsertAtEnd(3).doAction(testStorage)
        InsertAtEnd(2).doAction(testStorage)
        InsertAtEnd(1).doAction(testStorage)
        Move(0, 2).doAction(testStorage)
        testStorage.undoLastAction()
        assertEquals(listOf(3, 2, 1), testStorage.numberList)
    }
}
