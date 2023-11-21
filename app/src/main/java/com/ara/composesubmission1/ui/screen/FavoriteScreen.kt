package com.ara.composesubmission1.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ara.composesubmission1.R
import com.ara.composesubmission1.di.Injection
import com.ara.composesubmission1.modeldata.Artist
import com.ara.composesubmission1.ui.item.EmptyList
import com.ara.composesubmission1.ui.state.UiState
import com.ara.composesubmission1.ui.viewmodel.FavoriteViewModel
import com.ara.composesubmission1.ui.viewmodel.ViewModelFactory


@Composable
fun FavoriteScreen(
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFavoritePlayer()
            }
            is UiState.Success -> {
                FavoriteInformation(
                    listArtist = uiState.data,
                    navigateToDetail = navigateToDetail,
                    onFavoriteIconClicked = { id, newState ->
                        viewModel.updatePlayer(id, newState)
                    }
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun FavoriteInformation(
    listArtist: List<Artist>,
    navigateToDetail: (Int) -> Unit,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(Color(0xffffc107))
    ) {
        if (listArtist.isNotEmpty()) {
            ListArtist(
                listArtist = listArtist,
                onFavoriteIconClicked = onFavoriteIconClicked,
                contentPaddingTop = 16.dp,
                navigateToDetail = navigateToDetail
            )
        } else {
            EmptyList(
                Warning = stringResource(R.string.empty_favorite)
            )
        }
    }
}