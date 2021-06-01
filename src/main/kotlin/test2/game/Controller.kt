package test2.game

import javafx.scene.control.Button
import tornadofx.Controller

class Controller : Controller() {
    private lateinit var model: Model
    var gameSize = DEFAULT_GAME_SIZE
        private set

    fun setSize(newGameSize: Int) {
        require(newGameSize % 2 == 0) { "The size of the playing field must be even" }
        gameSize = newGameSize
    }

    fun makeMove(turnPlace: TurnPlace, buttons: List<List<Button>>) {
        if (model.makeTurn(turnPlace, buttons)) finishGame()
    }

    fun startGame() {
        find<StartView>().replaceWith<GameView>()
        model = Model(gameSize)
    }

    private fun finishGame() {
        find<GameView>().replaceWith<FinalView>()
    }

    companion object {
        const val DEFAULT_GAME_SIZE = 4
    }
}
