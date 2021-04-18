@file:Suppress("MagicNumber")
package homework6

import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.chart.block.BlockBorder
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer
import org.jfree.chart.title.TextTitle
import org.jfree.data.xy.XYDataset
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Font
import javax.swing.BorderFactory
import javax.swing.JFrame

class DrawArraySizeDependenceGraph : JFrame() {

    private fun initUI() {
        val dataset = createDataset()
        val chart = createChart(dataset)
        val chartPanel = ChartPanel(chart)
        chartPanel.border = BorderFactory.createEmptyBorder(15, 15, 15, 15)
        chartPanel.background = Color.white
        add(chartPanel)
        pack()
        title = "Dependence of the running time on the size of the array"
        setLocationRelativeTo(null)
        defaultCloseOperation = EXIT_ON_CLOSE
    }

    private fun createDataset(): XYDataset {
        val series = XYSeries("10 threads")
        for (arraySize in 2..25000) {
            val arrayToSort = IntArray(arraySize)
            for (i in 0 until arraySize) {
                arrayToSort[i] = arraySize - i
            }
            val startTime = System.nanoTime()
            arrayToSort.mergeSortingMainMultiThread(10)
            val endTime = System.nanoTime()
            series.add(arraySize, endTime - startTime)
        }
        val dataset = XYSeriesCollection()
        dataset.addSeries(series)
        return dataset
    }

    private fun createChart(dataset: XYDataset): JFreeChart {
        val chart = ChartFactory.createXYLineChart(
            "Dependence of execution time on the array size",
            "Array size",
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
            "Dependence of execution time on the array size",
            Font("Serif", Font.BOLD, 18)
        )
        return chart
    }

    init {
        initUI()
    }
}
