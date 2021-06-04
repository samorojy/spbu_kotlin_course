package test1

data class SparseVectorElementData<T : ArithmeticAvailable<T>>(val positionNumber: Int, val value: T)

data class SparseVector<T : ArithmeticAvailable<T>>(
    private val spaceSize: Int,
    private val list: List<SparseVectorElementData<T>>
) {
    private val mapOfSparseVectorElements =
        mutableMapOf<Int, T>().also {
            list.forEach { sparseVectorElement ->
                it[sparseVectorElement.positionNumber] = sparseVectorElement.value
            }
        }

    private fun Map<Int, T>.toSparseVectorElementList(): List<SparseVectorElementData<T>> =
        mutableListOf<SparseVectorElementData<T>>().also {
            this.toList()
                .forEach { pairFromMap ->
                    if (!pairFromMap.second.isZero()) it.add(
                        SparseVectorElementData(
                            pairFromMap.first,
                            pairFromMap.second
                        )
                    )
                }
        }

    private fun check(other: SparseVector<T>) =
        require(other.spaceSize == spaceSize) { "Cannot operate on vectors of size $spaceSize and ${other.spaceSize}" }

    operator fun plus(other: SparseVector<T>): SparseVector<T> {
        check(other)
        val resultedMapOfElements = mapOfSparseVectorElements.toMutableMap()
        other.list.forEach {
            resultedMapOfElements[it.positionNumber] =
                resultedMapOfElements[it.positionNumber]?.plus(it.value) ?: it.value
        }
        return SparseVector(spaceSize, resultedMapOfElements.toSparseVectorElementList())
    }

    operator fun minus(other: SparseVector<T>): SparseVector<T> {
        check(other)
        val resultedMapOfElements = mapOfSparseVectorElements.toMutableMap()
        other.list.forEach {
            resultedMapOfElements[it.positionNumber] =
                resultedMapOfElements[it.positionNumber]?.minus(it.value) ?: -it.value
        }
        return SparseVector(spaceSize, resultedMapOfElements.toSparseVectorElementList())
    }

    @Suppress("NestedBlockDepth")
    operator fun times(other: SparseVector<T>): T? {
        check(other)
        require(list.isNotEmpty()) { "Cannot multiply empty vectors" }
        var scalarTimesResult: T? = null
        for (i in list.indices) {
            for (j in other.list.indices) {
                if (list[i].positionNumber == other.list[j].positionNumber) {
                    if (scalarTimesResult != null) {
                        scalarTimesResult += list[i].value * other.list[j].value
                    } else scalarTimesResult = list[i].value * other.list[j].value
                }
            }
        }
        return scalarTimesResult
    }
}

interface ArithmeticAvailable<T : ArithmeticAvailable<T>> {
    operator fun plus(other: T): T
    operator fun minus(other: T): T
    operator fun times(other: T): T
    operator fun unaryMinus(): T
    fun isZero(): Boolean
}

data class ArithmeticInt(private val actual: Int) : ArithmeticAvailable<ArithmeticInt> {
    override fun plus(other: ArithmeticInt) = ArithmeticInt(actual + other.actual)
    override fun minus(other: ArithmeticInt) = ArithmeticInt(actual - other.actual)
    override fun times(other: ArithmeticInt) = ArithmeticInt(actual * other.actual)
    override fun unaryMinus(): ArithmeticInt = ArithmeticInt(-actual)
    override fun isZero() = actual == 0
}
