package com.mehmetpeker.recipe.presentation.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.util.extension.horizontalSpace

@Composable
fun HomeTopBar(
    modifier: Modifier,
    profileImageUrl: String?,
    username: String?
) {
    val imageUrl = when {
        profileImageUrl.isNullOrEmpty() ->
            "https://pbs.twimg.com/profile_images/1423037075264315404/9Tyzo0Lw_400x400.jpg"

        else -> profileImageUrl
    }
    Row(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            model = imageUrl,
            contentDescription = null,
        )
        4.horizontalSpace()
        Column(verticalArrangement = Arrangement.Center) {
            Text(
                stringResource(id = R.string.hello_username, username ?: "---"),
                style = MaterialTheme.typography.titleMedium.copy(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    ), textAlign = TextAlign.Center, lineHeight = 0.sp, color = Color.White
                )
            )
            Text(
                stringResource(id = R.string.check_amazing_recipe),
                style = MaterialTheme.typography.titleSmall.copy(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    ), textAlign = TextAlign.Center, lineHeight = 0.sp, color = Color.White
                )
            )
        }
    }
}