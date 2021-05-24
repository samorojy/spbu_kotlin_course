package homework8.server

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.http.cio.websocket.DefaultWebSocketSession
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.readText
import io.ktor.http.cio.websocket.send
import io.ktor.routing.routing
import io.ktor.websocket.WebSockets
import io.ktor.websocket.webSocket

fun Application.module() {
    install(WebSockets)
    routing {
        val connections = mutableListOf<DefaultWebSocketSession>()

        webSocket("/play") {
            connections.add(this)
            for (frame in incoming) {
                frame as? Frame.Text ?: continue
                val receivedText = frame.readText()
                if (receivedText == "exit") {
                    connections.remove(this)
                } else {
                    connections.forEach {
                        it.send(receivedText)
                    }
                }
            }
        }
    }
}

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)
