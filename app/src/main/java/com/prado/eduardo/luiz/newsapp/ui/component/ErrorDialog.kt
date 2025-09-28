package com.prado.eduardo.luiz.newsapp.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.prado.eduardo.luiz.newsapp.R

@Composable
fun ErrorDialog(
    title: String = stringResource(id = R.string.error_dialog_title),
    message: String,
    onRetry: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = {
            TextButton(onClick = onRetry) {
                Text(stringResource(id = R.string.retry_button_text))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(id = R.string.close_button_text))
            }
        },
    )
}
