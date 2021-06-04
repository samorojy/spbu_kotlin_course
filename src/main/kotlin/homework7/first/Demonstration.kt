@file:Suppress("MagicNumber")

package homework7.first

import homework6.ArraySizeDependenceGraphDrawer

fun main() {
    ArraySizeDependenceGraphDrawer(
        MergeSorterCoroutine(),
        16000, 900, 0,
        8
    )
}
