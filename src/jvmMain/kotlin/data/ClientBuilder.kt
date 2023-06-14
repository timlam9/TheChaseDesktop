package data

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.gson.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object ClientBuilder {

    val defaultHttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                }
            )
        }
        install(WebSockets) {
            contentConverter = GsonWebsocketContentConverter()
        }
    }
}