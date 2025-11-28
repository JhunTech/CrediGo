package com.redfrogec.credigo.ui.screen.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.redfrogec.credigo.data.model.Client
import com.redfrogec.credigo.data.model.ClientStatus
import com.redfrogec.credigo.domain.controls.ClientItemRow
import com.redfrogec.credigo.domain.controls.confirmDialog
import com.redfrogec.credigo.domain.controls.simpleDialog
import com.redfrogec.credigo.ui.viewModel.ClientsViewModel
import credigo.composeapp.generated.resources.Res
import credigo.composeapp.generated.resources.ic_plus
import credigo.composeapp.generated.resources.ic_user
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ClientsScreen(navController: NavController, modifier: Modifier = Modifier) {
    val viewModel = koinViewModel<ClientsViewModel>()
    viewModel.navigation = navController
    val showConfirmDelete by viewModel.showConfirmDelete.collectAsState()
    val selectedIdClient by viewModel.selectedIdClient.collectAsState()
    val clientDeleteOk by viewModel.clientDeleteOk.collectAsState()


    val query = viewModel.searchQuery.collectAsState().value

    if(showConfirmDelete)
    {
        val dialogResponse = confirmDialog("Confirmación", "Estas seguro(a) de eliminar este cliente?")
        if(dialogResponse == "OK")
        {
            viewModel.deleteClient(selectedIdClient)
        }
        else if(dialogResponse == "Cancel")
        {
            viewModel.updateClientList()
        }
    }

    if(clientDeleteOk)
    {
        if(simpleDialog("Alerta", "Cliente borrado correctamente"))
        {
            viewModel.loadClients()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(56.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Clientes",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(2f).wrapContentWidth(Alignment.CenterHorizontally)
                )
                IconButton(
                    onClick = { viewModel.onAddClick()},
                    modifier = Modifier.weight(1f).wrapContentWidth(Alignment.End)
                ) {
                    Icon(
                        modifier = modifier
                            .size(30.dp)
                            .padding(0.dp),
                        painter = painterResource(Res.drawable.ic_plus),
                        contentDescription = "Agregar",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // Caja de búsqueda
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFEAF5EF), shape = CircleShape)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                BasicTextField(
                    value = query,
                    onValueChange = { viewModel.onSearchQueryChange(it) },
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    keyboardOptions = KeyboardOptions.Default,
                    keyboardActions = KeyboardActions.Default,
                    decorationBox = { innerTextField ->
                        if (query.isEmpty()) {
                            Text("Buscar clientes", color = Color.Gray)
                        }
                        innerTextField()
                    }
                )
            }

            ClientList(viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientList(viewModel: ClientsViewModel) {
    val clients by viewModel.clients.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        items(clients!!, key = { it.id }) { client ->
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = { value ->
                    when (value) {
                        SwipeToDismissBoxValue.StartToEnd -> { // Izquierda → Eliminar
                            viewModel.onConfirmDeleteChanged(true, client.id)
                            true
                        }

                        SwipeToDismissBoxValue.EndToStart -> { // Derecha → Modificar
                            viewModel.updateClient(client.id)
                            true
                        }

                        else -> false
                    }
                }
            )

            SwipeToDismissBox(
                state = dismissState,
                backgroundContent = {
                    val color = when (dismissState.dismissDirection) {
                        SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.secondaryContainer // Verde
                        SwipeToDismissBoxValue.StartToEnd -> MaterialTheme.colorScheme.error // Rojo
                        else -> Color.Transparent
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color)
                            .padding(horizontal = 16.dp),
                        contentAlignment = when (dismissState.dismissDirection) {
                            SwipeToDismissBoxValue.EndToStart -> Alignment.CenterEnd
                            SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
                            else -> Alignment.Center
                        }
                    ) {
                        Text(
                            text = when (dismissState.dismissDirection) {
                                SwipeToDismissBoxValue.EndToStart -> "Modificar"
                                SwipeToDismissBoxValue.StartToEnd -> "Eliminar"
                                else -> ""
                            },
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                },
                content = {
                    ClientItemRow(client)
                }
            )
        }
    }
}