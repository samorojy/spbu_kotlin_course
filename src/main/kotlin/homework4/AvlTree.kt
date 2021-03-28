package homework4

class AvlTree<K : Comparable<K>, V> : Map<K, V> {
    private var root: AvlNode<K, V>? = null
    private var _size: Int = 0
    override val size: Int
        get() {
            return _size
        }
    override var entries: Set<Map.Entry<K, V>> = setOf()
    override val keys: MutableSet<K> = mutableSetOf()
    override val values: Collection<V> = mutableListOf()

    fun addValue(key: K, value: V) {
        if (this.isEmpty()) {
            root = AvlNode(key, value)
            this._size++
        } else {
            if (root?.add(key, value) == true) {
                _size++
            }
            root?.updateHeight()
            root = root?.balance()
        }
    }

    fun remove(key: K) {
        root = root?.remove(key)
        root?.updateHeight()
        root = root?.balance()
    }

    override fun containsKey(key: K): Boolean = root?.isContainsKey(key) ?: false

    override fun containsValue(value: V): Boolean = root?.isContainsValue(value) ?: false

    override fun isEmpty(): Boolean = root == null

    override fun get(key: K): V? = root?.getValueByKey(key)

}
