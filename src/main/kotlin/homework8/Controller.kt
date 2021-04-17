package homework8

import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.Controller
import tornadofx.borderpane
import tornadofx.box
import tornadofx.px
import tornadofx.style
import tornadofx.label

object Controller : Controller() {
    const val GAME_FIELD_SIZE = 5
    private val fields = Array(GAME_FIELD_SIZE) { CharArray(GAME_FIELD_SIZE) { ' ' } }
    private var gameMode = GameMode.PlayerVsPlayer
    private var turnNumber = 0
    private val winningXExpression: String
    private val winning0Expression: String

    init {
        var tempXExpression = ""
        var temp0Expression = ""
        repeat(GAME_FIELD_SIZE) {
            tempXExpression += "X"
            temp0Expression += "0"
        }
        winningXExpression = tempXExpression
        winning0Expression = temp0Expression
    }

    fun startGame() {
        find<StartView>().replaceWith<GameView>()
    }

    private fun finishGame(winningStage: WinningStage) {
        find<GameView>().replaceWith<FinishView>()
        find<FinishView>().root.borderpane {
            center = label(
                when (winningStage) {
                    WinningStage.Draw -> "Draw"
                    WinningStage.WinX -> "Cross player won"
                    WinningStage.Win0 -> "Nought player won"
                }
            ) {
                style() {
                    fontSize = 25.px
                    fontFamily = "Montserrat"
                    fontWeight = FontWeight.EXTRA_BOLD
                    borderColor += box(
                        top = Color.RED,
                        right = Color.DARKGREEN,
                        left = Color.ORANGE,
                        bottom = Color.PURPLE
                    )
                }
            }
        }
    }

    fun changeGameMode(newGameMode: GameMode) {
        gameMode = newGameMode
    }

    fun updateCell(row: Int, column: Int): Char {
        fields[row][column] = if (turnNumber % 2 == 0) {
            'X'
        } else '0'
        ++turnNumber
        val checkResult = checkWin(turnNumber == GAME_FIELD_SIZE * GAME_FIELD_SIZE)
        if (checkResult != null) finishGame(checkResult)
        return if ((turnNumber - 1) % 2 == 0) 'X' else '0'
    }

    private fun getRow(row: Int): String {
        var rowResult = ""
        for (x in 0 until GAME_FIELD_SIZE) {
            rowResult += fields[row][x].toString()
        }
        return rowResult
    }

    private fun getColumn(column: Int): String {
        var columnResult = ""
        for (x in 0 until GAME_FIELD_SIZE) {
            columnResult += fields[x][column].toString()
        }
        return columnResult
    }

    private fun getLeftDiagonal(): String {
        var diagonalResult = ""
        for (i in 0 until GAME_FIELD_SIZE) {
            diagonalResult += fields[i][i].toString()
        }
        return diagonalResult
    }

    private fun getRightDiagonal(): String {
        var diagonalResult = ""
        for (i in 0 until GAME_FIELD_SIZE) {
            diagonalResult += fields[i][GAME_FIELD_SIZE - 1 - i].toString()
        }
        return diagonalResult
    }

    @Suppress("ReturnCount")
    private fun checkWin(isLastTurn: Boolean): WinningStage? {

        for (i in 0 until GAME_FIELD_SIZE) {
            if (getRow(i) == winningXExpression || getColumn(i) == winningXExpression) {
                return WinningStage.WinX
            }
            if (getRow(i) == winning0Expression || getColumn(i) == winning0Expression) {
                return WinningStage.Win0
            }
        }
        if (getLeftDiagonal() == winningXExpression || getRightDiagonal() == winningXExpression) {
            return WinningStage.WinX
        }
        if (getLeftDiagonal() == winning0Expression || getRightDiagonal() == winning0Expression) {
            return WinningStage.Win0
        }
        if (isLastTurn) {
            return WinningStage.Draw
        }
        return null
    }
}
