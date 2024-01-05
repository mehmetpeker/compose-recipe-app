package com.mehmetpeker.recipe.presentation.authentication.register

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.mehmetpeker.recipe.base.EdgeToEdgeScaffold
import com.mehmetpeker.recipe.common.RecipeRoundedButton
import com.mehmetpeker.recipe.common.SuccessAlertDialog
import com.mehmetpeker.recipe.designsystem.RecipeTextField
import com.mehmetpeker.recipe.designsystem.RecipeTextFieldType
import com.mehmetpeker.recipe.designsystem.RecipeTopAppBar
import com.mehmetpeker.recipe.designsystem.theme.RecipeFontFamily
import com.mehmetpeker.recipe.util.RouteConstants.ROUTE_LOGIN
import com.mehmetpeker.recipe.util.ValidationResult
import com.mehmetpeker.recipe.util.extension.scaledSp
import com.mehmetpeker.recipe.util.extension.verticalSpace
import org.koin.androidx.compose.koinViewModel


@Composable
fun RegisterScreen(navController: NavController, viewModel: RegisterViewModel = koinViewModel()) {
    BaseScreen(viewModel, navController) {
        val uiState by viewModel.uiState.collectAsState()

        RegisterScreenContent(
            viewModel.email,
            viewModel.username,
            viewModel.password,
            uiState.usernameResult,
            uiState.emailResult,
            uiState.passwordResult,
            onNavigationClick = {
                navController.popBackStack()
            },
            onSignUpClick = {
                viewModel.validateInputs()
            },
            onEmailChanged = {
                viewModel.email = it
            },
            onPasswordChanged = {
                viewModel.password = it
            },
            onUserNameChanged = {
                viewModel.username = it
            },
        )
        LaunchedEffect(uiState.isValid) {
            if (uiState.isValid) {
                viewModel.register()
            }
        }
        if (uiState.registerState) {
            SuccessAlertDialog {
                viewModel.changeRegisterState(false)
                navController.navigate(ROUTE_LOGIN) {
                    launchSingleTop = true
                    popUpTo(ROUTE_LOGIN) {
                        inclusive = true
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreenContent(
    email: TextFieldValue,
    username: TextFieldValue,
    password: TextFieldValue,
    usernameValidationResult: ValidationResult?,
    emailValidationResult: ValidationResult?,
    passwordValidationResult: ValidationResult?,
    onNavigationClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onEmailChanged: (TextFieldValue) -> Unit,
    onUserNameChanged: (TextFieldValue) -> Unit,
    onPasswordChanged: (TextFieldValue) -> Unit

) {

    val titleTextStyle = TextStyle(
        fontFamily = RecipeFontFamily.poppinsFamily,
        fontWeight = FontWeight.Light,
        fontSize = 13.scaledSp,
        color = Color.Black.copy(alpha = 0.7f)
    )

    EdgeToEdgeScaffold(
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
                    modifier = Modifier.heightIn(min =56.dp),
                    hintText = stringResource(id = R.string.username),
                    value = username,
                    onValueChange = onUserNameChanged,
                    isErrorEnabled = usernameValidationResult?.isSuccess == false,
                    errorMessage = usernameValidationResult?.errorMessage
                )
                12.verticalSpace()
                Text(stringResource(id = R.string.email_address), style = titleTextStyle)
                4.verticalSpace()
                RecipeTextField(
                    modifier = Modifier.heightIn(min =56.dp),
                    hintText = stringResource(id = R.string.email_address),
                    value = email,
                    onValueChange = onEmailChanged,
                    isErrorEnabled = emailValidationResult?.isSuccess == false,
                    errorMessage = emailValidationResult?.errorMessage
                )
                12.verticalSpace()
                Text(stringResource(id = R.string.password), style = titleTextStyle)
                4.verticalSpace()
                RecipeTextField(
                    modifier = Modifier.heightIn(min =56.dp),
                    hintText = stringResource(R.string.password),
                    value = password,
                    onValueChange = onPasswordChanged,
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
                    .heightIn(min =56.dp),
                onClick = { onSignUpClick() }) {
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
