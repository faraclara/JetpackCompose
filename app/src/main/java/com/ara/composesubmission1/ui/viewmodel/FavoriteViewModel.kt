package com.ara.composesubmission1.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ara.composesubmission1.data.ArtistRepository
import com.ara.composesubmission1.modeldata.Artist
import com.ara.composesubmission1.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel (
    private val repository: ArtistRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Artist>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Artist>>>
        get() = _uiState

    fun getFavoritePlayer() = viewModelScope.launch {
        repository.getFavoriteArtist()
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    fun updatePlayer(id: Int, newState: Boolean) {
        repository.updateArtist(id, newState)
        getFavoritePlayer()
    }
}