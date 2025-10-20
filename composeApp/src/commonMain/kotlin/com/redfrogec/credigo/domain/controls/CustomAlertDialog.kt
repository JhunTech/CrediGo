package com.redfrogec.credigo.domain.controls

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun ShowCustomAlertDialog(title: String, message: String): Boolean{
    var showAlertDialog by remember { mutableStateOf(true) }
    var returnConfirm by remember { mutableStateOf(false) }
    if (showAlertDialog) {
        AlertDialog(
            onDismissRequest = {
                showAlertDialog = false
                returnConfirm = false
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
                        returnConfirm = true
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
                        returnConfirm = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    return returnConfirm

}