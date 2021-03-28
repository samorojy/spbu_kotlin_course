package homework4

class AvlTree<K : Comparable<K>, V> : Map<K, V> {
    private var root: AvlNode<K, V>? = null
    override var size: Int = 0
    override val entries: Set<Map.Entry<K, V>> = mutableSetOf()
    override val keys: Set<K> = mutableSetOf()
    override val values: Set<V> = mutableSetOf()

    override fun containsKey(key: K): Boolean {
        TODO("Not yet implemented")
    }

    override fun containsValue(value: V): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean = root == null

    override fun get(key: K): V? {
        TODO("Not yet implemented")
    }


}