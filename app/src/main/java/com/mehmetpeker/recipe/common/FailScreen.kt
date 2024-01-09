package com.mehmetpeker.recipe.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun FailScreen(
    title: String,
    message: String,
    onButtonClick: () -> Unit = {},
    content: @Composable RowScope.() -> Unit
) {
    Column(
        Modifier
            .padding(horizontal = 24.dp)
            .fillMaxSize()
            ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = message,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(top = 8.dp),
            textAlign = TextAlign.Center
        )
        RecipeRoundedButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(top = 10.dp),
            type = RecipeRoundedButtonType.Primary,
            onClick = onButtonClick,
        ) {
            content()
        }
    }
}