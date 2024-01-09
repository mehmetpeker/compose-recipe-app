package com.mehmetpeker.recipe.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mehmetpeker.recipe.R

@Composable
fun SuccessAlertDialog(
    description: String = stringResource(id = R.string.register_success),
    onDismiss: () -> Unit = {}
) {
    AlertDialog(
        onDismissRequest = { onDismiss.invoke() },
        title = { Text(stringResource(id = R.string.success)) },
        text = { Text(description) },
        confirmButton = {
            Button(
                onClick = { onDismiss.invoke() }
            ) {
                Text(stringResource(id = R.string.ok))
            }
        }
    )
}