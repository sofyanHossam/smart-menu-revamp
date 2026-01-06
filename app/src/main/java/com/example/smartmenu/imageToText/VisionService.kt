package com.example.smartmenu.imageToText

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface VisionService {
    @POST("v1/images:annotate")
    suspend fun annotate(
        @Query("key") apiKey: String,
        @Body body: VisionRequest
    ): VisionResponse
}
