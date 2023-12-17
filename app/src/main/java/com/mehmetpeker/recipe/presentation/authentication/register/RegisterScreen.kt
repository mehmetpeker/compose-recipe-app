package com.mehmetpeker.recipe.presentation.authentication.register

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.base.BaseScreen
import com.mehmetpeker.recipe.common.RecipeRoundedButton
import com.mehmetpeker.recipe.designsystem.RecipeTextField
import com.mehmetpeker.recipe.designsystem.RecipeTextFieldType
import com.mehmetpeker.recipe.designsystem.RecipeTopAppBar
import com.mehmetpeker.recipe.designsystem.theme.RecipeFontFamily
import com.mehmetpeker.recipe.util.ValidationResult
import com.mehmetpeker.recipe.util.extension.scaledSp
import com.mehmetpeker.recipe.util.extension.verticalSpace

@Composable
fun RegisterScreen(navController: NavController) {
    val viewModel = RegisterViewModel()
    BaseScreen(viewModel, navController) {
        val uiState by viewModel.uiState.collectAsState()
        when (uiState) {
            is RegisterViewModel.UiState.Invalid -> {
                val state = uiState as RegisterViewModel.UiState.Invalid
                RegisterScreenContent(
                    state.usernameResult,
                    state.emailResult,
                    state.passwordResult,
                    onNavigationClick = {
                        navController.popBackStack()
                    },
                    onSignUpClick = { username, email, password ->
                        viewModel.validateInputs(username, email, password)
                    }
                )
            }

            RegisterViewModel.UiState.Valid -> {
                viewModel.register()
                navController.navigate(navController.graph.startDestinationRoute ?: "")
            }
        }

    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreenContent(
    usernameValidationResult: ValidationResult?,
    emailValidationResult: ValidationResult?,
    passwordValidationResult: ValidationResult?,
    onNavigationClick: () -> Unit,
    onSignUpClick: (username: String, email: String, password: String) -> Unit
) {
    val (username, setUsername) = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val (email, setEmail) = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val (password, setPassword) = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val titleTextStyle = TextStyle(
        fontFamily = RecipeFontFamily.poppinsFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.scaledSp,
        color = Color.Black.copy(alpha = 0.7f)
    )

    Scaffold(
        containerColor = Color.White,
        topBar = {
            RecipeTopAppBar(
                titleRes = R.string.sign_up,
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
                .padding(it)
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState(), enabled = true)
            ) {
                Text(stringResource(id = R.string.username), style = titleTextStyle)
                4.verticalSpace()
                RecipeTextField(
                    modifier = Modifier.requiredHeight(56.dp),
                    hintText = stringResource(id = R.string.username),
                    value = username,
                    onValueChange = setUsername,
                    isErrorEnabled = usernameValidationResult?.isSuccess == false,
                    errorMessage = usernameValidationResult?.errorMessage
                )
                12.verticalSpace()
                Text(stringResource(id = R.string.email_address), style = titleTextStyle)
                4.verticalSpace()
                RecipeTextField(
                    modifier = Modifier.requiredHeight(56.dp),
                    hintText = stringResource(id = R.string.email_address),
                    value = email,
                    onValueChange = setEmail,
                    isErrorEnabled = emailValidationResult?.isSuccess == false,
                    errorMessage = emailValidationResult?.errorMessage
                )
                12.verticalSpace()
                Text(stringResource(id = R.string.password), style = titleTextStyle)
                4.verticalSpace()
                RecipeTextField(
                    modifier = Modifier.requiredHeight(56.dp),
                    hintText = stringResource(R.string.password),
                    value = password,
                    onValueChange = setPassword,
                    type = RecipeTextFieldType.PASSWORD,
                    isErrorEnabled = passwordValidationResult?.isSuccess == false,
                    errorMessage = passwordValidationResult?.errorMessage
                )
                10.verticalSpace()
            }
            RecipeRoundedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .requiredHeight(56.dp),
                onClick = { onSignUpClick(username.text, email.text, password.text) }) {
                Text(
                    text = stringResource(id = R.string.sign_up).uppercase(),
                    fontFamily = RecipeFontFamily.poppinsFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.scaledSp,
                    color = Color.White
                )
            }
        }
    }
}
