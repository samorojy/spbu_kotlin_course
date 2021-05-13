package homework8.bots

import homework8.TurnPlace
import kotlin.random.Random

class BotSimple : BotInterface {
    override fun makeTurn(gameSize: Int, currentGameState: List<List<Char>>): TurnPlace {
        var botTurn = getBotTurn(gameSize)
        while (!isBotTurnCorrect(botTurn, currentGameState)) botTurn = getBotTurn(gameSize)
        return botTurn
    }

    private fun getBotTurn(gameSize: Int) = TurnPlace(
        Random.nextInt(0, gameSize),
        Random.nextInt(0, gameSize)
    )

    private fun isBotTurnCorrect(botTurn: TurnPlace, currentGameState: List<List<Char>>): Boolean =
        currentGameState[botTurn.row][botTurn.column] == ' '
}
