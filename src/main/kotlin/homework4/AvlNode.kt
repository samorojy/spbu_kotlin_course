package homework4

class AvlNode<K : Comparable<K>, V>(private var key: K, var value: V) {
    private var height: Int = 0
    private var leftNode: AvlNode<K, V>? = null
    private var rightNode: AvlNode<K, V>? = null

    fun balance(): AvlNode<K, V> {
        return when (this.getBalanceFactor()) {
            2 -> {
                if (this.leftNode?.getBalanceFactor() == -1) {
                    this.leftRightRotate()
                } else {
                    this.rightRotate()
                }
            }
            -2 -> {
                if (this.rightNode?.getBalanceFactor() == 1) {
                    this.rightLeftRotate()
                } else {
                    this.leftRotate()
                }
            }
            else -> this
        }
    }

    private fun leftRotate(): AvlNode<K, V> {
        val pivot = this.rightNode!!
        this.rightNode = pivot.leftNode
        pivot.leftNode = this
        this.height = maxOf(this.leftNode?.height ?: 0, this.rightNode?.height ?: 0) + 1
        pivot.height = maxOf(pivot.leftNode?.height ?: 0, pivot.rightNode?.height ?: 0) + 1
        return pivot
    }

    private fun leftRightRotate(): AvlNode<K, V> {
        val leftChild = this.leftNode ?: return this
        this.leftNode = leftChild.rightRotate()
        return this.rightRotate()
    }

    private fun rightRotate(): AvlNode<K, V> {
        val pivot = this.leftNode!!
        this.leftNode = pivot.rightNode
        pivot.rightNode = this
        this.height = maxOf(this.leftNode?.height ?: 0, this.rightNode?.height ?: 0) + 1
        pivot.height = maxOf(pivot.leftNode?.height ?: 0, pivot.rightNode?.height ?: 0) + 1
        return pivot
    }

    private fun rightLeftRotate(): AvlNode<K, V> {
        val rightChild = this.rightNode ?: return this
        this.rightNode = rightChild.rightRotate()
        return this.leftRotate()
    }

    fun updateHeight() {
        if (this.leftNode != null) {
            this.leftNode!!.updateHeight()
        }
        if (this.rightNode != null) {
            this.rightNode!!.updateHeight()
        }
        height = maxOf(leftNode?.height ?: 0, rightNode?.height ?: 0) + 1
    }

    private fun getBalanceFactor(): Int {
        return (leftNode?.height ?: 0) - (rightNode?.height ?: 0)
    }

    fun add(key: K, newValue: V): Boolean {
        if (key == this.key) {
            value = newValue
            return false
        }
        if (key < this.key) {
            if (leftNode == null) {
                leftNode = AvlNode(key, newValue)
                return true
            } else {
                leftNode?.add(key, newValue)
            }
        }

        if (key > this.key) {
            if (rightNode == null) {
                rightNode = AvlNode(key, newValue)
                return true
            } else {
                rightNode?.add(key, newValue)
            }
        }
        return false
    }

    fun remove(key: K): AvlNode<K, V>? {
        if (key < this.key) {
            leftNode = leftNode?.remove(key)
            return this
        }
        if (key > this.key) {
            rightNode = rightNode?.remove(key)
            return this
        }
        if (leftNode == null) {
            return rightNode
        }
        if (rightNode == null) {
            return leftNode
        }
        val minimumNode = rightNode!!.getMinimumNode()
        this.value = minimumNode.value
        this.key = minimumNode.key
        if (rightNode?.key == minimumNode.key) {
            this.rightNode = rightNode?.rightNode
        } else {
            rightNode?.removeMinimumNode(this.key)
        }
        return this
    }

    private fun getMinimumNode(): AvlNode<K, V> {
        while (this.leftNode != null) {
            this.leftNode!!.getMinimumNode()
        }
        return this
    }

    private fun removeMinimumNode(minKey: K) {
        if (leftNode?.key == minKey) {
            leftNode = null
        } else {
            leftNode?.removeMinimumNode(minKey)
        }
    }

    fun isContainsKey(key: K): Boolean {
        if (key < this.key) {
            return this.leftNode?.isContainsKey(key) ?: false
        }
        if (key > this.key) {
            return this.rightNode?.isContainsKey(key) ?: false
        }
        return true
    }

    fun isContainsValue(value: V): Boolean {
        return this.value == value ||
                rightNode?.isContainsValue(value) ?: false ||
                leftNode?.isContainsValue(value) ?: false
    }

    fun getValueByKey(key: K): V? {
        if (key < this.key) {
            return this.leftNode?.getValueByKey(key)
        }
        if (key > this.key) {
            return this.rightNode?.getValueByKey(key)
        }
        return this.value
    }
}
