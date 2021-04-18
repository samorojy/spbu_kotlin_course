package homework6

import java.awt.EventQueue

fun main() {
    EventQueue.invokeLater {
        val ex = DrawThreadsDependenceGraph()
        ex.isVisible = true
    }
    EventQueue.invokeLater {
        val ex = DrawArraySizeDependenceGraph()
        ex.isVisible = true
    }
}