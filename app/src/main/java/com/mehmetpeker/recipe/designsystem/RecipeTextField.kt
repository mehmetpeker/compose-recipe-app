package com.mehmetpeker.recipe.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.designsystem.theme.RecipeFontFamily
import com.mehmetpeker.recipe.ui.theme.cd_theme_textfield_container_color

sealed interface RecipeTextFieldType {
    data object DEFAULT : RecipeTextFieldType
    data object PASSWORD : RecipeTextFieldType
}

@Composable
fun RecipeTextField(
    modifier: Modifier = Modifier,
    hintText: String,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    isErrorEnabled: Boolean = false,
    type: RecipeTextFieldType = RecipeTextFieldType.DEFAULT
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    val hintTextSize = when {
        value.text.isNotBlank() -> 11.sp
        else -> 15.sp
    }
    val visualTransformation = when {
        type == RecipeTextFieldType.DEFAULT || isPasswordVisible -> VisualTransformation.None
        type == RecipeTextFieldType.PASSWORD -> PasswordVisualTransformation()
        else -> VisualTransformation.None
    }
    val keyboardType = when (type) {
        RecipeTextFieldType.DEFAULT -> KeyboardType.Text
        RecipeTextFieldType.PASSWORD -> KeyboardType.Password
    }
    Box(
        modifier = modifier
            .background(cd_theme_textfield_container_color, shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 20.dp, vertical = 6.dp)
    ) {

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
            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
        ) { decorationBox ->
            val image = if (isPasswordVisible)
                R.drawable.baseline_visibility_off_24
            else R.drawable.baseline_visibility_24
            Row {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    if (value.text.isNotBlank()) {
                        Text(
                            text = hintText,
                            fontFamily = RecipeFontFamily.poppinsFamily,
                            fontSize = hintTextSize,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black
                        )
                    }
                    decorationBox.invoke()
                }
                if (type == RecipeTextFieldType.PASSWORD && value.text.isNotBlank()) {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(painter = painterResource(id = image), "toggle", tint = Color.Black)
                    }
                }
            }


        }
        if (value.text.isBlank()) {
            Text(
                text = hintText,
                fontFamily = RecipeFontFamily.poppinsFamily,
                fontSize = hintTextSize,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.align(Alignment.CenterStart),
                color = Color.Black
            )
        }
    }

}

@Preview
@Composable
fun RecipeTextfieldWithTextPreview() {
    var text by remember {
        mutableStateOf(TextFieldValue("mehmetpeker41@gmail.com"))
    }
    RecipeTextField(hintText = "E-Mail", value = text, onValueChange = {
        text = it
    })
}

@Preview
@Composable
fun RecipeTextfieldWithoutTextPreview() {
    var text by remember {
        mutableStateOf(TextFieldValue(""))
    }
    RecipeTextField(hintText = "E-Mail", value = text, onValueChange = {
        text = it
    })
}