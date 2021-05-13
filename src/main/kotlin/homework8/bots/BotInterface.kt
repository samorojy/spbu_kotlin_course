package homework8.bots

import homework8.controller.TurnPlace

interface BotInterface {
    val botName: String
    fun makeTurn(currentGameState: List<List<Char>>): TurnPlace
}
