package com.mehmetpeker.recipe.presentation.authentication.resetPassword

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.base.BaseScreen
import com.mehmetpeker.recipe.common.RecipeRoundedButton
import com.mehmetpeker.recipe.designsystem.RecipeTextField
import com.mehmetpeker.recipe.designsystem.RecipeTopAppBar
import com.mehmetpeker.recipe.designsystem.theme.RecipeFontFamily
import com.mehmetpeker.recipe.util.extension.scaledSp
import com.mehmetpeker.recipe.util.extension.verticalSpace
import org.koin.androidx.compose.koinViewModel
const val ROUTE_RESET_PASSWORD = "resetPassword/{email}/{token}"
@Composable
fun ResetPasswordScreen(
    navController: NavController,
    email: String,
    token: String,
    viewModel: ResetPasswordViewModel = koinViewModel()
) {
    BaseScreen(viewModel, navController) {
        ResetPasswordScreenContent(
            email,
            token,
            viewModel,
            onNavigationClick = {
                navController.popBackStack()
            },
            onResetButtonClick = {
                viewModel.validateEmail {
                    viewModel.resetPassword()
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordScreenContent(
    email: String,
    token: String,
    viewModel: ResetPasswordViewModel,
    onNavigationClick: () -> Unit,
    onResetButtonClick: () -> Unit,
) {
    Scaffold(
        containerColor = Color.White,
        topBar = {
            RecipeTopAppBar(
                titleRes = R.string.reset_password_title,
                navigationIcon = Icons.Default.ArrowBack,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onNavigationClick,
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White),
                navigationTint = Color.Black,
                textColor = Color.Black
            )
        }
    ) { it ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 24.dp)
                .padding(top = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                stringResource(id = R.string.reset_password_title),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                email,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .padding(top = 12.dp)
                    .align(Alignment.CenterHorizontally),

                )
            12.verticalSpace()
            Text(
                token,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .padding(top = 12.dp)
                    .align(Alignment.CenterHorizontally),

                )
            12.verticalSpace()
            Text(
                stringResource(id = R.string.password),
                style = MaterialTheme.typography.labelLarge
            )
            4.verticalSpace()
            RecipeTextField(
                modifier = Modifier.requiredHeight(56.dp),
                hintText = stringResource(id = R.string.password),
                value = viewModel.emailField,
                onValueChange = { textFieldValue ->
                    viewModel.onEmailTextChanged(textFieldValue)
                },
                isErrorEnabled = viewModel.emailValidationResult?.isSuccess?.not() ?: false,
                errorMessage = viewModel.emailValidationResult?.errorMessage
            )
            12.verticalSpace()
            Text(
                stringResource(id = R.string.password_again),
                style = MaterialTheme.typography.labelLarge
            )
            4.verticalSpace()
            RecipeTextField(
                modifier = Modifier.requiredHeight(56.dp),
                hintText = stringResource(id = R.string.password_again),
                value = viewModel.emailField,
                onValueChange = { textFieldValue ->
                    viewModel.onEmailTextChanged(textFieldValue)
                },
                isErrorEnabled = viewModel.emailValidationResult?.isSuccess?.not() ?: false,
                errorMessage = viewModel.emailValidationResult?.errorMessage
            )
            24.verticalSpace()
            RecipeRoundedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(56.dp), onClick = onResetButtonClick
            ) {
                Text(
                    text = stringResource(id = R.string.reset_password).uppercase(),
                    fontFamily = RecipeFontFamily.poppinsFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.scaledSp,
                    color = Color.White
                )
            }
            24.verticalSpace()
        }
    }
}
