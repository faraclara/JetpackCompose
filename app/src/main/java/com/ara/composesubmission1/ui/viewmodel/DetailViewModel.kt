package com.ara.composesubmission1.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ara.composesubmission1.data.ArtistRepository
import com.ara.composesubmission1.modeldata.Artist
import com.ara.composesubmission1.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel (
    private val repository: ArtistRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Artist>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Artist>>
        get() = _uiState

    fun getArtistById(id: Int) = viewModelScope.launch {
        _uiState.value = UiState.Loading
        _uiState.value = UiState.Success(repository.getArtistById(id))
    }


    fun updateArtist(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateArtist(id, !newState)
            .collect { isUpdated ->
                if (isUpdated) getArtistById(id)
            }
    }
}