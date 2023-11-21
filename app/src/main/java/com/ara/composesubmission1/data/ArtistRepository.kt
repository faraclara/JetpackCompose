package com.ara.composesubmission1.data

import com.ara.composesubmission1.modeldata.Artist
import com.ara.composesubmission1.modeldata.ArtistData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class ArtistRepository {
    private val dummyArtist = mutableListOf<Artist>()

    init {
        if (dummyArtist.isEmpty()) {
            ArtistData.dummyArtist.forEach {
                dummyArtist.add(it)
            }
        }
    }

    fun getArtistById(artistId: Int): Artist {
        return dummyArtist.first {
            it.id == artistId
        }
    }

    fun getFavoriteArtist(): Flow<List<Artist>> {
        return flowOf(dummyArtist.filter { it.isFavorite })
    }

    fun searchArtist(query: String) = flow {
        val data = dummyArtist.filter {
            it.name.contains(query, ignoreCase = true)
        }
        emit(data)
    }

    fun updateArtist(artisId: Int, newState: Boolean): Flow<Boolean> {
        val index = dummyArtist.indexOfFirst { it.id == artisId }
        val result = if (index >= 0) {
            val artist = dummyArtist[index]
            dummyArtist[index] = artist.copy(isFavorite = newState)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    companion object {
        @Volatile
        private var instance: ArtistRepository? = null

        fun getInstance(): ArtistRepository =
            instance ?: synchronized(this) {
                ArtistRepository().apply {
                    instance = this
                }
            }
    }
}