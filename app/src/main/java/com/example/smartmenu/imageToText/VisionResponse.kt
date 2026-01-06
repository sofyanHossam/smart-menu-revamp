package com.example.smartmenu.imageToText

data class VisionResponse(
    val responses: List<AnnotateImageResponse>
)

data class AnnotateImageResponse(
    val textAnnotations: List<TextAnnotation>?
)

data class TextAnnotation(val description: String?)
