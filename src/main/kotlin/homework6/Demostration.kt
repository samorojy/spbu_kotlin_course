@file:Suppress("MagicNumber")

package homework6

import homework7.first.SorterInterface

fun main() {
    ArraySizeDependenceGraphDrawer<SorterInterface>(
        MergeSorterThread(),
        16000, 200, 0,
        8
    )
}
