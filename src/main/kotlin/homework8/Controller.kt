package homework8

import homework8.bots.BotSimple
import javafx.scene.control.Button
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.Controller
import tornadofx.borderpane
import tornadofx.box
import tornadofx.px
import tornadofx.style
import tornadofx.label

class Controller : Controller() {
    private var model = Model()
    private var gameMode = GameMode.PlayerVsPlayer

    fun changeGameSize(newSize: Int) {
        model = Model(newSize)
    }

    fun changeGameMode(newGameMode: GameMode) {
        gameMode = newGameMode
    }

    fun getGameSize() = model.gameFieldSize

    fun startGame() {
        find<StartView>().replaceWith<GameView>()
    }

    fun makeTurn(turnPlace: TurnPlace, buttons: List<MutableList<Button>>) {
        val turnResult = model.makeTurn(turnPlace)
        buttons[turnPlace.row][turnPlace.column].text = turnResult.sign
        if (turnResult == TurnStage.Draw || turnResult == TurnStage.Win0 || turnResult == TurnStage.WinX
        ) {
            finishGame(turnResult)
        }
        if (gameMode == GameMode.PlayerVsComputerEasy) {
            val botTurnPlace = BotSimple().makeTurn(model.gameFieldSize, model.getCurrentState())
            val botTurn = model.makeTurn(botTurnPlace)
            buttons[botTurnPlace.row][botTurnPlace.column].text = botTurn.sign
        }
    }

    private fun finishGame(winningStage: TurnStage) {
        find<GameView>().replaceWith<FinishView>()
        find<FinishView>().root.borderpane {
            center = label(
                when (winningStage) {
                    TurnStage.Draw -> "Draw"
                    TurnStage.WinX -> "Cross player won"
                    TurnStage.Win0 -> "Nought player won"
                    else -> "Something else"
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
}
