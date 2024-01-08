package com.mehmetpeker.recipe.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.designsystem.RecipeTextField
import com.mehmetpeker.recipe.designsystem.theme.RecipeFontFamily
import com.mehmetpeker.recipe.designsystem.theme.RecipeTheme
import com.mehmetpeker.recipe.util.extension.scaledSp
import com.mehmetpeker.recipe.util.extension.verticalSpace

data class RecipeInputDialogProperties(
    val title: String = "",
    val placeHolder: String = ""
)

@Composable
fun RecipeInputDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    properties: RecipeInputDialogProperties = RecipeInputDialogProperties(),
    isErrorEnabled: Boolean = false,
    errorMessage: List<String> = emptyList(),
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onConfirm: (value: String) -> Unit
) {
    var inputFieldValue by remember {
        mutableStateOf(value)
    }
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        RecipeTheme {
            Surface(
                shape = RoundedCornerShape(12.dp),
            ) {
                Column(
                    modifier = modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(properties.title, style = MaterialTheme.typography.titleMedium)
                    8.verticalSpace()
                    RecipeTextField(
                        modifier = Modifier.heightIn(min = 56.dp),
                        hintText = properties.placeHolder,
                        value = inputFieldValue,
                        onValueChange = {
                            inputFieldValue = it
                            onValueChange(it)
                        },
                        isErrorEnabled = isErrorEnabled,
                        errorMessage = errorMessage,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    8.verticalSpace()
                    RecipeRoundedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 56.dp), onClick = { onConfirm(value.text) }
                    ) {
                        Text(
                            text = stringResource(id = R.string.confirm).uppercase(),
                            fontFamily = RecipeFontFamily.poppinsFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 15.scaledSp
                        )
                    }
                }
            }
        }
    }
}