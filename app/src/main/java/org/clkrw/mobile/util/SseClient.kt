package org.clkrw.mobile.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.time.Duration
import kotlin.coroutines.coroutineContext
import kotlin.reflect.KClass

class SseClient(
    client: OkHttpClient,
    private val baseUrl: String,
) {
    data class Event(var name: String = "", var data: String = "")

    private val client = client
        .newBuilder()
        .readTimeout(Duration.ZERO)
        .build()

    inline fun <reified T : Any> getEventsFlow(url: String, eventType: String) =
        getEventsFlow(url, eventType, T::class)

    fun <T : Any> getEventsFlow(url: String, eventType: String, kClass: KClass<T>): Flow<T> = flow {
        val serializer = serializer(kClass.javaObjectType)

        // Gets HttpURLConnection. Blocking function.  Should run in background
        val request = Request.Builder()
            .method("GET", null)
            .addHeader("Accept", "text/event-stream")
            .url("$baseUrl$url")
            .build()


        val inputReader = client.newCall(request).execute().body!!.byteStream().bufferedReader()

        val event = Event()

        // run forever
        try {
            while (coroutineContext.isActive) {
                val line =
                    inputReader.readLine() // Blocking function. Read stream until \n is found

                when {
                    line.startsWith("event:") -> { // get event name
                        event.name = line.substring(6).trim()
                    }

                    line.startsWith("data:") -> { // get data
                        event.data = line.substring(5).trim()
                    }

                    line.isEmpty() -> { // empty line, finished block. Emit the event
                        if (event.name == eventType) {
                            @Suppress("UNCHECKED_CAST")
                            val deserialized = Json.decodeFromString(serializer, event.data) as T
                            emit(deserialized)
                            event.data = ""
                            event.name = ""
                        }
                    }
                }
            }
        } catch (_: IOException) {
        }
    }.flowOn(Dispatchers.IO)
}