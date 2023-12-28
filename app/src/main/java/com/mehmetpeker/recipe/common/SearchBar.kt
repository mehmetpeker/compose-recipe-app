package com.mehmetpeker.recipe.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.designsystem.theme.RecipeFontFamily
import com.mehmetpeker.recipe.designsystem.theme.md_theme_light_primary
import com.mehmetpeker.recipe.util.extension.scaledSp

@Composable
fun SearchBar(
    modifier: Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    placeholder: String = stringResource(id = R.string.search_any_recipe),
    onSearchClick: (() -> Unit)? = null,
    onClearClick: (() -> Unit)? = null
) {
    Box(modifier = modifier) {
        BasicTextField(
            modifier = Modifier.align(Alignment.CenterStart),
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(
                fontFamily = RecipeFontFamily.poppinsFamily,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            ),
        ) { decorationBox ->
            Row {
                IconButton(onClick = { onSearchClick?.invoke() }) {
                    Icon(
                        Icons.Default.Search,
                        "search",
                        tint = md_theme_light_primary
                    )
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    if (value.text.isNotBlank()) {
                        Text(
                            text = placeholder,
                            fontFamily = RecipeFontFamily.poppinsFamily,
                            fontSize = 14.scaledSp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Gray
                        )
                    }
                    decorationBox.invoke()
                }
                IconButton(onClick = { onClearClick?.invoke() }) {
                    Icon(
                        Icons.Default.Clear,
                        "clear",
                        tint = md_theme_light_primary
                    )
                }
            }
        }
    }
}