package homework8.model

import homework8.bots.hard.BotHard
import homework8.bots.simple.BotSimple
import homework8.controller.GameMode
import homework8.controller.TurnAuthor
import homework8.controller.TurnPlace
import javafx.scene.control.Button

class PlayWithBotModel(gameSize: Int, gameMode: GameMode) : Model(gameSize) {

    private val bot = when (gameMode) {
        GameMode.PLAYER_VS_COMPUTER_EASY -> BotSimple()
        GameMode.PLAYER_VS_COMPUTER_HARD -> BotHard()
        else -> throw IllegalArgumentException("Game mode must be game with bot")
    }

    override fun move(turnPlace: TurnPlace, gameField: List<List<Button>>, turnAuthor: TurnAuthor): MoveResult {
        var turnResult = makeTurn(turnPlace, gameField)
        if (turnResult.isGameOver) return turnResult

        turnResult = makeTurn(bot.makeTurn(getCurrentState()), gameField)
        return turnResult
    }
}
