package homework5.hashTable

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class HashTableTest {
    private val table = HashTable<String, String>(DefaultHashFunction)

    @Test
    fun add() {
        val dataList = listOf(Pair("first", "1"), Pair("second", "2"), Pair("third", "3"))
        dataList.forEach { table.add(it.first, it.second) }
        dataList.forEach { assertEquals(it.second, table[it.first]) }
    }

    @Test
    fun remove() {
        val dataList = listOf(Pair("first", "1"), Pair("second", "2"), Pair("third", "3"))
        dataList.forEach { table.add(it.first, it.second) }
        assertEquals("2", table["second"])
        assertEquals(true, table.remove("second"))
        assertEquals(false, table.remove("six"))
        assertEquals(null, table["second"])
    }

    @Test
    fun contains() {
        val dataList = listOf(Pair("first", "1"), Pair("second", "2"), Pair("third", "3"))
        dataList.forEach { table.add(it.first, it.second) }
        assertEquals(false, table.contains("fifth"))
        assertEquals(true, table.contains("third"))
        table.add("fifth", "5")
        assertEquals(true, table.contains("fifth"))
    }

    @Test
    fun changeHashFunction() {
        val dataList = listOf(Pair("first", "1"), Pair("second", "2"), Pair("third", "3"))
        dataList.forEach { table.add(it.first, it.second) }
        assertEquals(table["first"], "1")
        table.changeHashFunction(OwnHashFunction)
        assertEquals(table["first"], "1")
    }
}
