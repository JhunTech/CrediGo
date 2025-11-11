package com.redfrogec.credigo.ui.screen.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.redfrogec.credigo.data.model.Client
import com.redfrogec.credigo.data.model.ClientStatus
import com.redfrogec.credigo.ui.viewModel.ClientsViewModel
import com.redfrogec.credigo.ui.viewModel.DashboardViewModel
import credigo.composeapp.generated.resources.Res
import credigo.composeapp.generated.resources.ic_plus
import credigo.composeapp.generated.resources.ic_user
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ClientsScreen(navController: NavController, modifier: Modifier = Modifier) {
    val viewModel = koinViewModel<ClientsViewModel>()
    val dashboardViewModel= viewModel<DashboardViewModel>()
    viewModel.navigation = navController

    val scrollState = rememberScrollState()

    val clients = viewModel.clients.collectAsState().value
    val query = viewModel.searchQuery.collectAsState().value

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
                            Text("Search clients", color = Color.Gray)
                        }
                        innerTextField()
                    }
                )
            }

            Spacer(Modifier.height(16.dp))

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                // Lista de clientes
                clients.forEach { client ->
                    CLientItemRow(client, viewModel)
                }
            }
        }
    }
}

@Composable
fun CLientItemRow(client: Client, viewModel: ClientsViewModel){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color(0xFFF0EAE2)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_user), // Coloca un ícono de usuario por defecto
                contentDescription = null,
                tint = Color.DarkGray,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(Modifier.width(12.dp))

        Column {
            Text(
                text = client.name+" - "+client.identification,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Email: ${client.email}",
                style = MaterialTheme.typography.bodySmall,
            )
            Text(
                text = "Teléfono: ${client.phone}",
                style = MaterialTheme.typography.bodySmall,
            )
            Text(
                text = if (!client.blocked) ClientStatus.Activo.toString() else ClientStatus.Bloqueado.toString(),
                color = if (!client.blocked) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}