package com.example.smartmenu.imageToText

import android.graphics.Bitmap
import android.util.Base64
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class MenuViewModel : ViewModel() {

    var uiState by mutableStateOf<MenuUiState>(MenuUiState.Idle)
        private set

    fun processImage(bitmap: Bitmap) {
        viewModelScope.launch {
            uiState = MenuUiState.Loading

            try {
                val base64 = bitmap.toBase64()
                val request = VisionRequest(
                    requests = listOf(
                        ImageRequest(
                            image = ImageContent(base64),
                            features = listOf(FeatureType("TEXT_DETECTION"))
                        )
                    )
                )

                val response = VisionClient.service.annotate(
                    apiKey = "AIzaSyCZYPUFTKfU5pAexS_HwDg5Nfg6YsWwOSs",
                    body = request
                )

                val text = response.responses
                    .firstOrNull()
                    ?.textAnnotations
                    ?.firstOrNull()
                    ?.description ?: ""

                val items = text
                    .lines()
                    .mapNotNull { parseLine(it) }

                uiState = MenuUiState.Success(items)

            } catch (e: Exception) {
                uiState = MenuUiState.Error(e.localizedMessage ?: "Vision API Error")
            }
        }
    }
}
fun Bitmap.toBase64(): String {
    val stream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, 90, stream)
    val bytes = stream.toByteArray()
    return Base64.encodeToString(bytes, Base64.NO_WRAP)
}
private fun parseLine(line: String): MenuItem? {
    val tokens = line.split(" ")

    val nameParts = mutableListOf<String>()
    val prices = mutableListOf<Int>()

    tokens.forEach { token ->
        val clean = token.replace("[^0-9]".toRegex(), "")
        if (clean.isNotEmpty()) {
            prices.add(clean.toInt())
        } else {
            nameParts.add(token)
        }
    }

    if (nameParts.isEmpty() || prices.isEmpty()) return null

    return MenuItem(
        name = nameParts.joinToString(" "),
        prices = prices
    )
}


