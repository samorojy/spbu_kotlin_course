package homework4

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.concurrent.fixedRateTimer
import kotlin.math.abs

internal class AvlTreeTest {
    private val tree = AvlTree<Int, String>()

    @Test
    fun addValue() {
        tree.addValue(1, "Kotlin")
        tree.addValue(2, "C#")
        assertEquals(setOf(Pair(1, "Kotlin"), Pair(2, "C#")), tree.entries.map { Pair(it.key, it.value) }.toSet())
    }

    @Test
    fun remove() {
        tree.addValue(1, "Kotlin")
        tree.addValue(2, "C#")
        tree.addValue(0, "F#")
        tree.remove(1)
        assertEquals(setOf(Pair(2, "C#"), Pair(0, "F#")), tree.entries.map { Pair(it.key, it.value) }.toSet())
    }

    @Test
    fun containsKey() {
        tree.addValue(1, "First")
        tree.addValue(2, "Second")
        assertEquals(setOf(true, false), setOf(tree.containsKey(1), tree.containsKey(3)))
    }

    @Test
    fun containsValue() {
        tree.addValue(1, "First")
        tree.addValue(2, "Second")
        assertEquals(setOf(true, false), setOf(tree.containsValue("First"), tree.containsValue("Third")))
    }

    @Test
    fun isEmpty() {
        assertEquals(true, tree.isEmpty())
    }

    @Test
    fun isNotEmpty() {
        tree.addValue(1, "Value")
        assertEquals(false, tree.isEmpty())
    }

    @Test
    fun getSize() {
        tree.addValue(1, "First")
        tree.addValue(4, "Second")
        assertEquals(2, tree.size)
    }

    @Test
    fun clear() {
        tree.addValue(1, "First")
        tree.addValue(4, "Second")
        tree.clear()
        assertEquals(setOf(0, true), setOf(tree.size, tree.isEmpty()))
    }
}
