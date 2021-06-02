package homework8.controller

import homework8.model.Model
import homework8.model.PlayWithBotModel
import homework8.model.PlayWithPlayerOnlineModel
import homework8.view.FinishView
import homework8.view.GameView
import homework8.view.StartView
import javafx.application.Platform
import javafx.scene.control.Button
import tornadofx.Controller

class Controller : Controller() {
    private lateinit var model: Model
    private var gameMode = GameMode.PLAYER_VS_PLAYER_ONLINE
    var gameSize = DEFAULT_GAME_SIZE
        private set

    fun changeGameSize(newSize: Int) {
        gameSize = newSize
    }

    fun changeGameMode(newGameMode: GameMode) {
        gameMode = newGameMode
        find<StartView>().currentGameMode.set(gameMode.presentableName)
    }

    fun makeTurn(turnPlace: TurnPlace, buttons: List<List<Button>>, turnAuthor: TurnAuthor = TurnAuthor.SERVER) {
        if (buttons[turnPlace.row][turnPlace.column].text != " ") return
        val turnResult = model.move(turnPlace, buttons)
        if (turnResult.isGameOver) finishGame(turnResult.turnStage, buttons, turnAuthor)
    }

    fun startGame() {
        find<StartView>().replaceWith<GameView>()
        model = when (gameMode) {
            GameMode.PLAYER_VS_PLAYER_LOCAL -> Model(gameSize)
            GameMode.PLAYER_VS_PLAYER_ONLINE -> PlayWithPlayerOnlineModel(
                this,
                gameSize,
                find<GameView>().buttons,
            )
            else -> PlayWithBotModel(gameSize, gameMode)
        }
    }

    fun restartGame() {
        find<FinishView>().replaceWith<StartView>()
    }

    fun finishGame(
        winningStage: TurnStage,
        buttons: List<List<Button>>,
        turnAuthor: TurnAuthor
    ) {
        if (turnAuthor == TurnAuthor.CLIENT) {
            endingGame(winningStage, buttons)
        } else {
            Platform.runLater { endingGame(winningStage, buttons) }
        }
    }

    private fun endingGame(
        winningStage: TurnStage,
        buttons: List<List<Button>>,
    ) {
        find<GameView>().replaceWith<FinishView>()
        buttons.forEach { list -> list.forEach { it.text = " " } }
        find<FinishView>().winnerMessage.set(
            when (winningStage) {
                TurnStage.DRAW -> "Draw"
                TurnStage.WIN_X -> "Cross player won"
                TurnStage.WIN_0 -> "Nought player won"
                else -> throw IllegalArgumentException("The game can only be completed at the winner stage")
            }
        )
    }

    companion object {
        const val DEFAULT_GAME_SIZE = 3
    }
}
