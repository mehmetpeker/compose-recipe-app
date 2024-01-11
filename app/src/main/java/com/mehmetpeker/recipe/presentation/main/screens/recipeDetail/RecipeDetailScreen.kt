@file:OptIn(ExperimentalMaterial3Api::class)

package com.mehmetpeker.recipe.presentation.main.screens.recipeDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.base.EdgeToEdgeScaffold
import com.mehmetpeker.recipe.common.FailScreen
import com.mehmetpeker.recipe.designsystem.theme.RecipeFontFamily
import com.mehmetpeker.recipe.designsystem.theme.RoundedCornerShape10Percent
import com.mehmetpeker.recipe.designsystem.theme.RoundedCornerShape30Percent
import com.mehmetpeker.recipe.designsystem.theme.md_theme_light_primary
import com.mehmetpeker.recipe.presentation.main.screens.addRecipe.uiModel.MaterialsUiModel
import com.mehmetpeker.recipe.presentation.main.screens.recipeDetail.components.RecipeCommentsBottomSheetContent
import com.mehmetpeker.recipe.util.extension.horizontalSpace
import com.mehmetpeker.recipe.util.extension.screenHeight
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@Composable
fun RecipeDetailScreen(
    navController: NavController,
    recipeId: String,
    recipeDetailViewModel: RecipeDetailViewModel = koinViewModel()
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val uiState by recipeDetailViewModel.recipeDetailUiState.collectAsStateWithLifecycle()
    LaunchedEffect(recipeId) {
        recipeDetailViewModel.getRecipeDetail(recipeId = recipeId)
    }
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(topStartPercent = 10, topEndPercent = 10),
        sheetShadowElevation = 20.dp,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            RecipeCommentsBottomSheetContent(
                modifier = Modifier
                    .requiredHeight(60.screenHeight),
                uiState = uiState,
                onSendClick = {

                },
            )
        }
    ) {
        RecipeDetailContent(
            uiState,
            onLike = {

                recipeDetailViewModel.likeRecipe(
                    recipeId,
                    isAlreadyLiked = it
                )
            },
            onShare = {},
            onNavigateClick = {
                navController.popBackStack()
            },
            onTryAgain = {
                recipeDetailViewModel.getRecipeDetail(recipeId = recipeId)
            },
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
    uiState: RecipeDetailViewModel.RecipeDetailUiState,
    onNavigateClick: () -> Unit = {},
    onLike: (isAlreadyLiked: Boolean) -> Unit = {},
    onShare: () -> Unit = {},
    onComment: () -> Unit = {},
    onTryAgain: () -> Unit = {},
) {
    EdgeToEdgeScaffold(
        topBar = {
            TopAppBar(navigationIcon = {
                IconButton(onClick = onNavigateClick) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
            }, title = {})
        }
    ) {
        when (uiState) {
            is RecipeDetailViewModel.RecipeDetailUiState.Success -> {
                RecipeDetailSuccessContent(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                        .padding(bottom = 16.dp),
                    uiState.uiState,
                    onLike = {
                        onLike(uiState.uiState.recipeDetail.recipe?.isCurrentUserLikeRecipe == true)
                    },
                    onShare = onShare, onComment = onComment
                )
            }

            is RecipeDetailViewModel.RecipeDetailUiState.FAILED -> {
                FailScreen(
                    title = "Bir hata oluştu",
                    message = "Tarif detayına ulaşamadık!",
                    onButtonClick = onTryAgain
                ) {
                    Text(
                        text = "Tekrar Dene",
                        style = TextStyle(
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            fontFamily = RecipeFontFamily.poppinsFamily
                        )
                    )
                }
            }

            else -> Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            }
        }
    }
}

@Composable
private fun RecipeDetailSuccessContent(
    modifier: Modifier,
    uiState: RecipeDetailViewModel.RecipeDetailSuccessUiState,
    onLike: () -> Unit = {},
    onShare: () -> Unit = {},
    onComment: () -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
    ) {
        recipeTitle(uiState.recipeDetail.recipe?.name ?: "")
        recipeImage(
            modifier = Modifier
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape10Percent),
            uiState.recipeDetail.recipe?.photoUrl
        )
        recipeShareLikeAndFavoriteRow(
            isLiked = uiState.recipeDetail.recipe?.isCurrentUserLikeRecipe == true,
            likeCount = uiState.recipeDetail.recipe?.likesCount ?: 0,
            onLike = onLike,
            onShare = onShare,
            onComment = onComment
        )
        recipePreparitionAndCookingTimeRow(
            modifier = Modifier.padding(top = 12.dp),
            portion = uiState.recipeDetail.recipe?.portions ?: 0,
            cookingTime = uiState.recipeDetail.recipe?.cookingTime ?: 0,
            preparitionTime = uiState.recipeDetail.recipe?.preparitionTime ?: 0
        )
        recipeIngredients(
            modifier = Modifier.fillMaxSize(),
            ingredients = uiState.recipeDetail.recipe?.materials?.mapNotNull {
                MaterialsUiModel(
                    id = it?.id,
                    measurement = it?.measurement?.unit,
                    amount = it?.measurement?.amount?.toInt() ?: 0,
                    name = it?.name
                )
            } ?: emptyList()
        )

        recipeDescription(
            modifier = Modifier.padding(top = 10.dp),
            description = uiState.recipeDetail.recipe?.description ?: ""
        )
    }
}

private fun LazyListScope.recipeTitle(title: String) {
    item {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp
            )
        )
    }
}

private fun LazyListScope.recipeDescription(modifier: Modifier = Modifier, description: String) {
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
            text = description,
            color = Color.Black.copy(alpha = 0.7f)
        )
    }
}

private fun LazyListScope.recipeImage(modifier: Modifier = Modifier, photoUrl: String?) {
    item {
        SubcomposeAsyncImage(
            model = photoUrl,
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

private fun LazyListScope.recipeIngredients(
    modifier: Modifier,
    ingredients: List<MaterialsUiModel>
) {
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
    likeCount: Int,
    onLike: () -> Unit,
    onShare: () -> Unit,
    onComment: () -> Unit,
) {
    val likeIconAndTintColor: Pair<ImageVector, Color> = when {
        isLiked -> Pair(Icons.Default.Favorite, md_theme_light_primary)
        else -> Pair(Icons.Default.FavoriteBorder, Color(0xffABABAB))
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
                    likeIconAndTintColor.first,
                    contentDescription = null,
                    tint = likeIconAndTintColor.second,
                    modifier = Modifier.clickable { onLike() }
                )
                2.horizontalSpace()
                Text(
                    text = likeCount.toString(),
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

@Composable
private fun RecipeUser(modifier: Modifier = Modifier) {

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
private fun IngredientsItem(model: MaterialsUiModel) {
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
            text = model.name ?: "-",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Text(
            text = model.amount.toString(),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}