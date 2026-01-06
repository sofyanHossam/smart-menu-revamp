package com.example.smartmenu.imageToText

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object VisionClient {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://vision.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val service: VisionService by lazy {
        retrofit.create(VisionService::class.java)
    }
}
