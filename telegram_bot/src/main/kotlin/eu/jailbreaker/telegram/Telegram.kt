package eu.jailbreaker.telegram

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.launch
import java.util.concurrent.CompletableFuture

class Telegram {

    private lateinit var client: HttpClient

    fun connect(): CompletableFuture<Void> = CompletableFuture.runAsync {
        client = HttpClient {
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
        }
    }

    fun disconnect(): CompletableFuture<Void> = CompletableFuture.runAsync { client.close() }

    fun sendMessage(chatId: String, message: String) {
        client.launch {
            try {
                client.post("http://telegram-api:8080/sendMessage") {
                    body = TelegramMessage(chatId, message)
                    contentType(ContentType.Application.Json)
                }
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }

    fun changeChatTitle(chatId: String, title: String) {
        client.launch {
            try {
                client.post("http://telegram-api:8080/changeChatTitle") {
                    body = TelegramMessage(chatId, title)
                    contentType(ContentType.Application.Json)
                }
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }
}