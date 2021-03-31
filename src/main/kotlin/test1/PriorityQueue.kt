package test1

import java.util.PriorityQueue
import kotlin.NoSuchElementException

class QueueMember<E, K : Comparable<K>>(val element: E, private val priority: K) : Comparable<QueueMember<E, K>> {
    override fun compareTo(other: QueueMember<E, K>) = priority.compareTo(other.priority)
}

class PriorityQueue<E, K : Comparable<K>> {

    private val queue: PriorityQueue<QueueMember<E, K>> = PriorityQueue()

    fun enqueue(element: E, priority: K) {
        queue.add(QueueMember(element, priority))
    }

    private fun throwExceptionIfQueueEmpty(queue: PriorityQueue<QueueMember<E, K>>) {
        if (queue.isEmpty()) {
            throw NoSuchElementException("Queue is empty")
        }
    }

    fun peek(): E {
        throwExceptionIfQueueEmpty(queue)
        return queue.peek().element
    }

    fun remove() {
        throwExceptionIfQueueEmpty(queue)
        queue.poll()
    }

    fun rool(): E {
        throwExceptionIfQueueEmpty(queue)
        return queue.poll().element
    }
}
