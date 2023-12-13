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
import com.mehmetpeker.recipe.R
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
            val message = stringResource(id = error?.messageId ?: R.string.generic_error)
            AlertDialog(
                onDismissRequest = {
                    viewModel.removeError()
                },
                title = {},
                text = {
                    Text(text = message)
                },
                properties = DialogProperties(),
                confirmButton = {},
                dismissButton = {
                    val action: () -> Unit = {
                        when (error?.code) {
                            HttpStatusCode.Unauthorized.value -> {
                                // navigate to login screen
                                /*navController.navigate(
                                    "eg:loginRoute",
                                    navOptions = navOptions {
                                        popUpTo("startDestinationRoute") {
                                            inclusive = true
                                        }
                                    })*/
                                viewModel.removeError()
                            }

                            else -> viewModel.removeError()
                        }
                    }
                    TextButton(onClick = action) {
                        Text(text = "")
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