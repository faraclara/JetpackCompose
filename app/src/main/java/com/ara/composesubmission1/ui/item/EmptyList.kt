package com.ara.composesubmission1.ui.item

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ara.composesubmission1.ui.theme.ComposeSubmission1Theme


@Composable
fun EmptyList(
    Warning: String,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = Warning,
        )
    }
}


@Composable
@Preview(showBackground = true)
fun EmptyList() {
    ComposeSubmission1Theme {
        EmptyList()
    }
}