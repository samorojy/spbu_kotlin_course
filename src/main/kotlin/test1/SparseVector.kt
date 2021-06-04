package test1

data class SparseVectorElementData<T : ArithmeticAvailable<T>>(val positionNumber: Int, var value: T)

data class SparseVector<T : ArithmeticAvailable<T>>(
    private val spaceSize: Int,
    private val list: MutableList<SparseVectorElementData<T>>
) {
    private val mapOfSparseVectorElements =
        mutableMapOf<Int, T>().also {
            list.forEach { sparseVectorElement ->
                it[sparseVectorElement.positionNumber] = sparseVectorElement.value
            }
        }

    private fun Map<Int, T>.toSparseVectorElementList(): MutableList<SparseVectorElementData<T>> =
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

    operator fun times(other: SparseVector<T>): T? {
        check(other)
        require(list.isNotEmpty()) { "Cannot multiply empty vectors" }
        var scalarTimesResult: T? = null
        val positions = list.map { it.positionNumber }.intersect(other.list.map { it.positionNumber })
        for (i in positions) {
            val first = list.find { it.positionNumber == i }
            val second = other.list.find { it.positionNumber == i }
            if (first == null || second == null) continue
            scalarTimesResult = scalarTimesResult?.let { it + first.value * second.value }
                ?: first.value * second.value
        }
        return scalarTimesResult
    }

    operator fun get(index: Int): T? = list.find { it.positionNumber == index }?.value

    operator fun set(index: Int, data: SparseVectorElementData<T>) {
        list.find { it.positionNumber == data.positionNumber }?.also { it.value = data.value } ?: list.add(index, data)
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
