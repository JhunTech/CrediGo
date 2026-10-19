package com.redfrogec.credigo.domain.controls

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun confirmDialog(title: String, message: String): String{
    var showAlertDialog by remember { mutableStateOf(true) }
    var returnConfirm by remember { mutableStateOf("None") }
    if (showAlertDialog) {
        AlertDialog(
            onDismissRequest = {
                showAlertDialog = false
                returnConfirm = "Cancel"
            },
            title = {
                Text(title)
            },
            text = {
                Text(message)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showAlertDialog = false
                        println("Confirmed!")
                        returnConfirm = "OK"
                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showAlertDialog = false
                        println("Cancelled!")
                        returnConfirm = "Cancel"
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    return returnConfirm

}