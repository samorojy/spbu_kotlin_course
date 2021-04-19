package homework7.first

import java.awt.EventQueue

fun main() {
    EventQueue.invokeLater {
        val coroutinesDependenceChart = DrawCoroutinesDependenceChart()
        coroutinesDependenceChart.isVisible = true
        val arraySizeDependenceChart = DrawArraySizeDependenceChart()
        arraySizeDependenceChart.isVisible = true
    }
}
