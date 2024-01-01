@file:OptIn(ExperimentalMaterial3Api::class)

package com.mehmetpeker.recipe.presentation.main.screens.recipeDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.base.EdgeToEdgeScaffold
import com.mehmetpeker.recipe.designsystem.theme.RoundedCornerShape10Percent
import com.mehmetpeker.recipe.designsystem.theme.RoundedCornerShape30Percent
import com.mehmetpeker.recipe.designsystem.theme.md_theme_light_primary
import com.mehmetpeker.recipe.presentation.main.screens.recipeDetail.components.RecipeCommentsBottomSheetContent
import com.mehmetpeker.recipe.util.extension.horizontalSpace
import com.mehmetpeker.recipe.util.extension.screenHeight
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@Composable
fun RecipeDetailScreen(
    navController: NavController,
    recipeDetailViewModel: RecipeDetailViewModel = koinViewModel()
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(topStartPercent = 10, topEndPercent = 10),
        sheetShadowElevation = 20.dp,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            RecipeCommentsBottomSheetContent(
                modifier = Modifier
                    .requiredHeight(60.screenHeight),
                onSendClick = {

                }
            )
        }
    ) {
        RecipeDetailContent(
            onBookmark = {},
            onLike = {},
            onShare = {},
            onComment = {
                scope.launch {
                    scaffoldState.bottomSheetState.expand()
                }
            }
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailContent(
    onLike: () -> Unit,
    onBookmark: () -> Unit,
    onShare: () -> Unit,
    onComment: () -> Unit
) {
    EdgeToEdgeScaffold(
        topBar = {
            TopAppBar(navigationIcon = {
                IconButton(onClick = { }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
            }, title = {})
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .padding(bottom = 16.dp)
        ) {
            recipeTitle()
            recipeImage(
                modifier = Modifier
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape10Percent)
            )
            recipeShareLikeAndFavoriteRow(
                isLiked = true,
                isBookmarked = true,
                onBookmark = {},
                onLike = {},
                onShare = {},
                onComment = {
                    onComment()
                }
            )
            recipePreparitionAndCookingTimeRow(
                modifier = Modifier.padding(top = 12.dp),
                portion = 1,
                cookingTime = 60,
                preparitionTime = 30
            )
            recipeIngredients(
                modifier = Modifier.fillMaxSize(),
                ingredients = listOf("Ekmek", "Mercimek", "Yumurta")
            )

            recipeDescription(modifier = Modifier.padding(top = 10.dp))
        }
    }
}

private fun LazyListScope.recipeTitle() {
    item {
        Text(
            text = "How to make french toast",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp
            )
        )
    }
}

private fun LazyListScope.recipeDescription(modifier: Modifier = Modifier) {
    item {
        Text(
            modifier = modifier,
            text = stringResource(R.string.how_to_make),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp
            )
        )
    }
    item {
        Text(
            modifier = modifier,
            text = "French toast, known as pain perdu (\"lost bread\") in France, is a breakfast and brunch dish made with old bread slices that are soaked in a milk and egg mixture and fried until golden brown and crispy. Often served with fresh fruit and a glass of orange juice, French toast is one of America's favorite breakfast foods.",
            color = Color.Black.copy(alpha = 0.7f)
        )
    }
}

private fun LazyListScope.recipeImage(modifier: Modifier = Modifier) {
    item {
        SubcomposeAsyncImage(
            model = "https://www.allrecipes.com/thmb/VjVrkCVYaalH2JXogJMoFQ_a-zI=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/7016-french-toast-mfs-010-0e1007bf0b47433abe91f2f0c74e5a27.jpg",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier,
            loading = {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(24.dp)
                        .align(
                            Alignment.Center
                        )
                )
            }
        )
    }
}

private fun LazyListScope.recipeIngredients(modifier: Modifier, ingredients: List<String>) {
    item {
        Text(
            modifier = modifier,
            text = stringResource(R.string.ingredients),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp
            )
        )
    }
    items(ingredients) {
        IngredientsItem(it)
    }
}

private fun LazyListScope.recipeShareLikeAndFavoriteRow(
    isLiked: Boolean,
    isBookmarked: Boolean,
    onLike: () -> Unit,
    onBookmark: () -> Unit,
    onShare: () -> Unit,
    onComment: () -> Unit,
) {
    val likeIcon = when {
        isLiked -> Icons.Default.Favorite
        else -> Icons.Default.FavoriteBorder
    }
    val likeTint = when {
        isLiked -> md_theme_light_primary
        else -> Color(0xffABABAB)
    }
    val favoriteTint = when {
        isBookmarked -> md_theme_light_primary
        else -> Color(0xffABABAB)
    }
    val favoriteText = when {
        isBookmarked -> R.string.saved
        else -> R.string.save
    }
    item {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
                .background(
                    Color(0xffF1F1F1), RoundedCornerShape30Percent
                )
                .padding(vertical = 12.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    likeIcon,
                    contentDescription = null,
                    tint = likeTint,
                    modifier = Modifier.clickable { onLike() }
                )
                2.horizontalSpace()
                Text(
                    text = "326",
                    style = MaterialTheme.typography.labelSmall.copy(Color(0xffABABAB))
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = favoriteTint,
                    modifier = Modifier.clickable { onBookmark() })
                2.horizontalSpace()
                Text(
                    text = stringResource(favoriteText),
                    style = MaterialTheme.typography.labelSmall.copy(Color(0xffABABAB))
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Share,
                    contentDescription = null,
                    tint = Color(0xffABABAB),
                    modifier = Modifier.clickable { onShare() })
                2.horizontalSpace()
                Text(
                    text = stringResource(R.string.share),
                    style = MaterialTheme.typography.labelSmall.copy(Color(0xffABABAB))
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painterResource(id = R.drawable.baseline_comment_24),
                    contentDescription = null,
                    tint = Color(0xffABABAB),
                    modifier = Modifier.clickable { onComment() })
                2.horizontalSpace()
                Text(
                    text = stringResource(R.string.comments),
                    style = MaterialTheme.typography.labelSmall.copy(Color(0xffABABAB))
                )
            }
        }
    }
}

private fun LazyListScope.recipePreparitionAndCookingTimeRow(
    modifier: Modifier,
    portion: Int,
    preparitionTime: Int,
    cookingTime: Int,
) {
    item {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .background(
                    Color(0xffF1F1F1), RoundedCornerShape30Percent
                )
                .padding(vertical = 12.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "$portion",
                    style = MaterialTheme.typography.titleMedium.copy(md_theme_light_primary)
                )
                2.horizontalSpace()
                Text(
                    text = "Porsiyon",
                    style = MaterialTheme.typography.labelSmall.copy(Color(0xffABABAB))
                )
            }
            Divider(
                modifier = Modifier
                    .height(16.dp)
                    .width(1.dp),
                thickness = 1.dp,
                color = Color.LightGray
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "$preparitionTime",
                    style = MaterialTheme.typography.titleMedium.copy(md_theme_light_primary)
                )
                2.horizontalSpace()
                Text(
                    text = "Hazırlama",
                    style = MaterialTheme.typography.labelSmall.copy(Color(0xffABABAB))
                )
            }
            Divider(
                modifier = Modifier
                    .height(16.dp)
                    .width(1.dp),
                thickness = 1.dp,
                color = Color.LightGray
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "$cookingTime",
                    style = MaterialTheme.typography.titleMedium.copy(md_theme_light_primary)
                )
                2.horizontalSpace()
                Text(
                    text = "Pişirme",
                    style = MaterialTheme.typography.labelSmall.copy(Color(0xffABABAB))
                )
            }
        }
    }
}

@Composable
private fun IngredientsItem(name: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = 12.dp)
            .fillMaxWidth()
            .background(
                Color(0xffF1F1F1), RoundedCornerShape30Percent
            )
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        Text(
            text = "Yumurta",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Text(
            text = "3 adet", style = MaterialTheme.typography.titleSmall, modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}