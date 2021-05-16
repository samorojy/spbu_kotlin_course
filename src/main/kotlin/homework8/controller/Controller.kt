package homework8.controller

import homework8.view.FinishView
import homework8.view.GameView
import homework8.view.StartView
import homework8.bots.BotHard
import homework8.bots.BotInterface
import homework8.bots.BotSimple
import homework8.model.Model
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.*
import kotlinx.serialization.json.Json
import javafx.scene.control.Button
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import tornadofx.Controller
import tornadofx.borderpane
import tornadofx.box
import tornadofx.px
import tornadofx.style
import tornadofx.label
import java.lang.IllegalArgumentException

class Controller : Controller() {
    private var model = Model()
    private var gameMode = GameMode.PLAYER_VS_COMPUTER_HARD
    private var currentBot: BotInterface? = null
    var currentTurn: TurnPlace = TurnPlace(0, 0)
        private set

    fun getGameSize(): Int = model.gameFieldSize

    fun changeGameSize(newSize: Int) {
        model = Model(newSize)
    }

    fun changeGameMode(newGameMode: GameMode) {
        gameMode = newGameMode
        currentBot = when (gameMode) {
            GameMode.PLAYER_VS_COMPUTER_EASY -> BotSimple()
            GameMode.PLAYER_VS_COMPUTER_HARD -> BotHard()
            else -> null
        }
    }

    fun updateFields(buttons: List<List<Button>>) {
        if (gameMode == GameMode.PLAYER_VS_PLAYER_ONLINE) {
            val client = HttpClient(CIO)
            var string: String
            runBlocking {
                string = client.get<String>("http://0.0.0.0:8080/getTurn")
            }
            client.close()
            val otherPlayerTurn = Json.decodeFromString<TurnPlace>(string)
            if (otherPlayerTurn.row > 0) {
                makeTurn(otherPlayerTurn, buttons)
            }
        }
    }

    private fun sendTurnToServer(turnPlace: TurnPlace) {
        val client = HttpClient(CIO)
        runBlocking {
            client.post<String>("http://0.0.0.0:8080/sendTurn") {
                body = Json.encodeToString(turnPlace)
            }
        }
        client.close()
    }

    fun makeTurn(turnPlace: TurnPlace, buttons: List<List<Button>>, turnAuthor: TurnAuthor = TurnAuthor.SERVER) {
        if (buttons[turnPlace.row][turnPlace.column].text != " ") return
        val turnResult = model.makeTurn(turnPlace)
        currentTurn = turnPlace
        buttons[turnPlace.row][turnPlace.column].text = turnResult.sign
        if (isGameFinished(turnResult, buttons)) return

        if (gameMode != GameMode.PLAYER_VS_PLAYER_LOCAL) {
            if (gameMode != GameMode.PLAYER_VS_PLAYER_ONLINE) {
                if (currentBot != null) {
                    val botTurnPlace = currentBot!!.makeTurn(model.getCurrentState())
                    val botTurn = model.makeTurn(botTurnPlace)
                    buttons[botTurnPlace.row][botTurnPlace.column].text = botTurn.sign
                    isGameFinished(botTurn, buttons)
                }
            } else {
                if (turnAuthor == TurnAuthor.CLIENT) sendTurnToServer(turnPlace)
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
