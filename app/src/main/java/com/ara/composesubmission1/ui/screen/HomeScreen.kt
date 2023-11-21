package com.ara.composesubmission1.ui.screen

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ara.composesubmission1.R
import com.ara.composesubmission1.di.Injection
import com.ara.composesubmission1.modeldata.Artist
import com.ara.composesubmission1.ui.item.ArtistItem
import com.ara.composesubmission1.ui.item.EmptyList
import com.ara.composesubmission1.ui.item.Search
import com.ara.composesubmission1.ui.state.UiState
import com.ara.composesubmission1.ui.viewmodel.HomeViewModel
import com.ara.composesubmission1.ui.viewmodel.ViewModelFactory


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Int) -> Unit,
) {
    val query by viewModel.query
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.search(query)
            }
            is UiState.Success -> {
                HomeContent(
                    query = query,
                    onQueryChange = viewModel::search,
                    listArtist = uiState.data,
                    onFavoriteIconClicked = { id, newState ->
                        viewModel.updateArtist(id, newState)
                    },
                    navigateToDetail = navigateToDetail
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    query: String,
    onQueryChange: (String) -> Unit,
    listArtist: List<Artist>,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
) {
    Column {
        Search(
            query = query,
            onQueryChange = onQueryChange,
            modifier = modifier
                .background(Color(0xffffc107))

        )
        if (listArtist.isNotEmpty()) {
            ListArtist(
                listArtist = listArtist,
                onFavoriteIconClicked = onFavoriteIconClicked,
                navigateToDetail = navigateToDetail
            )
        } else {
            EmptyList(
                Warning = stringResource(R.string.empty_data),
                modifier = Modifier
                    .testTag("emptyList")
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListArtist(
//    Surface (color = MaterialTheme.colors.primary,
    listArtist: List<Artist>,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
    contentPaddingTop: Dp = 0.dp,

) {
    LazyColumn(
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp,
            top = contentPaddingTop
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .background(Color(0xffffc107))
            .testTag("lazy_list")
    ) {
        items(listArtist, key = { it.id }) { item ->
            ArtistItem(
                id = item.id,
                name = item.name,
                genre = item.genre,
                image = item.image,
                rating = item.rating,
                isFavorite = item.isFavorite,
                onFavoriteIconClicked = onFavoriteIconClicked,
                modifier = Modifier
                    .animateItemPlacement(tween(durationMillis = 200))
                    .clickable { navigateToDetail(item.id) }
            )
        }
    }
}
