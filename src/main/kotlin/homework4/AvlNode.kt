package homework4

import kotlin.math.min

class AvlNode<K : Comparable<K>, V>(val key: K, var value: V) {
    var height: Int = 0
    var leftNode: AvlNode<K, V>? = null
    var rightNode: AvlNode<K, V>? = null

    fun add(key: K, newValue: V): Boolean? {
        return when {
            key == this.key -> {
                value = newValue
                true
            }

            key < this.key -> {
                if (leftNode == null) {
                    leftNode = AvlNode(key, newValue)
                    true
                } else {
                    leftNode?.add(key, newValue)
                }
            }

            key > this.key -> {
                if (rightNode == null) {
                    rightNode = AvlNode(key, newValue)
                    true
                } else {
                    rightNode?.add(key, newValue)
                }
            }
            else -> false
        }
    }

    private fun getParentOfKey(key: K): AvlNode<K, V>? {
        if (key != this.key) {
            if (key == this.rightNode?.key || key == this.leftNode?.key) {
                return this
            }
            if (key < this.key) {
                if (this.leftNode == null) {
                    return null
                }
                return this.leftNode?.getParentOfKey(key)
            }
            if (key > this.key) {
                if (this.rightNode == null) {
                    return null
                }
                return this.rightNode?.getParentOfKey(key)
            }
        }
        return this
    }

    fun removeNode(key: K): Boolean {
        val removeNodeParent = getParentOfKey(key) ?: return false
        if (key == removeNodeParent.key) {
            return remove(null,removeNodeParent)
        }
        if (key == removeNodeParent.leftNode?.key) {
            return remove(removeNodeParent,removeNodeParent.leftNode!!)
        }
        if (key == removeNodeParent.rightNode?.key) {
            return remove(removeNodeParent, removeNodeParent.rightNode!!)
        }
        return false
    }

    private fun remove(parentNode:AvlNode<K,V>?,node: AvlNode<K, V>): Boolean {

        if (this.leftNode != null && this.rightNode != null) {
            val minimumNodeParent = this.rightNode?.getMinimumParent()
            val newNode = minimumNodeParent?.leftNode
            if (newNode?.rightNode != null) {
                minimumNodeParent.leftNode = newNode.rightNode
            } else {
                minimumNodeParent?.leftNode = null
            }
            this.leftNode = newNode?.leftNode
            this.rightNode = newNode?.rightNode
            return true
        }
        return false
    }

    private fun getMinimumParent(): AvlNode<K, V>? {
        if (this.leftNode != null) {
            if (this.leftNode?.leftNode == null) {
                return this
            }
            return leftNode?.getMinimumParent()
        }
        return this
    }
}