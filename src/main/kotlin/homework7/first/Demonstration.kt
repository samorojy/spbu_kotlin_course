@file:Suppress("MagicNumber")

package homework7.first

import homework6.ArraySizeDependenceGraphDrawer

fun main() {
    ArraySizeDependenceGraphDrawer<SorterInterface>(
        MergeSorterCoroutine(),
        16000, 200, 0,
        8
    )
}
