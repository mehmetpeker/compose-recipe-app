package com.mehmetpeker.recipe.presentation.main.screens.addRecipe.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CreateRecipeInput(
    modifier: Modifier = Modifier,
    icon: @Composable RowScope.() -> Unit,
    title: String = "",
    value: String = "",
    onAction: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .background(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xffF1F1F1)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            icon()
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
                    .weight(1f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = Color(0xffA9A9A9),
                    fontSize = 14.sp
                ),
                modifier = Modifier
                    .padding(horizontal = 4.dp)
            )
            IconButton(onClick = onAction) {
                Icon(Icons.Default.ArrowForward, contentDescription = null)
            }
        }
    }
}

@Composable
@Preview
fun CreateRecipeInputPreview() {
    CreateRecipeInput(
        modifier = Modifier.height(60.dp), icon = {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                    .padding(8.dp)
            ) {
                Icon(Icons.Default.DateRange, contentDescription = null, tint = Color.Red)
            }
        }, title = "Cook Time", value = "45 min"
    )
}