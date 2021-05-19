package homework8.controller

import homework8.view.FinishView
import homework8.view.GameView
import homework8.view.StartView
import homework8.bots.hard.BotHard
import homework8.bots.BotInterface
import homework8.bots.simple.BotSimple
import homework8.model.Model
import io.ktor.client.HttpClient
import io.ktor.client.features.websocket.DefaultClientWebSocketSession
import io.ktor.client.features.websocket.WebSockets
import io.ktor.client.features.websocket.webSocket
import io.ktor.http.HttpMethod
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.send
import javafx.application.Platform
import javafx.scene.control.Button
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import tornadofx.Controller
import tornadofx.borderpane
import tornadofx.box
import tornadofx.px
import tornadofx.style
import tornadofx.label
import java.lang.IllegalArgumentException

@Suppress("TooManyFunctions")
class Controller : Controller() {
    private var model = Model()
    private var gameMode = GameMode.PLAYER_VS_PLAYER_ONLINE
    private var currentBot: BotInterface? = null
    private val client = HttpClient {
        install(WebSockets)
    }

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

    @Suppress("TooGenericExceptionCaught")
    private suspend fun DefaultClientWebSocketSession.sendTurn(turnPlace: TurnPlace) {
        try {
            send(Json.encodeToString(turnPlace))
            send("exit")
        } catch (e: Exception) {
            println("Error while sending: " + e.localizedMessage)
            return
        }
    }

    @Suppress("TooGenericExceptionCaught")
    private suspend fun DefaultClientWebSocketSession.getTurn(buttons: List<List<Button>>) {
        try {
            for (jsonTurnPlace in incoming) {
                jsonTurnPlace as? Frame.Text ?: continue
                val string = jsonTurnPlace.readText()
                val turnPlace = Json.decodeFromString<TurnPlace>(string)
                makeTurn(turnPlace, buttons)
            }
        } catch (e: Exception) {
            println("Error while receiving: " + e.localizedMessage)
        }
    }

    fun getCurrentState(buttons: List<List<Button>>) {
        if (gameMode == GameMode.PLAYER_VS_PLAYER_ONLINE) {
            runBlocking {
                client.webSocket(method = HttpMethod.Get, host = "127.0.0.1", port = 8080, path = "/play") {
                    val gettingTurn = launch { getTurn(buttons) }
                    gettingTurn.join()
                }
            }
        }
    }

    fun makeTurn(turnPlace: TurnPlace, buttons: List<List<Button>>, turnAuthor: TurnAuthor = TurnAuthor.SERVER) {
        if (buttons[turnPlace.row][turnPlace.column].text != " ") {
            return
        }
        val turnResult = model.makeTurn(turnPlace)

        if (turnAuthor == TurnAuthor.SERVER) {
            Platform.runLater { buttons[turnPlace.row][turnPlace.column].text = turnResult.sign }
        } else {
            buttons[turnPlace.row][turnPlace.column].text = turnResult.sign
            if (gameMode == GameMode.PLAYER_VS_PLAYER_ONLINE) {
                runBlocking {
                    client.webSocket(method = HttpMethod.Get, host = "127.0.0.1", port = 8080, path = "/play") {
                        val sendingTurn = launch { sendTurn(turnPlace) }
                        sendingTurn.join()
                    }
                }
            }
        }

        if (isGameFinished(turnResult, buttons, turnAuthor)) return

        if (gameMode != GameMode.PLAYER_VS_PLAYER_LOCAL ||
            gameMode != GameMode.PLAYER_VS_PLAYER_ONLINE
        ) {
            if (currentBot != null) {
                val botTurnPlace = currentBot!!.makeTurn(model.getCurrentState())
                val botTurn = model.makeTurn(botTurnPlace)
                buttons[botTurnPlace.row][botTurnPlace.column].text = botTurn.sign
                isGameFinished(botTurn, buttons)
            }
        }
    }

    private fun isGameFinished(
        turnResult: TurnStage,
        buttons: List<List<Button>>,
        turnAuthor: TurnAuthor = TurnAuthor.CLIENT
    ): Boolean {
        if (turnResult == TurnStage.DRAW || turnResult == TurnStage.WIN_0 || turnResult == TurnStage.WIN_X) {
            finishGame(turnResult, buttons, turnAuthor)
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

    private fun finishGame(
        winningStage: TurnStage,
        buttons: List<List<Button>>,
        turnAuthor: TurnAuthor
    ) {

        if (turnAuthor == TurnAuthor.CLIENT) {
            find<GameView>().replaceWith<FinishView>()
            buttons.forEach { list -> list.forEach { it.text = " " } }
            printWinner(winningStage)
        } else {
            Platform.runLater { find<GameView>().replaceWith<FinishView>() }
            buttons.forEach { list -> list.forEach { Platform.runLater { it.text = " " } } }
            Platform.runLater { printWinner(winningStage) }
        }
    }

    private fun printWinner(winningStage: TurnStage) {
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
