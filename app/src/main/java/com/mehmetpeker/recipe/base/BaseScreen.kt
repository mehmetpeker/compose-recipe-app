package com.mehmetpeker.recipe.base

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.mehmetpeker.recipe.R
import com.mehmetpeker.recipe.util.RouteConstants.ROUTE_LOGIN
import io.ktor.http.HttpStatusCode

@Composable
fun <VM : BaseViewModel> BaseScreen(
    viewModel: VM,
    navController: NavController,
    content: @Composable () -> Unit
) {
    val error by viewModel.error.collectAsState()
    when (error) {
        null -> Unit
        else -> {
            val title = stringResource(id = R.string.error)
            val genericMessage = stringResource(id = error?.messageId ?: R.string.generic_error)
            val errorMessage = error?.errorBody?.errorMessage ?: genericMessage
            val errorCode = error?.errorBody?.errorCode
            AlertDialog(
                onDismissRequest = {
                    viewModel.removeError()
                },
                title = { Text(text = title) },
                text = {
                    Text(text = errorMessage)
                },
                properties = DialogProperties(),
                confirmButton = {},
                dismissButton = {
                    val action: () -> Unit = {
                        when (errorCode?.toIntOrNull()) {
                            HttpStatusCode.Unauthorized.value -> {
                                navController.navigate(
                                    ROUTE_LOGIN,
                                    navOptions = navOptions {
                                        popUpTo(ROUTE_LOGIN) {
                                            inclusive = true
                                        }
                                    })
                                viewModel.removeError()
                            }

                            else -> viewModel.removeError()
                        }
                    }
                    TextButton(onClick = action) {
                        Text(text = stringResource(id = R.string.ok))
                    }
                },
                shape = RoundedCornerShape(12.dp)
            )
        }
    }

    val showProgress by viewModel.showProgress.collectAsState()
    content()
    if (showProgress) {
        Dialog(onDismissRequest = { }) {
            CircularProgressIndicator()
        }
    }
}