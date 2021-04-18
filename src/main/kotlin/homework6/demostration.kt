package homework6

import java.awt.EventQueue

fun main() {
    EventQueue.invokeLater {
        val threadsDependenceGraph = DrawThreadsDependenceGraph()
        threadsDependenceGraph.isVisible = true
        val arraySizeDependenceGraph = DrawArraySizeDependenceGraph()
        arraySizeDependenceGraph.isVisible = true
    }
}
