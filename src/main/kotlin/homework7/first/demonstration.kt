@file:Suppress("MagicNumber", "VariableNaming")

package homework7.first

fun main() {
    val ChartDrawer = ArraySizeDependenceCoroutineGraphDrawer(
        1000, 1,
        30, 5
    )
    ChartDrawer.isVisible = true
}
