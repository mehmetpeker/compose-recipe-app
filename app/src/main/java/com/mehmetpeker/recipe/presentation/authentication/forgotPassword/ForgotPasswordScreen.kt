package com.mehmetpeker.recipe.presentation.authentication.forgotPassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.base.BaseScreen
import com.mehmetpeker.recipe.base.EdgeToEdgeScaffold
import com.mehmetpeker.recipe.common.RecipeRoundedButton
import com.mehmetpeker.recipe.designsystem.RecipeTextField
import com.mehmetpeker.recipe.designsystem.RecipeTopAppBar
import com.mehmetpeker.recipe.designsystem.theme.RecipeFontFamily
import com.mehmetpeker.recipe.util.extension.scaledSp
import com.mehmetpeker.recipe.util.extension.verticalSpace
import org.koin.androidx.compose.koinViewModel


@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    viewModel: ForgotPasswordViewModel = koinViewModel()
) {
    BaseScreen(viewModel, navController) {
        ForgotPasswordScreenContent(
            viewModel,
            onNavigationClick = {
                navController.popBackStack()
            },
            onResetButtonClick = {
                viewModel.validateEmail { mail ->
                    viewModel.sendForgotPasswordEmail(mail)
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreenContent(
    viewModel: ForgotPasswordViewModel,
    onNavigationClick: () -> Unit,
    onResetButtonClick: () -> Unit,
) {
    EdgeToEdgeScaffold(
        containerColor = Color.White,
        topBar = {
            RecipeTopAppBar(
                titleRes = R.string.forgot_password_title,
                navigationIcon = Icons.Default.ArrowBack,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onNavigationClick,
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White),
                navigationTint = Color.Black,
                textColor = Color.Black
            )
        }
    ) { it ->
        when (viewModel.uiState) {
            ForgotPasswordViewModel.ForgotPasswordUiState.INITIAL -> InitialContent(
                modifier = Modifier.padding(
                    it
                ), viewModel = viewModel,
                onResetButtonClick = onResetButtonClick
            )

            ForgotPasswordViewModel.ForgotPasswordUiState.SUCCESS -> SuccessContent(Modifier.fillMaxSize()) {
                onNavigationClick()
            }
        }
    }
}

@Composable
fun InitialContent(
    modifier: Modifier = Modifier,
    viewModel: ForgotPasswordViewModel,
    onResetButtonClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .padding(top = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            stringResource(id = R.string.forgot_password),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            stringResource(id = R.string.forgot_password_description),
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
            stringResource(id = R.string.forgot_password_title),
            style = MaterialTheme.typography.labelLarge
        )
        4.verticalSpace()
        RecipeTextField(
            modifier = Modifier.heightIn(min = 56.dp),
            hintText = stringResource(id = R.string.enter_your_email_address),
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
                .heightIn(min = 56.dp), onClick = onResetButtonClick
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

@Composable
fun SuccessContent(modifier: Modifier = Modifier, onOkClick: () -> Unit) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.forgot_password_email_send_successfuly))
        8.verticalSpace()
        RecipeRoundedButton(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp), onClick = onOkClick
        ) {
            Text(
                text = stringResource(id = R.string.ok).uppercase(),
                fontFamily = RecipeFontFamily.poppinsFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 15.scaledSp,
                color = Color.White
            )
        }
    }
}

@Composable
@Preview
fun ForgotPasswordPreview() {
    ForgotPasswordScreen(navController = rememberNavController())
}
