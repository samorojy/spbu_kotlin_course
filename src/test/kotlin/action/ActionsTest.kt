package action

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class InsertAtStartTest {

    @Test
    fun doAction() {
        val testStorage = CommandStorage()
        InsertAtStart(3).doAction(testStorage)
        InsertAtStart(2).doAction(testStorage)
        InsertAtStart(1).doAction(testStorage)
        assertEquals(listOf(1, 2, 3), testStorage.numberList)
    }

    @Test
    fun undoAction() {
        val testStorage = CommandStorage()
        InsertAtStart(3).doAction(testStorage)
        InsertAtStart(2).doAction(testStorage)
        InsertAtStart(1).doAction(testStorage)
        testStorage.undoLastAction()
        assertEquals(listOf(2, 3), testStorage.numberList)
    }
}

internal class InsertAtStartEndTest {

    @Test
    fun doAction() {
        val testStorage = CommandStorage()
        InsertAtEnd(3).doAction(testStorage)
        InsertAtEnd(2).doAction(testStorage)
        InsertAtEnd(1).doAction(testStorage)
        assertEquals(listOf(3, 2, 1), testStorage.numberList)
    }

    @Test
    fun undoAction() {
        val testStorage = CommandStorage()
        InsertAtEnd(3).doAction(testStorage)
        InsertAtEnd(2).doAction(testStorage)
        InsertAtEnd(1).doAction(testStorage)
        testStorage.undoLastAction()
        assertEquals(listOf(3, 2), testStorage.numberList)
    }
}

internal class MoveTest {

    @Test
    fun doAction() {
        val testStorage = CommandStorage()
        InsertAtEnd(3).doAction(testStorage)
        InsertAtEnd(2).doAction(testStorage)
        InsertAtEnd(1).doAction(testStorage)
        Move(0, 2).doAction(testStorage)
        assertEquals(listOf(2, 1, 3), testStorage.numberList)
    }

    @Test
    fun undoAction() {
        val testStorage = CommandStorage()
        InsertAtEnd(3).doAction(testStorage)
        InsertAtEnd(2).doAction(testStorage)
        InsertAtEnd(1).doAction(testStorage)
        Move(0, 2).doAction(testStorage)
        testStorage.undoLastAction()
        assertEquals(listOf(3, 2, 1), testStorage.numberList)
    }
}
