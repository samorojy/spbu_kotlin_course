@file:Suppress("MagicNumber")

package homework8.model

import homework8.controller.Controller
import homework8.controller.TurnAuthor
import homework8.controller.TurnPlace
import homework8.controller.TurnStage
import io.ktor.client.HttpClient
import io.ktor.client.features.websocket.DefaultClientWebSocketSession
import io.ktor.client.features.websocket.WebSockets
import io.ktor.client.features.websocket.webSocket
import io.ktor.http.HttpMethod
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.readText
import io.ktor.http.cio.websocket.send
import javafx.scene.control.Button
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PlayWithPlayerOnlineModel(val controller: Controller, gameSize: Int, gameField: List<List<Button>>) :
    Model(gameSize) {
    var isMyTurn = true
    private val client = HttpClient { install(WebSockets) }
    private var session: DefaultClientWebSocketSession? = null

    init {
        val updateStateThread = Thread { updateCurrentState(gameField) }
        updateStateThread.start()
        Thread.sleep(15)
    }

    private suspend fun DefaultClientWebSocketSession.getTurn(buttons: List<List<Button>>) {
        try {
            for (jsonTurnPlace in incoming) {
                jsonTurnPlace as? Frame.Text ?: continue
                val string = jsonTurnPlace.readText()
                val turnPlace = Json.decodeFromString<TurnPlace>(string)
                val turnResult = move(turnPlace, buttons, TurnAuthor.SERVER)
                if (turnResult.isGameOver) controller.finishGame(turnResult.turnStage, buttons, TurnAuthor.SERVER)
            }
        } catch (e: SerializationException) {
            println("Error while receiving: " + e.localizedMessage)
        }
    }

    @Serializable
    data class ConnectionInfo(val host: String, val port: Int, val path: String)

    private fun updateCurrentState(buttons: List<List<Button>>) {
        val connectionInfo =
            Json.decodeFromString<ConnectionInfo>(javaClass.getResource("ConnectionInfo.json").readText())
        runBlocking {
            client.webSocket(
                method = HttpMethod.Get,
                host = connectionInfo.host,
                port = connectionInfo.port,
                path = connectionInfo.path
            ) {
                session = this
                val gettingTurn = launch { getTurn(buttons) }
                gettingTurn.join()
            }
        }
    }

    private suspend fun DefaultClientWebSocketSession.sendTurn(turnPlace: TurnPlace) {
        try {
            send(Json.encodeToString(turnPlace))
        } catch (e: SerializationException) {
            println("Error while sending: " + e.localizedMessage)
            return
        }
    }

    override fun move(turnPlace: TurnPlace, gameField: List<List<Button>>, turnAuthor: TurnAuthor): MoveResult =
        when {
            turnAuthor == TurnAuthor.CLIENT && isMyTurn -> {
                runBlocking {
                    val sendingTurn = launch { session?.sendTurn(turnPlace) }
                    sendingTurn.join()
                }
                isMyTurn = false
                makeTurn(turnPlace, gameField, turnAuthor)
            }
            turnAuthor == TurnAuthor.SERVER -> {
                isMyTurn = true
                makeTurn(turnPlace, gameField, turnAuthor)
            }
            else -> MoveResult(TurnStage.NO_WINNER_YET_X, false)
        }
}
