@file:Suppress("TooManyFunctions")

package homework4

import kotlin.math.max

class AvlNode<K : Comparable<K>, V>(override val key: K, private var privateValue: V) : Map.Entry<K, V> {
    private var height: Int = 0
    override val value: V
        get() {
            return privateValue
        }
    private var leftNode: AvlNode<K, V>? = null
    private var rightNode: AvlNode<K, V>? = null

    companion object {
        private const val balanceFactorValue = 2
    }

    fun balance(): AvlNode<K, V> {
        leftNode = leftNode?.balance()
        rightNode = rightNode?.balance()
        return when (this.getBalanceFactor()) {
            balanceFactorValue -> {
                if (this.leftNode?.getBalanceFactor() == -1) {
                    this.bigLeftRotate()
                } else {
                    this.rightRotate()
                }
            }
            -balanceFactorValue -> {
                if (this.rightNode?.getBalanceFactor() == 1) {
                    this.bigRightRotate()
                } else {
                    this.leftRotate()
                }
            }
            else -> this
        }
    }

    fun getBalanceFactor(): Int {
        return (leftNode?.height ?: 0) - (rightNode?.height ?: 0)
    }

    fun updateHeight() {
        if (this.leftNode != null) {
            this.leftNode?.updateHeight()
        }
        if (this.rightNode != null) {
            this.rightNode?.updateHeight()
        }
        height = max(leftNode?.height ?: 0, rightNode?.height ?: 0) + 1
    }

    private fun leftRotate(): AvlNode<K, V> {
        val pivot = this.rightNode!!
        this.rightNode = pivot.leftNode
        pivot.leftNode = this
        this.height = max(this.leftNode?.height ?: 0, this.rightNode?.height ?: 0) + 1
        pivot.height = max(pivot.leftNode?.height ?: 0, pivot.rightNode?.height ?: 0) + 1
        return pivot
    }

    private fun bigLeftRotate(): AvlNode<K, V> {
        val leftChild = this.leftNode ?: return this
        this.leftNode = leftChild.leftRotate()
        return this.rightRotate()
    }

    private fun rightRotate(): AvlNode<K, V> {
        val pivot = this.leftNode!!
        this.leftNode = pivot.rightNode
        pivot.rightNode = this
        this.height = max(this.leftNode?.height ?: 0, this.rightNode?.height ?: 0) + 1
        pivot.height = max(pivot.leftNode?.height ?: 0, pivot.rightNode?.height ?: 0) + 1
        return pivot
    }

    private fun bigRightRotate(): AvlNode<K, V> {
        val rightChild = this.rightNode ?: return this
        this.rightNode = rightChild.rightRotate()
        return this.leftRotate()
    }

    fun add(key: K, newValue: V): Boolean {
        return when {
            key == this.key -> {
                privateValue = newValue
                false
            }
            key < this.key -> {
                if (leftNode == null) {
                    leftNode = AvlNode(key, newValue)
                    true
                } else {
                    leftNode?.add(key, newValue) ?: false
                }
            }

            key > this.key -> {
                if (rightNode == null) {
                    rightNode = AvlNode(key, newValue)
                    true
                } else {
                    rightNode?.add(key, newValue) ?: false
                }
            }
            else -> false
        }
    }

    fun remove(key: K, parentNode: AvlNode<K, V>?): AvlNode<K, V>? {
        return when {
            key < this.key -> {
                this.leftNode = this.leftNode?.remove(key, this)
                this
            }
            key > this.key -> {
                this.rightNode = this.rightNode?.remove(key, this)
                this
            }
            else -> removeByKey(key, parentNode)
        }
    }

    private fun removeByKey(key: K, previousNode: AvlNode<K, V>?): AvlNode<K, V>? {
        return when {
            leftNode == null -> this.rightNode
            rightNode == null -> this.leftNode
            else -> {
                val minimumNode = this.rightNode?.getMinimumNode() ?: this

                if (previousNode?.leftNode == this) {
                    previousNode.leftNode = minimumNode
                } else {
                    previousNode?.rightNode = minimumNode
                }
                minimumNode.leftNode = this.leftNode
                if (this.rightNode?.key != minimumNode.key) {
                    this.rightNode?.removeMinimumNode(key)
                    minimumNode.rightNode = this.rightNode
                } else {
                    minimumNode.rightNode = minimumNode.rightNode?.rightNode
                }
                minimumNode.balance()
            }
        }
    }

    private fun getMinimumNode(): AvlNode<K, V> = leftNode?.getMinimumNode() ?: this

    private fun removeMinimumNode(minimumKey: K) {
        if (leftNode?.key == minimumKey) {
            leftNode = null
        } else {
            leftNode?.removeMinimumNode(minimumKey)
        }
    }

    fun isContainsKey(key: K): Boolean {
        return when {
            key < this.key -> {
                this.leftNode?.isContainsKey(key) ?: false
            }

            key > this.key -> {
                this.rightNode?.isContainsKey(key) ?: false
            }

            else -> true
        }
    }

    fun isContainsValue(value: V): Boolean = this.privateValue == value ||
            rightNode?.isContainsValue(value) ?: false ||
            leftNode?.isContainsValue(value) ?: false

    fun getValueByKey(key: K): V? {
        return when {
            key < this.key -> {
                this.leftNode?.getValueByKey(key)
            }
            key > this.key -> {
                this.rightNode?.getValueByKey(key)
            }
            else -> this.privateValue
        }
    }

    fun getEntries(entries: MutableSet<AvlNode<K, V>>) {
        entries.add(this)
        this.leftNode?.getEntries(entries)
        this.rightNode?.getEntries(entries)
    }
}
