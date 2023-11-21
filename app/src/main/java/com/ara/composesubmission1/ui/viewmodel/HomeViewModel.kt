package com.ara.composesubmission1.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ara.composesubmission1.data.ArtistRepository
import com.ara.composesubmission1.modeldata.Artist
import com.ara.composesubmission1.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel (
    private val repository: ArtistRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Artist>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Artist>>>
        get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) = viewModelScope.launch {
        _query.value = newQuery
        repository.searchArtist(_query.value)
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    fun updateArtist(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateArtist(id, newState)
            .collect { isUpdated ->
                if (isUpdated) search(_query.value)
            }
    }
}