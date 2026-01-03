package com.example.smartmenu.speechToText.remotes

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OpenAIClient {
    private const val BASE_URL = "https://api.openai.com/v1/"
    private const val API_KEY = "Bearer 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" // حط هنا مفتاحك

    // Logging عشان نقدر نشوف طلبات الـ API في Logcat
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Interceptor لإضافة Authorization Header لكل طلب
    private val authInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Authorization", API_KEY)
            .build()
        chain.proceed(request)
    }

    // إنشاء OkHttpClient
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(authInterceptor)
        .build()

    // إنشاء Retrofit instance
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
// Bearer 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