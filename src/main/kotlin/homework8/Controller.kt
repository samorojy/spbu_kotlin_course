package homework8

import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.Controller
import tornadofx.borderpane
import tornadofx.box
import tornadofx.px
import tornadofx.style
import tornadofx.label

class Controller() : Controller() {
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

    fun makeTurn(row: Int, column: Int):String {
        return model.makeTurn(row, column).sign
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
