package com.example.ucbpos.model

data class HomeResponse(
    val logo: String,
    val appName: String,
    val appNameSubTitle: String,
    val sliderMedia: List<SliderMedia>
)