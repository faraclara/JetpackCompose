package com.ara.composesubmission1.modeldata

data class Artist(
    val id: Int,
    val name: String,
    val genre: String,
    val image: Int,
    val description: String,
    val rating: Double,
    var isFavorite: Boolean = false
)
