@file:Suppress("MagicNumber")

package homework7.first

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
import kotlin.random.Random

class ArraySizeDependenceCoroutineGraphDrawer(
    private val arraySize: Int,
    private val minThreadsNumber: Int,
    private val maxThreadsNumber: Int,
    private val stepThread: Int
) : JFrame() {

    init {
        initUI()
    }

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
        val dataset = XYSeriesCollection()
        for (threadsNumber in minThreadsNumber until maxThreadsNumber step stepThread) {
            val series = XYSeries("$threadsNumber coroutines")
            for (tempArraySize in 2..arraySize) {
                val arrayToSort = getNewArrayAndFill(tempArraySize)
                val startTime = System.nanoTime()
                MergeSorterCoroutine().sort(arrayToSort, threadsNumber)
                val endTime = System.nanoTime()
                series.add(tempArraySize, endTime - startTime)
            }
            dataset.addSeries(series)
        }
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
        for (series in 0 until (maxThreadsNumber - minThreadsNumber) / stepThread) {
            renderer.setSeriesPaint(series, colorArray[series % colorArray.size])
            renderer.setSeriesStroke(series, BasicStroke(1.0f))
        }
        renderer.setSeriesPaint(0, colorArray[0])
        renderer.setSeriesStroke(0, BasicStroke(1.0f))
        plot.renderer = renderer
        plot.backgroundPaint = Color.white
        plot.isRangeGridlinesVisible = true
        plot.rangeGridlinePaint = Color.BLACK
        plot.isDomainGridlinesVisible = true
        plot.domainGridlinePaint = Color.BLACK
        chart.legend.frame = BlockBorder.NONE
        chart.title = TextTitle(
            "Dependence of execution time on the array size",
            Font("Serif", Font.BOLD, 22)
        )
        return chart
    }

    private fun getNewArrayAndFill(tempArraySize: Int): IntArray {
        val arrayToSort = IntArray(tempArraySize)
        for (i in 0 until tempArraySize) {
            arrayToSort[i] = Random.nextInt(Int.MAX_VALUE)
        }
        return arrayToSort
    }

    companion object {
        val colorArray = arrayOf(
            Color.RED, Color.CYAN, Color.ORANGE,
            Color.MAGENTA, Color.LIGHT_GRAY, Color.PINK,
            Color.GREEN, Color.BLUE, Color.BLACK,
            Color.YELLOW
        )
    }
}
