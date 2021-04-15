package homework5.hashTable

interface HashFunction<K> {
    val name: String
    fun getHash(key: K): Int
}

object DefaultHashFunction : HashFunction<String> {
    override val name = "Default hash"
    override fun getHash(key: String): Int {
        return kotlin.math.abs(key.hashCode())
    }
}

object OwnHashFunction : HashFunction<String> {
    override val name = "Own hash"
    override fun getHash(key: String): Int {
        var hash = 0
        key.forEach {
            hash = hash * 2 + (it - 'a')
        }
        return kotlin.math.abs(hash)
    }
}
