package homework5.hashTable

class HashTable<K, V>(private var hashFunction: HashFunction<K>) {
    companion object {
        const val MAX_LOAD_FACTOR = 0.7
    }

    data class HashElement<K, V>(val key: K, var value: V)

    private var elementCount = 0
    private val loadFactor: Double
        get() {
            return elementCount / buckets.size.toDouble()
        }
    private var buckets = Array(1) { mutableListOf<HashElement<K, V>>() }

    fun add(key: K, value: V) {
        val hash = hashFunction.getHash(key) % buckets.size
        val element = buckets[hash].find { key == it.key }
        if (element != null) {
            if (value != element.value) {
                element.value = value
            }
        } else {
            buckets[hash].add(HashElement(key, value))
            ++elementCount
        }
        if (loadFactor >= MAX_LOAD_FACTOR) {
            this.expand()
        }
    }

    private fun expand() {
        updateTable(buckets.size * 2)
    }

    private fun updateTable(newSize: Int = buckets.size) {
        val newBuckets = Array(newSize) { mutableListOf<HashElement<K, V>>() }
        buckets.forEach { list ->
            list.forEach { hashElement ->
                val hash = hashFunction.getHash(hashElement.key) % (newSize)
                newBuckets[hash].add(hashElement)
            }
        }
        buckets = newBuckets
    }

    fun remove(key: K): Boolean {
        val hash = hashFunction.getHash(key) % (buckets.size)
        val element = buckets[hash].find { key == it.key }
        if (element != null) {
            buckets[hash].remove(element)
            --elementCount
        }
        return element != null
    }

    fun contains(key: K): V? {
        val hash = hashFunction.getHash(key) % (buckets.size)
        return buckets[hash].find { key == it.key }?.value
    }
}
