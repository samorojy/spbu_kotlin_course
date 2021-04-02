@file:Suppress("TooManyFunctions")

package homework4

import kotlin.math.max

class AvlNode<K : Comparable<K>, V>(override var key: K, override var value: V) : Map.Entry<K, V> {
    private var height: Int = 0
    private var leftNode: AvlNode<K, V>? = null
    private var rightNode: AvlNode<K, V>? = null

    companion object {
        private const val balanceFactorValue = 2
    }

    fun balance(): AvlNode<K, V> {
        return when (this.getBalanceFactor()) {
            balanceFactorValue -> {
                if (this.leftNode?.getBalanceFactor() == -1) {
                    this.leftRightRotate()
                } else {
                    this.rightRotate()
                }
            }
            -balanceFactorValue -> {
                if (this.rightNode?.getBalanceFactor() == 1) {
                    this.rightLeftRotate()
                } else {
                    this.leftRotate()
                }
            }
            else -> this
        }
    }

    private fun getBalanceFactor(): Int {
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

    private fun leftRightRotate(): AvlNode<K, V> {
        val leftChild = this.leftNode ?: return this
        this.leftNode = leftChild.rightRotate()
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

    private fun rightLeftRotate(): AvlNode<K, V> {
        val rightChild = this.rightNode ?: return this
        this.rightNode = rightChild.rightRotate()
        return this.leftRotate()
    }

    fun add(key: K, newValue: V): Boolean {
        return when {
            key == this.key -> {
                value = newValue
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

    fun remove(key: K): AvlNode<K, V>? {
        return when {
            key < this.key -> {
                leftNode = leftNode?.remove(key)
                this
            }
            key > this.key -> {
                rightNode = rightNode?.remove(key)
                this
            }
            else -> {
                when {
                    leftNode == null -> {
                        rightNode
                    }
                    rightNode == null -> {
                        leftNode
                    }
                    else -> {
                        val minimumNode = rightNode?.getMinimumNode() ?: this
                        minimumNode.leftNode = this.leftNode
                        if (minimumNode.key != rightNode?.key) {
                            rightNode?.removeMinimumNode(minimumNode.key, minimumNode.rightNode)
                            minimumNode.rightNode = this.rightNode
                        }
                        minimumNode.balance()
                    }
                }
            }
        }
    }

    private fun getMinimumNode(): AvlNode<K, V> = leftNode?.getMinimumNode() ?: this

    private fun removeMinimumNode(minimumKey: K, renewedNode: AvlNode<K, V>?) {
        if (leftNode?.key == minimumKey) {
            leftNode = renewedNode
        } else {
            leftNode?.removeMinimumNode(minimumKey, renewedNode)
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

    fun isContainsValue(value: V): Boolean {
        return this.value == value ||
                rightNode?.isContainsValue(value) ?: false ||
                leftNode?.isContainsValue(value) ?: false
    }

    fun getValueByKey(key: K): V? {
        return when {
            key < this.key -> {
                this.leftNode?.getValueByKey(key)
            }
            key > this.key -> {
                this.rightNode?.getValueByKey(key)
            }
            else -> this.value
        }
    }

    fun getEntries(entries: MutableSet<AvlNode<K, V>>) {
        entries.add(this)
        this.leftNode?.getEntries(entries)
        this.rightNode?.getEntries(entries)
    }
}
