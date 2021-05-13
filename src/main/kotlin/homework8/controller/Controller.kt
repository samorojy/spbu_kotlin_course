package homework8.controller

import homework8.FinishView
import homework8.GameView
import homework8.StartView
import homework8.bots.BotHard
import homework8.model.Model
import javafx.scene.control.Button
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.Controller
import tornadofx.borderpane
import tornadofx.box
import tornadofx.px
import tornadofx.style
import tornadofx.label
import java.lang.IllegalArgumentException

class Controller : Controller() {
    private var model = Model()
    private var gameMode = GameMode.PlayerVsComputerHard

    fun changeGameSize(newSize: Int) {
        model = Model(newSize)
    }

    fun changeGameMode(newGameMode: GameMode) {
        gameMode = newGameMode
    }

    fun startGame() {
        find<StartView>().replaceWith<GameView>()
    }

    fun makeTurn(turnPlace: TurnPlace, buttons: List<List<Button>>) {
        val turnResult = model.makeTurn(turnPlace)
        buttons[turnPlace.row][turnPlace.column].text = turnResult.sign
        if (isGameFinished(turnResult)) return
        if (gameMode == GameMode.PlayerVsComputerHard) {
            val botTurnPlace = BotHard().makeTurn(model.getCurrentState())
            val botTurn = model.makeTurn(botTurnPlace)
            buttons[botTurnPlace.row][botTurnPlace.column].text = botTurn.sign
            if (isGameFinished(botTurn)) return
        }
    }

    private fun isGameFinished(turnResult: TurnStage): Boolean {
        if (turnResult == TurnStage.DRAW || turnResult == TurnStage.WIN_0 || turnResult == TurnStage.WIN_X) {
            finishGame(turnResult)
            return true
        }
        return false
    }

    private fun finishGame(winningStage: TurnStage) {
        find<GameView>().replaceWith<FinishView>()
        find<FinishView>().root.borderpane {
            center = label(
                when (winningStage) {
                    TurnStage.DRAW -> "Draw"
                    TurnStage.WIN_X -> "Cross player won"
                    TurnStage.WIN_0 -> "Nought player won"
                    else -> throw IllegalArgumentException("The game can only be completed at the winner stage")
                }
            ) {
                style() {
                    fontSize = 25.px
                    fontFamily = "Helvetica"
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
}
