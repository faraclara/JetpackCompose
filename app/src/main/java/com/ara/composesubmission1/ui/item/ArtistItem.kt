package com.ara.composesubmission1.ui.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp


@Composable
fun ArtistItem(
    id : Int,
    image: Int,
    name : String,
    genre: String,
    isFavorite: Boolean,
    rating: Double,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(Color(0xffffc107))
            .wrapContentHeight(),
        contentAlignment = Alignment.CenterStart
    )

    {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color(0xffffc107))
            ) {
                Image(
                    painter = painterResource(image),
                    contentDescription = "image_artist",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxWidth()
                    .background(Color(0xffffc107))
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold)
                )
                Text(
                    text = genre,
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 2.dp)
                            .size(16.dp)
                    )
                    Text(
                        text = rating.toString(),
                        fontSize = 14.sp,
//                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
        Icon(
            imageVector = if (isFavorite) Icons.Rounded.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = null,
            tint = if (!isFavorite) Color.Black else Color.Red,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .size(24.dp)
                .testTag("item_favorite_button")
                .clickable { onFavoriteIconClicked(id, !isFavorite) }
        )
//        Divider(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp))
    }
    Divider(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp))
}


