package com.mehmetpeker.recipe.presentation.authentication.updatePassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.base.BaseScreen
import com.mehmetpeker.recipe.base.EdgeToEdgeScaffold
import com.mehmetpeker.recipe.common.RecipeRoundedButton
import com.mehmetpeker.recipe.designsystem.RecipeTextField
import com.mehmetpeker.recipe.designsystem.RecipeTopAppBar
import com.mehmetpeker.recipe.designsystem.theme.RecipeFontFamily
import com.mehmetpeker.recipe.util.RouteConstants
import com.mehmetpeker.recipe.util.extension.scaledSp
import com.mehmetpeker.recipe.util.extension.verticalSpace
import org.koin.androidx.compose.koinViewModel

@Composable
fun UpdatePasswordScreen(
    navController: NavController,
    viewModel: UpdatePasswordViewModel = koinViewModel()
) {
    BaseScreen(viewModel, navController) {
        UpdatePasswordScreenContent(
            viewModel,
            onNavigationClick = {
                navController.popBackStack()
            },
            onResetButtonClick = {
                viewModel.validateForm {
                    viewModel.updatePassword()
                }
            },
            onLoginClick = {
                navController.navigate(
                    RouteConstants.ROUTE_LOGIN,
                    navOptions = navOptions {
                        launchSingleTop = true
                        popUpTo(RouteConstants.ROUTE_LOGIN) {
                            inclusive = true
                        }
                    })
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePasswordScreenContent(
    viewModel: UpdatePasswordViewModel,
    onNavigationClick: () -> Unit,
    onResetButtonClick: () -> Unit,
    onLoginClick: () -> Unit,
) {
    EdgeToEdgeScaffold(
        containerColor = Color.White,
        topBar = {
            RecipeTopAppBar(
                titleRes = R.string.update_password_title,
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
            UpdatePasswordViewModel.ResetPasswordUiState.Initial -> {
                UpdatePasswordInitialContent(
                    viewModel = viewModel, modifier = Modifier
                        .padding(it)
                        .systemBarsPadding()
                        .fillMaxSize(), onResetButtonClick = onResetButtonClick
                )
            }

            UpdatePasswordViewModel.ResetPasswordUiState.Success -> {
                UpdatePasswordSuccessContent(
                    modifier = Modifier
                        .padding(it)
                        .systemBarsPadding()
                        .fillMaxSize()
                ) {
                    onLoginClick()
                }
            }
        }
    }
}

@Composable
fun UpdatePasswordInitialContent(
    modifier: Modifier = Modifier,
    viewModel: UpdatePasswordViewModel,
    onResetButtonClick: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .padding(top = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            stringResource(id = R.string.update_password_title),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        12.verticalSpace()
        Text(
            stringResource(id = R.string.current_password),
            style = MaterialTheme.typography.labelLarge
        )
        4.verticalSpace()
        RecipeTextField(
            modifier = Modifier.heightIn(min = 56.dp),
            hintText = stringResource(id = R.string.current_password),
            value = viewModel.currentPassword,
            isErrorEnabled = viewModel.resetPasswordValidation?.isSuccess?.not() ?: false,
            onValueChange = { textFieldValue ->
                viewModel.onCurrentPasswordChanged(textFieldValue)
            }
        )
        12.verticalSpace()
        Text(
            stringResource(id = R.string.password),
            style = MaterialTheme.typography.labelLarge
        )
        4.verticalSpace()
        RecipeTextField(
            modifier = Modifier.heightIn(min = 56.dp),
            hintText = stringResource(id = R.string.password),
            value = viewModel.password,
            isErrorEnabled = viewModel.resetPasswordValidation?.isSuccess?.not() ?: false,
            onValueChange = { textFieldValue ->
                viewModel.onPasswordTextChanged(textFieldValue)
            }
        )
        12.verticalSpace()
        Text(
            stringResource(id = R.string.password_again),
            style = MaterialTheme.typography.labelLarge
        )
        4.verticalSpace()
        RecipeTextField(
            modifier = Modifier.heightIn(min = 56.dp),
            hintText = stringResource(id = R.string.password_again),
            value = viewModel.passwordConfirm,
            onValueChange = { textFieldValue ->
                viewModel.onPasswordConfirmTextChanged(textFieldValue)
            },
            isErrorEnabled = viewModel.resetPasswordValidation?.isSuccess?.not() ?: false,
            errorMessage = viewModel.resetPasswordValidation?.errorMessage
        )
        24.verticalSpace()
        RecipeRoundedButton(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp), onClick = onResetButtonClick
        ) {
            Text(
                text = stringResource(id = R.string.update_password_title).uppercase(),
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
fun UpdatePasswordSuccessContent(modifier: Modifier, onLoginClick: () -> Unit) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.update_password_success))
        8.verticalSpace()
        RecipeRoundedButton(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp), onClick = onLoginClick
        ) {
            Text(
                text = stringResource(id = R.string.log_in).uppercase(),
                fontFamily = RecipeFontFamily.poppinsFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 15.scaledSp
            )
        }
    }
}