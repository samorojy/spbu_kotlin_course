package action

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.lang.IllegalStateException
import javax.print.attribute.standard.NumberOfInterveningJobs

internal class InsertAtStartTest {
    private val testStorage = CommandStorage<Int>()
    private val testNumberList: MutableList<Int> = mutableListOf()

    @Test
    fun doAction() {
        InsertAtStart(3).doAction(testNumberList, testStorage)
        InsertAtStart(2).doAction(testNumberList, testStorage)
        InsertAtStart(1).doAction(testNumberList, testStorage)
        assertEquals(listOf(1, 2, 3), testNumberList)
    }

    @Test
    fun undoAction() {
        InsertAtStart(3).doAction(testNumberList, testStorage)
        InsertAtStart(2).doAction(testNumberList, testStorage)
        InsertAtStart(1).doAction(testNumberList, testStorage)
        testStorage.undoLastAction(testNumberList)
        assertEquals(listOf(2, 3), testNumberList)
    }
}

internal class InsertAtEndTest {
    private val testStorage = CommandStorage<Int>()
    private val testNumberList: MutableList<Int> = mutableListOf()


    @Test
    fun doAction() {
        InsertAtEnd(3).doAction(testNumberList, testStorage)
        InsertAtEnd(2).doAction(testNumberList, testStorage)
        InsertAtEnd(1).doAction(testNumberList, testStorage)
        assertEquals(listOf(3, 2, 1), testNumberList)
    }

    @Test
    fun undoAction() {
        InsertAtEnd(3).doAction(testNumberList, testStorage)
        InsertAtEnd(2).doAction(testNumberList, testStorage)
        InsertAtEnd(1).doAction(testNumberList, testStorage)
        testStorage.undoLastAction(testNumberList)
        assertEquals(listOf(3, 2), testNumberList)
    }
}

internal class MoveTest {
    private val testStorage = CommandStorage<Int>()
    private val testNumberList: MutableList<Int> = mutableListOf()

    @Test
    fun doAction() {
        InsertAtEnd(3).doAction(testNumberList, testStorage)
        InsertAtEnd(2).doAction(testNumberList, testStorage)
        InsertAtEnd(1).doAction(testNumberList, testStorage)
        Move<Int>(0, 2).doAction(testNumberList, testStorage)
        assertEquals(listOf(2, 1, 3), testNumberList)
    }

    @Test
    fun undoAction() {
        InsertAtEnd(3).doAction(testNumberList, testStorage)
        InsertAtEnd(2).doAction(testNumberList, testStorage)
        InsertAtEnd(1).doAction(testNumberList, testStorage)
        Move<Int>(0, 2).doAction(testNumberList, testStorage)
        testStorage.undoLastAction(testNumberList)
        assertEquals(listOf(3, 2, 1), testNumberList)
    }

    @Test
    fun useIncorrectData() {
        InsertAtEnd(3).doAction(testNumberList, testStorage)
        InsertAtEnd(2).doAction(testNumberList, testStorage)
        InsertAtEnd(1).doAction(testNumberList, testStorage)
        assertThrows(IllegalStateException("ERROR! Out of List bounds")::class.java) {
            Move<Int>(-1, 5).doAction(testNumberList, testStorage)
        }
    }
}
