package com.mehmetpeker.recipe.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.mehmetpeker.recipe.designsystem.theme.md_theme_light_onPrimary
import com.mehmetpeker.recipe.designsystem.theme.md_theme_light_primary

sealed interface RecipeOutlinedButtonType {
    data object Primary : RecipeOutlinedButtonType
    data object Secondary : RecipeOutlinedButtonType

}

sealed interface RecipeRoundedButtonType {
    data object Primary : RecipeRoundedButtonType
    data object Secondary : RecipeRoundedButtonType
}

@Composable
private fun RecipeButtonContent(
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    endIcon: @Composable (() -> Unit)? = null,
    shape: Shape = RoundedCornerShape(8.dp),
) {
    if (leadingIcon != null) {
        Box(Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize)) {
            leadingIcon()
        }
    }
    if (endIcon != null) {
        Box(Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize)) {
            endIcon()
        }
    }
    Box(
        Modifier
            .padding(
                start = if (leadingIcon != null) {
                    ButtonDefaults.IconSpacing
                } else {
                    0.dp
                },
                end = if (endIcon != null) {
                    ButtonDefaults.IconSpacing
                } else {
                    0.dp
                }
            ),
    ) {
        text()
    }
}

@Composable
fun RecipeOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    shape: Shape = RoundedCornerShape(8.dp),
    type: RecipeOutlinedButtonType = RecipeOutlinedButtonType.Primary,
    content: @Composable RowScope.() -> Unit,
) {
    val color = when (type) {
        RecipeOutlinedButtonType.Primary -> MaterialTheme.colorScheme.primary
        RecipeOutlinedButtonType.Secondary -> MaterialTheme.colorScheme.onPrimary
    }
    val borderColor = when (type) {
        RecipeOutlinedButtonType.Primary -> Color.Unspecified
        RecipeOutlinedButtonType.Secondary -> MaterialTheme.colorScheme.outline
    }
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = color
        ),
        border = BorderStroke(
            width = RecipeButtonDefaults.OutlinedButtonBorderWidth,
            color = if (enabled) {
                borderColor
            } else {
                MaterialTheme.colorScheme.onSurface.copy(
                    alpha = RecipeButtonDefaults.DisabledOutlinedButtonBorderAlpha,
                )
            },
        ),
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
fun RecipeOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
    shape: Shape = RoundedCornerShape(8.dp),
    leadingIcon: @Composable (() -> Unit)? = null,
    endIcon: @Composable (() -> Unit)? = null,
) {
    RecipeOutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = if (leadingIcon != null || endIcon != null) {
            ButtonDefaults.ButtonWithIconContentPadding
        } else {
            ButtonDefaults.ContentPadding
        },
    ) {
        RecipeButtonContent(
            text = text,
            leadingIcon = leadingIcon,
            shape = shape
        )
    }
}

@Composable
fun RecipeRoundedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    shape: Shape = RoundedCornerShape(percent = 50),
    type: RecipeRoundedButtonType = RecipeRoundedButtonType.Primary,
    content: @Composable RowScope.() -> Unit,
) {
    val color = when (type) {
        RecipeRoundedButtonType.Primary -> md_theme_light_primary
        RecipeRoundedButtonType.Secondary -> md_theme_light_onPrimary
    }
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        ),
        contentPadding = contentPadding,
        content = content,
    )
}

object RecipeButtonDefaults {
    // OutlinedButton border color doesn't respect disabled state by default
    const val DisabledOutlinedButtonBorderAlpha = 0.12f

    // OutlinedButton default border width isn't exposed via ButtonDefaults
    val OutlinedButtonBorderWidth = 1.dp
}