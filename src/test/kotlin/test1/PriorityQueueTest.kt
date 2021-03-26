package test1

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows

internal class PriorityQueueTest {
    private val queue = PriorityQueue<String, Int>()

    @Test
    fun enqueue() {
        queue.enqueue("F#", 1)
        assertEquals("F#", queue.peek())
    }

    @Test
    fun peek() {
        queue.enqueue("Kotlin", 1)
        queue.enqueue("F#", 100)
        assertEquals("Kotlin", queue.peek())
    }

    @Test
    fun peekEmpty() {
        assertThrows<NoSuchElementException> { queue.peek() }
    }

    @Test
    fun remove() {
        queue.enqueue("Kotlin", 10)
        queue.enqueue("F#", 1)
        queue.remove()
        assertEquals("Kotlin", queue.peek())
    }

    @Test
    fun removeEmpty() {
        assertThrows<NoSuchElementException> { queue.remove() }
    }

    @Test
    fun rool() {
        queue.enqueue("Kotlin", 10)
        queue.enqueue("F#", 1)
        assertEquals("F#", queue.rool())
    }

    @Test
    fun roolEmpty() {
        assertThrows<NoSuchElementException> { queue.rool() }
    }
}