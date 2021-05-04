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
import kotlin.math.pow
import kotlin.random.Random

class ArraySizeDependenceCoroutineGraphDrawer(
    private val arraySize: Int,
    private val arraySizeStep: Int,
    private val minPowerOfTwoOfThreadsNumber: Int,
    private val maxPowerOfTwoOfThreadsNumber: Int
) : JFrame() {

    init {
        initUI()
        this.isVisible = true
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
        val maxNumberOfThreads = 2.0.pow(maxPowerOfTwoOfThreadsNumber).toInt()
        var threadsNumber = 2.0.pow(minPowerOfTwoOfThreadsNumber).toInt()
        while (threadsNumber <= maxNumberOfThreads) {
            val series = XYSeries("$threadsNumber coroutines")
            for (tempArraySize in 2..arraySize step arraySizeStep) {
                val arrayToSort = getRandomArray(tempArraySize)
                val startTime = System.currentTimeMillis()
                MergeSorterCoroutine().sort(arrayToSort, threadsNumber)
                val endTime = System.currentTimeMillis()
                series.add(tempArraySize, endTime - startTime)
            }
            dataset.addSeries(series)
            threadsNumber *= 2
        }
        return dataset
    }

    private fun createChart(dataset: XYDataset): JFreeChart {
        val chart = ChartFactory.createXYLineChart(
            "Dependence of execution time on the array size",
            "Array size",
            "Time (milliseconds)",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        )
        val plot = chart.xyPlot
        val renderer = XYLineAndShapeRenderer()
        for (series in 0 until (maxPowerOfTwoOfThreadsNumber - minPowerOfTwoOfThreadsNumber)) {
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

    private fun getRandomArray(tempArraySize: Int): IntArray =
        IntArray(tempArraySize) { Random.nextInt(Int.MAX_VALUE) }

    companion object {
        val colorArray = arrayOf(
            Color.RED, Color.CYAN, Color.ORANGE,
            Color.MAGENTA, Color.LIGHT_GRAY, Color.PINK,
            Color.GREEN, Color.BLUE, Color.BLACK,
            Color.YELLOW
        )
    }
}
