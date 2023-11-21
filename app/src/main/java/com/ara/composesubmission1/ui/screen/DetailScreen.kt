package com.ara.composesubmission1.ui.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ara.composesubmission1.di.Injection
import com.ara.composesubmission1.ui.state.UiState
import com.ara.composesubmission1.ui.viewmodel.DetailViewModel
import com.ara.composesubmission1.ui.viewmodel.ViewModelFactory
import com.ara.composesubmission1.R


@Composable
fun DetailScreen(
    artistId: Int,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getArtistById(artistId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailInformation(
                    image = data.image,
                    id = data.id,
                    name = data.name,
                    genre = data.genre,
                    description = data.description,
                    rating = data.rating,
                    isFavorite = data.isFavorite,
                    navigateBack = navigateBack,
                    onFavoriteButtonClicked = { id, state ->
                        viewModel.updateArtist(id, state)
                    }
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailInformation(
    id: Int,
    name: String,
    genre: String,
    @DrawableRes image: Int,
    description: String,
    isFavorite: Boolean,
    navigateBack: () -> Unit,
    onFavoriteButtonClicked: (id: Int, state: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    rating: Double,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
                .background(Color(0xffffc107))
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(296.dp)
                    .testTag("scrollToBottom")
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier
                            .size(16.dp)
                    )
                    Text(
                        text = rating.toString(),
                        modifier = Modifier
                            .padding(start = 2.dp, end = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = genre,
                        modifier = Modifier
                            .padding(start = 2.dp, end = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                }

                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                }
            }

            Divider(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp))

                Text(
                    text = description,
                    fontSize = 16.sp,
                    lineHeight = 28.sp,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            IconButton(
                onClick = navigateBack,
                modifier = Modifier
                    .padding(start = 16.dp, top = 8.dp)
                    .align(Alignment.TopStart)
                    .clip(CircleShape)
                    .size(40.dp)
                    .testTag("back_home")
//                    .background(Color.White)
                    .background(Color(0xffffc107))
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                )
            }
            IconButton(
                onClick = {
                    onFavoriteButtonClicked(id, isFavorite)
                },
                modifier = Modifier
                    .padding(end = 16.dp, top = 8.dp)
                    .align(Alignment.TopEnd)
                    .clip(CircleShape)
                    .size(40.dp)
//                    .background(Color.White)
                    .background(Color(0xffffc107))
                    .testTag("favorite_detail_button")
            ) {
                Icon(
                    imageVector = if (!isFavorite) Icons.Default.FavoriteBorder else Icons.Default.Favorite,
                    contentDescription = if (!isFavorite) stringResource(R.string.add_favorite) else stringResource(
                        R.string.delete_favorite
                    ),
                    tint = if (!isFavorite) Color.Black else Color.Red
                )

        }
    }
}