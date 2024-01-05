package com.mehmetpeker.recipe.presentation.authentication.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.base.BaseScreen
import com.mehmetpeker.recipe.base.EdgeToEdgeScaffold
import com.mehmetpeker.recipe.common.RecipeRoundedButton
import com.mehmetpeker.recipe.designsystem.RecipeTextField
import com.mehmetpeker.recipe.designsystem.RecipeTextFieldType
import com.mehmetpeker.recipe.designsystem.RecipeTopAppBar
import com.mehmetpeker.recipe.designsystem.theme.RecipeFontFamily
import com.mehmetpeker.recipe.designsystem.theme.md_theme_light_primary
import com.mehmetpeker.recipe.util.RouteConstants.ROUTE_FORGOT_PASSWORD
import com.mehmetpeker.recipe.util.RouteConstants.ROUTE_HOME
import com.mehmetpeker.recipe.util.RouteConstants.ROUTE_ONBOARDING
import com.mehmetpeker.recipe.util.RouteConstants.ROUTE_REGISTER
import com.mehmetpeker.recipe.util.extension.scaledSp
import com.mehmetpeker.recipe.util.extension.verticalSpace
import org.koin.androidx.compose.koinViewModel


@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    BaseScreen(viewModel, navController) {
        LoginScreenContent(
            viewModel,
            uiState,
            onNavigationClick = {
                navController.navigate(ROUTE_ONBOARDING) {
                    launchSingleTop = true
                    popUpTo(ROUTE_ONBOARDING) {
                        inclusive = true
                    }
                }
            },
            onLogInClick = { email, password ->
                viewModel.validateInputs(email, password)
            },
            onSignUpClick = {
                navController.navigate(ROUTE_REGISTER) {
                    launchSingleTop = true
                }
            },
            onForgotPasswordClick = {
                navController.navigate(ROUTE_FORGOT_PASSWORD)
            }
        )
    }
    LaunchedEffect(uiState.loginResponse) {
        if (uiState.loginResponse != null) {
            navController.navigate(ROUTE_HOME) {
                launchSingleTop = true
                navController.graph.startDestinationRoute?.let {
                    popUpTo(it) {
                        inclusive = true
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenContent(
    viewModel: LoginViewModel,
    uiState: LoginViewModel.UiState,
    onNavigationClick: () -> Unit,
    onLogInClick: (email: String, password: String) -> Unit,
    onSignUpClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    var email by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var password by remember {
        mutableStateOf(TextFieldValue(""))
    }
    val titleTextStyle = TextStyle(
        fontFamily = RecipeFontFamily.poppinsFamily,
        fontWeight = FontWeight.Light,
        fontSize = 13.scaledSp,
        color = Color.Black.copy(alpha = 0.7f)
    )

    LaunchedEffect(uiState) {
        if (uiState.isFormValid && uiState.loginResponse == null) {
            viewModel.login(email.text, password.text)
        }
    }
    EdgeToEdgeScaffold(
        containerColor = Color.White,
        topBar = {
            RecipeTopAppBar(
                titleRes = R.string.log_in,
                navigationIcon = Icons.Default.ArrowBack,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onNavigationClick,
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White),
                navigationTint = Color.Black,
                textColor = Color.Black
            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .systemBarsPadding()
                .padding(horizontal = 24.dp)
                .padding(top = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(stringResource(id = R.string.username), style = titleTextStyle)
            4.verticalSpace()
            RecipeTextField(
                modifier = Modifier.heightIn(min = 56.dp),
                hintText = stringResource(id = R.string.username),
                value = email,
                onValueChange = {
                    email = it
                },
                isErrorEnabled = uiState.usernameResult?.isSuccess?.not() ?: false,
                errorMessage = uiState.usernameResult?.errorMessage
            )
            12.verticalSpace()
            Text(text = "Password", style = titleTextStyle)
            4.verticalSpace()
            RecipeTextField(
                modifier = Modifier.heightIn(min = 56.dp),
                hintText = stringResource(id = R.string.password),
                value = password,
                onValueChange = {
                    password = it
                },
                type = RecipeTextFieldType.PASSWORD,
                isErrorEnabled = uiState.passwordResult?.isSuccess?.not() ?: false,
                errorMessage = uiState.passwordResult?.errorMessage
            )
            12.verticalSpace()
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = uiState.isRememberChecked,
                    onCheckedChange = {
                        viewModel.changeRememberState(it)
                    },
                    colors = CheckboxDefaults.colors(md_theme_light_primary)
                )
                Text(
                    text = stringResource(id = R.string.remember_me),
                    style = MaterialTheme.typography.labelLarge
                )
            }
            12.verticalSpace()
            Text(
                modifier = Modifier.clickable { onForgotPasswordClick() },
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
                    .heightIn(min = 56.dp), onClick = { onLogInClick(email.text, password.text) }) {
                Text(
                    text = stringResource(id = R.string.log_in).uppercase(),
                    fontFamily = RecipeFontFamily.poppinsFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.scaledSp,
                    color = Color.White
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
    val doNotHaveAnAccountText = stringResource(id = R.string.do_not_have_account)
    val signUpText = stringResource(id = R.string.sign_up)

    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                fontFamily = RecipeFontFamily.poppinsFamily
            )
        ) {
            append(doNotHaveAnAccountText)
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
