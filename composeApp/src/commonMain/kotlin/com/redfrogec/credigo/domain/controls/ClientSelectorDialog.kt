package com.redfrogec.credigo.domain.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.unit.dp
import com.redfrogec.credigo.data.model.Client
import com.redfrogec.credigo.ui.viewModel.ClientSelectorViewModel
import io.ktor.websocket.Frame

@Composable
fun ClientSelectorDialog(
    viewModel: ClientSelectorViewModel,
    onClientSelected: (Client) -> Unit,
) {
    val dialogOpen by viewModel.dialogOpen.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filteredClients = viewModel.filteredClients()

    if (dialogOpen) {
        AlertDialog(
            onDismissRequest = { viewModel.closeDialog() },
            title = { Frame.Text("Seleccionar Cliente") },
            text = {
                Column(Modifier.fillMaxWidth()) {
                    // Search input
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { viewModel.onSearchQueryChange(it) },
                        placeholder = { Text("Buscar cliente...") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))

                    // Scrollable client list
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 600.dp)
                    ) {
                        if (filteredClients.isEmpty()) {
                            item {
                                Text(
                                    "No existen clientes",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        } else {
                            items(filteredClients) { client ->
                                ListItem(
                                    headlineContent = { Text(client.name)},
                                    supportingContent = { Text("Id: ${client.identification}") },
                                    modifier = Modifier
                                        .background(Color.Transparent)
                                        .fillMaxWidth()
                                        .clickable {
                                            viewModel.selectClient(client)
                                            onClientSelected(client)
                                        }
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { viewModel.closeDialog() }) {
                    Text("Cancelar")
                }
            }
        )
    }
}