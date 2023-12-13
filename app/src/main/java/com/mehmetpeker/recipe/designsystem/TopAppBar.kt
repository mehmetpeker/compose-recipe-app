package com.mehmetpeker.recipe.designsystem

import androidx.annotation.StringRes
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mehmetpeker.recipe.designsystem.theme.RecipeFontFamily
import com.mehmetpeker.recipe.designsystem.theme.RecipeTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeTopAppBar(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    navigationIcon: ImageVector? = null,
    navigationIconContentDescription: String?,
    actionIcon: ImageVector? = null,
    actionIconContentDescription: String? = null,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    onNavigationClick: () -> Unit = {},
    onActionClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = titleRes),
                fontFamily = RecipeFontFamily.poppinsFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                if (navigationIcon != null) {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = navigationIconContentDescription,
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = onActionClick) {
                if (actionIcon != null) {
                    Icon(
                        imageVector = actionIcon,
                        contentDescription = actionIconContentDescription,
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        },
        colors = colors,
        modifier = modifier.testTag("recipeTopAppBar"),
    )
}