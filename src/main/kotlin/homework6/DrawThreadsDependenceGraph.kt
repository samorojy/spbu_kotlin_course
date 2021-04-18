@file:Suppress("MagicNumber")
package homework6

import java.awt.Font

import org.jfree.chart.title.TextTitle

import org.jfree.chart.block.BlockBorder

import java.awt.Color

import java.awt.BasicStroke

import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer

import org.jfree.chart.plot.PlotOrientation

import org.jfree.chart.ChartFactory

import org.jfree.chart.JFreeChart

import org.jfree.data.xy.XYDataset

import org.jfree.data.xy.XYSeriesCollection

import org.jfree.data.xy.XYSeries

import javax.swing.JFrame

import javax.swing.BorderFactory

import org.jfree.chart.ChartPanel

class DrawThreadsDependenceGraph : JFrame() {

    private fun initUI() {
        val dataset = createDataset()
        val chart = createChart(dataset)
        val chartPanel = ChartPanel(chart)
        chartPanel.border = BorderFactory.createEmptyBorder(15, 15, 15, 15)
        chartPanel.background = Color.white
        add(chartPanel)
        pack()
        title = "Dependence of the running time on the number of threads"
        setLocationRelativeTo(null)
        defaultCloseOperation = EXIT_ON_CLOSE
    }

    private fun createDataset(): XYDataset {
        val series = XYSeries("array of 1000 numbers")
        for (threads in 1..100) {
            val arrayToSort = IntArray(1000)
            for (i in 0 until 1000) {
                arrayToSort[i] = 1000 - i
            }
            val startTime = System.nanoTime()
            arrayToSort.mergeSortingMainMultiThread(threads)
            val endTime = System.nanoTime()
            series.add(threads, endTime - startTime)
        }
        val dataset = XYSeriesCollection()
        dataset.addSeries(series)
        return dataset
    }

    private fun createChart(dataset: XYDataset): JFreeChart {
        val chart = ChartFactory.createXYLineChart(
            "Dependence of execution time on the number of threads",
            "Threads",
            "Time (nanoseconds)",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        )
        val plot = chart.xyPlot
        val renderer = XYLineAndShapeRenderer()
        renderer.setSeriesPaint(0, Color.RED)
        renderer.setSeriesStroke(0, BasicStroke(2.0f))
        plot.renderer = renderer
        plot.backgroundPaint = Color.white
        plot.isRangeGridlinesVisible = true
        plot.rangeGridlinePaint = Color.BLACK
        plot.isDomainGridlinesVisible = true
        plot.domainGridlinePaint = Color.BLACK
        chart.legend.frame = BlockBorder.NONE
        chart.title = TextTitle(
            "Dependence of execution time on the number of threads",
            Font("Serif", Font.BOLD, 18)
        )
        return chart
    }

    init {
        initUI()
    }
}
