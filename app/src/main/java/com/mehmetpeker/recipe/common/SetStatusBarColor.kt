package com.mehmetpeker.recipe.common

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun SetStatusBarColor(color: Color) {
    val view = LocalView.current
    val colorScheme = MaterialTheme.colorScheme
    val window = (view.context as Activity).window
    val insetsController = WindowCompat.getInsetsController(window, view)

    SideEffect {
        window.statusBarColor = color.toArgb()
        insetsController.isAppearanceLightStatusBars = false
    }
    DisposableEffect(Unit) {
        onDispose {
            window.statusBarColor = colorScheme.primary.toArgb()
            insetsController.isAppearanceLightStatusBars = false
        }
    }
}