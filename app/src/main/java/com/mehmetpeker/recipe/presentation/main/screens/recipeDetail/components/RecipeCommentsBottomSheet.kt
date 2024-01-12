@file:OptIn(
    ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)

package com.mehmetpeker.recipe.presentation.main.screens.recipeDetail.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.data.entity.recipe.recipeComments.RecipeCommentsResponseItem
import com.mehmetpeker.recipe.designsystem.theme.md_theme_light_primary
import com.mehmetpeker.recipe.util.SessionManager
import org.koin.compose.koinInject

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
fun RecipeCommentsBottomSheetContent(
    modifier: Modifier = Modifier,
    sessionManager: SessionManager = koinInject(),
    onSendClick: (comment: String) -> Unit,
    commentList: List<RecipeCommentsResponseItem> = emptyList()
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    val lazyListState = rememberLazyListState()
    val flingBehavior = rememberSnapFlingBehavior(lazyListState)
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
        if (commentList.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Bu tarife hiç yorum yapılmadı ilk yorum yapan sen ol",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f), flingBehavior = flingBehavior,
                state = lazyListState,
                contentPadding = PaddingValues(10.dp),
                reverseLayout = true
            ) {
                items(commentList) {
                    CommentItem(it)
                }
            }
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xffF1F1F1))
            .navigationBarsPadding()
            .heightIn(min = 60.dp)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
            model = sessionManager.user.profilePhotoUrl,
            contentDescription = null
        )
        BasicTextField(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
                .heightIn(min = 60.dp)
                .weight(1f),
            value = textFieldValue,
            onValueChange = {
                textFieldValue = it
            },
            singleLine = true
        ) {
            Box(contentAlignment = Alignment.CenterStart) {
                if (textFieldValue.text.isEmpty()) {
                    Text(
                        text = "Tarif için bir yorum ekle",
                        style = MaterialTheme.typography.labelMedium
                    )
                } else {
                    Text(
                        text = textFieldValue.text,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

        }
        IconButton(
            modifier = Modifier
                .requiredSize(24.dp)
                .alpha(if (textFieldValue.text.isNotEmpty()) 1f else 0f),
            onClick = {
                onSendClick(textFieldValue.text)
                textFieldValue = TextFieldValue("")
            }
        ) {
            Icon(Icons.Default.Send, contentDescription = null, tint = md_theme_light_primary)
        }
    }
}

@Composable
fun CommentItem(comment: RecipeCommentsResponseItem) {
    val profileImageUrl = comment.user?.profilePhoto
    Row(Modifier.padding(top = 8.dp)) {
        AsyncImage(
            model = profileImageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
        )
        Column(modifier = Modifier.padding(horizontal = 10.dp)) {
            Text(text = comment.user?.username ?: "-", style = MaterialTheme.typography.titleSmall)
            Text(
                text = comment.content ?: "-",
                style = MaterialTheme.typography.labelSmall.copy(color = Color.DarkGray),
                modifier = Modifier.padding(top = 5.dp)
            )
        }
    }
}

