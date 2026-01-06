package com.example.smartmenu.imageToText

data class VisionRequest(
    val requests: List<ImageRequest>
)

data class ImageRequest(
    val image: ImageContent,
    val features: List<FeatureType>
)

data class ImageContent(val content: String)

data class FeatureType(val type: String)
