package com.redfrogec.credigo.domain.controls

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun simpleDialog(title: String, message: String): Boolean {
    var showDialog by remember { mutableStateOf(true) }
    var returnDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
                returnDialog = true
            },
            title = { Text(title)},
            text = { Text(message) },
            confirmButton = {
                Button(onClick = {
                    showDialog = false
                    returnDialog = true
                }) {
                    Text("OK")
                }
            }
        )
    }
    return returnDialog
}
