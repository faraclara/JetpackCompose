package com.ara.composesubmission1.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ara.composesubmission1.R


@Composable
fun AboutScreen(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xffffc107))

    ) {
        Image(
            painter = painterResource(R.drawable.pict),
            contentDescription = stringResource(R.string.profile_name),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(
                    top = 56.dp,
                    bottom = 28.dp
                )
                .size(200.dp)
                .clip(CircleShape)
        )
        Text(
            text = stringResource(R.string.profile_name),
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            modifier = modifier
                .fillMaxWidth()
        )
        Text(
            text = stringResource(R.string.profile_email),
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            modifier = modifier
                .padding(
                    top = 14.dp
                )
                .fillMaxWidth()
        )
    }
}