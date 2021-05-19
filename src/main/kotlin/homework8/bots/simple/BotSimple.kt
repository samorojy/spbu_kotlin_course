package homework8.bots.simple

import homework8.bots.BotInterface
import homework8.controller.TurnPlace
import kotlin.random.Random

class BotSimple : BotInterface {
    override val botName = "Simple Bot: Alex"
    override fun makeTurn(currentGameState: List<List<Char>>): TurnPlace {
        var botTurn = getBotTurn(currentGameState)
        while (!isBotTurnCorrect(botTurn, currentGameState)) botTurn = getBotTurn(currentGameState)
        return botTurn
    }

    private fun getBotTurn(currentGameState: List<List<Char>>) = TurnPlace(
        Random.nextInt(0, currentGameState.size),
        Random.nextInt(0, currentGameState.size)
    )

    private fun isBotTurnCorrect(botTurn: TurnPlace, currentGameState: List<List<Char>>): Boolean =
        currentGameState[botTurn.row][botTurn.column] == ' '
}
