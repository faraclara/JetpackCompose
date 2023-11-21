package com.ara.composesubmission1.di

import com.ara.composesubmission1.data.ArtistRepository

object Injection {
    fun provideRepository(): ArtistRepository {
        return ArtistRepository.getInstance()
    }
}