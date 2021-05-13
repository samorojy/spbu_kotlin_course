package homework8.controller

import homework8.view.FinishView
import homework8.view.GameView
import homework8.view.StartView
import homework8.bots.BotHard
import homework8.bots.BotInterface
import homework8.bots.BotSimple
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
    private var currentBot: BotInterface? = null

    fun getGameSize(): Int = model.gameFieldSize

    fun changeGameSize(newSize: Int) {
        model = Model(newSize)
    }

    fun changeGameMode(newGameMode: GameMode) {
        gameMode = newGameMode
        currentBot = when (gameMode) {
            GameMode.PlayerVsComputerEasy -> BotSimple()
            GameMode.PlayerVsComputerHard -> BotHard()
            else -> null
        }
    }

    fun makeTurn(turnPlace: TurnPlace, buttons: List<List<Button>>) {
        val turnResult = model.makeTurn(turnPlace)
        buttons[turnPlace.row][turnPlace.column].text = turnResult.sign
        if (isGameFinished(turnResult, buttons)) return
        if (gameMode != GameMode.PlayerVsPlayerLocal) {
            if (gameMode != GameMode.PlayerVsPlayerOnline) {
                if (currentBot != null) {
                    val botTurnPlace = currentBot!!.makeTurn(model.getCurrentState())
                    val botTurn = model.makeTurn(botTurnPlace)
                    buttons[botTurnPlace.row][botTurnPlace.column].text = botTurn.sign
                    isGameFinished(botTurn, buttons)
                }
            } else {
                TODO()
            }
        }
    }

    private fun isGameFinished(turnResult: TurnStage, buttons: List<List<Button>>): Boolean {
        if (turnResult == TurnStage.DRAW || turnResult == TurnStage.WIN_0 || turnResult == TurnStage.WIN_X) {
            finishGame(turnResult, buttons)
            return true
        }
        return false
    }

    fun startGame() {
        find<StartView>().replaceWith<GameView>()
    }

    fun restartGame() {
        find<FinishView>().replaceWith<StartView>()
        model = Model()
    }

    private fun finishGame(winningStage: TurnStage, buttons: List<List<Button>>) {
        buttons.forEach { list -> list.forEach { it.text = " " } }
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
