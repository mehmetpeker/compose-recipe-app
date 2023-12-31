@file:OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)

package com.mehmetpeker.recipe.presentation.main.screens.recipeDetail.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mehmetpeker.recipe.R
 /*
 * Example
 *  val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(topStartPercent = 10, topEndPercent = 10),
        sheetShadowElevation = 20.dp,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            RecipeCommentsBottomSheetContent(
                modifier = Modifier
                    .requiredHeight(60.screenHeight)
            )
        }
    ) {
        Scaffold content
    }
 *
 *
 * */
@Composable
fun RecipeCommentsBottomSheetContent(modifier: Modifier = Modifier) {
    val lazyListState = rememberLazyListState()
    val flingBehavior = rememberSnapFlingBehavior(lazyListState)
    val commentList = (1..50).map { it.toString() }
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.comments),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold
        )
        Divider(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            thickness = 0.5.dp,
            color = Color.Gray
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(), flingBehavior = flingBehavior,
            state = lazyListState,
            contentPadding = PaddingValues(10.dp)
        ) {
            items(commentList) {
                CommentItem()
            }
        }
    }
}

@Composable
fun CommentItem() {
    Row(Modifier.padding(top = 8.dp)) {
        AsyncImage(
            model = "https://pbs.twimg.com/profile_images/1423037075264315404/9Tyzo0Lw_400x400.jpg",
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
        )
        Column(modifier = Modifier.padding(horizontal = 10.dp)) {
            Text(text = "mehmetpeker", style = MaterialTheme.typography.titleSmall)
            Text(
                text = "Harika bir tarif,bu uygulamayı yapanlara teşekkür ederim böyle tariflere erişebiliyoruz <3 <3",
                style = MaterialTheme.typography.labelSmall.copy(color = Color.DarkGray),
                modifier = Modifier.padding(top = 5.dp)
            )
        }
    }
}

