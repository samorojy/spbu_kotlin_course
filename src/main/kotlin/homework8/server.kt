package homework8

import homework8.controller.TurnPlace
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Application.configureRouting() {

    routing {
        var currentTurn = TurnPlace(-1, -1)

        post("/sendTurn") {
            currentTurn = Json.decodeFromString(call.receive())
            call.respondText { "TurnPlace = $currentTurn " }
        }

        get("/getTurn") {
            call.respondText { Json.encodeToString(currentTurn) }
        }
    }
}

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
    }.start(wait = true)
}
