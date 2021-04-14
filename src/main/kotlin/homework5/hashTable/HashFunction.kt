package homework5.hashTable

interface HashFunction<K> {
    fun getHash(key: K): Int
}

class SimpleHashFunction : HashFunction<String> {
    override fun getHash(key: String): Int {
        var hash = 0
        key.forEach {
            hash *= 2
            hash += (it - 'a')
        }
        return kotlin.math.abs(hash)
    }

}
