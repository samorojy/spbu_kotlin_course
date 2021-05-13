package homework8

class Model(val gameFieldSize: Int = 3) {
    private val fields: List<MutableList<Char>> = List(gameFieldSize) { MutableList(gameFieldSize) { ' ' } }
    private var turnNumber = 0
    private val winningXExpression: String
    private val winning0Expression: String

    init {
        var tempXExpression = ""
        var temp0Expression = ""
        repeat(gameFieldSize) {
            tempXExpression += TurnStage.NoWinnerYetX.sign
            temp0Expression += TurnStage.NoWinnerYet0.sign
        }
        this.winningXExpression = tempXExpression
        this.winning0Expression = temp0Expression
    }

    fun makeTurn(turnPlace: TurnPlace): TurnStage {
        fields[turnPlace.row][turnPlace.column] = if (turnNumber % 2 == 0) {
            'X'
        } else '0'
        ++turnNumber
        return checkWin(turnNumber == gameFieldSize * gameFieldSize)
    }

    fun getCurrentState(): List<List<Char>> = fields

    private fun getRow(row: Int): String {
        var rowResult = ""
        for (x in 0 until gameFieldSize) {
            rowResult += fields[row][x].toString()
        }
        return rowResult
    }

    private fun getColumn(column: Int): String {
        var columnResult = ""
        for (x in 0 until gameFieldSize) {
            columnResult += fields[x][column].toString()
        }
        return columnResult
    }

    private fun getLeftDiagonal(): String {
        var diagonalResult = ""
        for (i in 0 until gameFieldSize) {
            diagonalResult += fields[i][i].toString()
        }
        return diagonalResult
    }

    private fun getRightDiagonal(): String {
        var diagonalResult = ""
        for (i in 0 until gameFieldSize) {
            diagonalResult += fields[i][gameFieldSize - i - 1].toString()
        }
        return diagonalResult
    }

    @Suppress("ReturnCount")
    private fun checkWin(isLastTurn: Boolean): TurnStage {

        for (i in 0 until gameFieldSize) {
            if (getRow(i) == winningXExpression || getColumn(i) == winningXExpression) {
                return TurnStage.WinX
            }
            if (getRow(i) == winning0Expression || getColumn(i) == winning0Expression) {
                return TurnStage.Win0
            }
        }
        if (getLeftDiagonal() == winningXExpression || getRightDiagonal() == winningXExpression) {
            return TurnStage.WinX
        }
        if (getLeftDiagonal() == winning0Expression || getRightDiagonal() == winning0Expression) {
            return TurnStage.Win0
        }
        if (isLastTurn) {
            return TurnStage.Draw
        }
        return if ((turnNumber - 1) % 2 == 0) TurnStage.NoWinnerYetX else TurnStage.NoWinnerYet0
    }
}
