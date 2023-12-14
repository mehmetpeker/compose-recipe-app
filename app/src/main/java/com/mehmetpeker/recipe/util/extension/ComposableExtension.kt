package com.mehmetpeker.recipe.util.extension

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Int.scaledSp(): TextUnit {
    val value: Int = this
    return with(LocalDensity.current) {
        val fontScale = this.fontScale
        val textSize = value / fontScale
        textSize.sp
    }
}

val Int.scaledSp: TextUnit
    @Composable get() = scaledSp()

@Composable
fun Int.verticalSpace() {
    Spacer(modifier = Modifier.height(this.dp))
}

@Composable
fun Int.horizontalSpace() {
    Spacer(modifier = Modifier.width(this.dp))
}
