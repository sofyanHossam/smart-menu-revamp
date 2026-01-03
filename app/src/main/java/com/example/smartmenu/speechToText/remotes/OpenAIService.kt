package com.example.smartmenu.speechToText.remotes

import retrofit2.http.Body
import retrofit2.http.POST

// نموذج الرسالة
data class Message(
    val role: String,  // "user" أو "system"
    val content: String
)

// نموذج طلب ChatGPT
data class ChatRequest(
    val model: String,          // زي "gpt-3.5-turbo"
    val messages: List<Message>
)

// نموذج الرد
data class ChatResponse(
    val id: String,
    val choices: List<Choice>
)

data class Choice(
    val message: Message
)

// واجهة Retrofit
interface OpenAIService {
    @POST("chat/completions")
    suspend fun sendMessage(@Body request: ChatRequest): ChatResponse
}
