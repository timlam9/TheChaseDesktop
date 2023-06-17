package data

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.gson.*

object ClientBuilder {

    val defaultHttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            gson {

            }
        }
        install(WebSockets) {
            contentConverter = GsonWebsocketContentConverter()
        }
    }
}