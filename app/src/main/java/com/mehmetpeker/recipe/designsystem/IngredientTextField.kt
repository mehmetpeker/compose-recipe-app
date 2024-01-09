package com.mehmetpeker.recipe.designsystem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mehmetpeker.recipe.designsystem.theme.RecipeTheme
import com.mehmetpeker.recipe.util.extension.scaledSp

sealed class IngredientTextFieldType {
    data object Default : IngredientTextFieldType()
    data class Unit(val unitList: List<String>) : IngredientTextFieldType()
}

@Composable
fun IngredientsTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    cursorBrush: Brush = SolidColor(Color.Black),
    placeHolder: String = "",
    type: IngredientTextFieldType = IngredientTextFieldType.Default,
    onUnitSelected: (String) -> Unit = {},
    onClick: () -> Unit = {}
) {
    var isDialogExpanded by remember {
        mutableStateOf(false)
    }
    var selectedIndex by remember {
        mutableIntStateOf(-1)
    }
    val ingredientsTextStyle = when {
        value.text.isEmpty() -> MaterialTheme.typography.titleMedium.copy(
            color = Color(0xffC1C1C1),
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            fontSize = 14.scaledSp
        )

        else -> MaterialTheme.typography.titleMedium.copy(
            color = Color(0xff303030),
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            fontSize = 14.scaledSp
        )
    }
    Box(
        modifier = modifier
            .border(
                BorderStroke(1.dp, Color.LightGray),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(8.dp),
    ) {
        Row(
            Modifier
                .align(Alignment.CenterStart)
        ) {
            BasicTextField(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth()
                    .weight(1f)
                    .clickable {
                        onClick()
                    },
                value = value,
                onValueChange = onValueChange,
                enabled = enabled,
                readOnly = readOnly,
                keyboardActions = keyboardActions,
                keyboardOptions = keyboardOptions,
                singleLine = singleLine,
                maxLines = maxLines,
                minLines = minLines,
                visualTransformation = visualTransformation,
                onTextLayout = onTextLayout,
                interactionSource = interactionSource,
                cursorBrush = cursorBrush,
                textStyle = ingredientsTextStyle
            ) {
                it()
            }
            if (type is IngredientTextFieldType.Unit) {
                val unitList = type.unitList
                Row(modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable { isDialogExpanded = true }) {
                    Text(
                        text = if (selectedIndex < 0) unitList.first() else unitList[selectedIndex],
                        style = ingredientsTextStyle
                    )
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }

                if (isDialogExpanded) {
                    Dialog(
                        onDismissRequest = { isDialogExpanded = false },
                    ) {
                        RecipeTheme {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                            ) {
                                LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                                    itemsIndexed(unitList) { index, item ->
                                        Text(
                                            text = item,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    onUnitSelected(item)
                                                    selectedIndex = index
                                                    isDialogExpanded = false
                                                },
                                            textAlign = TextAlign.Center,
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontWeight = FontWeight.Normal
                                            )
                                        )
                                        if (index < unitList.lastIndex) {
                                            Divider(modifier = Modifier.padding(horizontal = 16.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
        if (value.text.isEmpty()) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = placeHolder,
                style = ingredientsTextStyle
            )

        }
    }

}