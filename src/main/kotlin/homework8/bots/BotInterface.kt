package homework8.bots

import homework8.TurnPlace

interface BotInterface {
    fun makeTurn(gameSize: Int, currentGameState: List<List<Char>>): TurnPlace
}
