package com.mehmetpeker.recipe.presentation.authentication.login

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.base.BaseScreen
import com.mehmetpeker.recipe.common.RecipeRoundedButton
import com.mehmetpeker.recipe.designsystem.RecipeTextField
import com.mehmetpeker.recipe.designsystem.RecipeTextFieldType
import com.mehmetpeker.recipe.designsystem.RecipeTopAppBar
import com.mehmetpeker.recipe.designsystem.theme.RecipeFontFamily
import com.mehmetpeker.recipe.ui.theme.md_theme_light_primary
import com.mehmetpeker.recipe.util.extension.scaledSp
import com.mehmetpeker.recipe.util.extension.verticalSpace

@Composable
fun LoginScreen(navController: NavController) {
    BaseScreen(viewModel = LoginViewModel(), navController) {
        LoginScreenContent(
            onNavigationClick = {
                navController.popBackStack()
            },
            onSignUpClick = {
                navController.navigate("register") {
                    launchSingleTop = true
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenContent(
    onNavigationClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    val context = LocalContext.current
    var email by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var password by remember {
        mutableStateOf(TextFieldValue(""))
    }
    Scaffold(
        containerColor = Color.White,
        topBar = {
            RecipeTopAppBar(
                titleRes = R.string.log_in,
                navigationIcon = Icons.Default.ArrowBack,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onNavigationClick
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 24.dp)
                .padding(top = 16.dp)
        ) {

            RecipeTextField(
                modifier = Modifier.requiredHeight(56.dp),
                hintText = "Email Adress",
                value = email,
                onValueChange = {
                    email = it
                })
            16.verticalSpace()
            RecipeTextField(
                modifier = Modifier.requiredHeight(56.dp),
                hintText = "Password",
                value = password,
                onValueChange = {
                    password = it
                },
                type = RecipeTextFieldType.PASSWORD
            )
            16.verticalSpace()
            Text(
                text = stringResource(id = R.string.forgot_password),
                fontFamily = RecipeFontFamily.poppinsFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.scaledSp,
                color = md_theme_light_primary
            )
            40.verticalSpace()
            RecipeRoundedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(56.dp), onClick = { }) {
                Text(
                    text = stringResource(id = R.string.log_in).uppercase(),
                    fontFamily = RecipeFontFamily.poppinsFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.scaledSp
                )
            }
            24.verticalSpace()
            val annotatedString = buildSignUpAnnotatedString()
            ClickableText(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = annotatedString,
                onClick = { offset ->
                    annotatedString.getStringAnnotations(offset, offset)
                        .firstOrNull()?.let { _ ->
                            onSignUpClick()
                        }
                })
        }
    }
}

@Composable
private fun buildSignUpAnnotatedString(): AnnotatedString {
    val dontHaveAnAccountText = stringResource(id = R.string.dont_have_account)
    val signUpText = stringResource(id = R.string.sign_up)

    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                fontFamily = RecipeFontFamily.poppinsFamily
            )
        ) {
            append(dontHaveAnAccountText)
            append("\t")
        }
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold,
                color = md_theme_light_primary,
                fontFamily = RecipeFontFamily.poppinsFamily
            )
        ) {
            pushStringAnnotation(tag = signUpText, annotation = signUpText)
            append(signUpText)
        }
    }
    return annotatedString
}
