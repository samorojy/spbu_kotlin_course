package homework8.model

import homework8.controller.Controller
import homework8.controller.TurnAuthor
import homework8.controller.TurnPlace
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
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.concurrent.thread

class PlayWithPlayerOnlineModel(val controller: Controller, gameSize: Int, gameField: List<List<Button>>) :
    Model(gameSize) {

    private val client = HttpClient { install(WebSockets) }
    private var session: DefaultClientWebSocketSession? = null

    init {
        thread(isDaemon = true) { getCurrentState(gameField) }
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

    private fun getCurrentState(buttons: List<List<Button>>) {
        runBlocking {
            client.webSocket(method = HttpMethod.Get, host = "127.0.0.1", port = 8080, path = "/play") {
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

    override fun move(turnPlace: TurnPlace, gameField: List<List<Button>>, turnAuthor: TurnAuthor): MoveResult {

        if (turnAuthor == TurnAuthor.CLIENT) {
            runBlocking {
                val sendingTurn = launch { session?.sendTurn(turnPlace) }
                sendingTurn.join()
            }
        }
        return makeTurn(turnPlace, gameField)
    }
}
