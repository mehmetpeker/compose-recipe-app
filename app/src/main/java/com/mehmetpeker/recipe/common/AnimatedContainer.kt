package com.mehmetpeker.recipe.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedContainer(visibility: Boolean, content: @Composable () -> Unit) {
    val density = LocalDensity.current

    AnimatedVisibility(
        visible = visibility,
        enter = slideInVertically(
            // Slide in from 40 dp from the top.
            initialOffsetY = { with(density) { -40.dp.roundToPx() } },
            animationSpec = spring()
        ) + expandVertically(
            // Expand from the top.
            expandFrom = Alignment.Top,
            animationSpec = tween(1500)
        ) + fadeIn(
            // Fade in with the initial alpha of 0.3f.
            initialAlpha = 0.3f,
            animationSpec = tween(1500)
        ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        content()
    }
}