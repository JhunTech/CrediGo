package com.redfrogec.credigo.ui.screen.tabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import com.redfrogec.credigo.data.model.Client
import com.redfrogec.credigo.domain.controls.LoadingPopup
import com.redfrogec.credigo.domain.controls.dateTimeDialog
import com.redfrogec.credigo.domain.controls.simpleDialog
import com.redfrogec.credigo.ui.viewModel.NewClientViewModel
import credigo.composeapp.generated.resources.Res
import credigo.composeapp.generated.resources.ic_arrow_left
import credigo.composeapp.generated.resources.ic_date_range
import credigo.composeapp.generated.resources.ic_plus
import credigo.composeapp.generated.resources.ic_user
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewClientScreen(navController: NavController, modifier: Modifier = Modifier) {

    val viewModel = koinViewModel<NewClientViewModel>()
    viewModel.navigation = navController

    val clientId by viewModel.clientId.collectAsState()
    val userId by viewModel.userId.collectAsState()
    val image by viewModel.image.collectAsState()
    val identification by viewModel.identification.collectAsState()
    val name by viewModel.name.collectAsState()
    val email by viewModel.email.collectAsState()
    val phone by viewModel.phone.collectAsState()
    val address by viewModel.address.collectAsState()
    val blocked by viewModel.blocked.collectAsState()
    val registerDate by viewModel.registerDate.collectAsState()
    val newClientEnabled by viewModel.newClientEnabled.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val clientOk by viewModel.clientOk.collectAsState()
    val clientFail by viewModel.clientFail.collectAsState()
    val clientMessage by viewModel.clientMessage.collectAsState()
    val showLoading by viewModel.showLoading.collectAsState()

    val scrollState = rememberScrollState()
    var showDatePicker by remember { mutableStateOf(false) }
    if(clientOk)
    {
        if(simpleDialog("Alerta", clientMessage))
        {
            viewModel.onBackClicked()
        }
    }

    if(clientFail) {
        simpleDialog("Error", clientMessage)
    }

    if(showDatePicker)
    {
        val newDate = dateTimeDialog()
        if(newDate.isNotBlank())
            viewModel.onRegisterChanged(newDate)
    }

    LoadingPopup(showLoading)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Título
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = modifier
                        .size(25.dp)
                        .padding(0.dp)
                        .clickable{ viewModel.onBackClicked() },
                    painter = painterResource(Res.drawable.ic_arrow_left),
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary
                )

                // Espaciador flexible antes del título
                Spacer(modifier = Modifier.weight(1f))

                // Título centrado
                Text(
                    text = if (clientId < 0) "Nuevo cliente" else "Actualizar cliente",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleSmall
                )

                // Espaciador flexible después del título (mantiene el centro visual)
                Spacer(modifier = Modifier.weight(1f))
            }
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF0EAE2)),
                    contentAlignment = Alignment.Center
                ) {
                    // Imagen de perfil
                    Image(
                        painter = painterResource(Res.drawable.ic_user), // Imagen local
                        contentDescription = "Profile Picture",
                        modifier = modifier
                            .clip(CircleShape)
                            .fillMaxSize(0.7f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = if (clientId < 0) "ID: New" else "ID: $clientId",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = identification,
                    onValueChange = { viewModel.onIdentificationChanged(it) },
                    placeholder = { Text("Identificación") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { viewModel.onNameChanged(it) },
                    placeholder = { Text("Nombre y Apellido") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { viewModel.onEmailChanged(it) },
                    placeholder = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = phone,
                    onValueChange = { viewModel.onPhoneChanged(it) },
                    placeholder = { Text("Teléfono") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = address,
                    onValueChange = { viewModel.onAddressChanged(it) },
                    placeholder = { Text("Dirección") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Cliente bloqueado?",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f) // Ocupa el espacio restante a la izquierda
                    )

                    Switch(
                        checked = blocked,
                        onCheckedChange = { viewModel.onBlockedChanged(it) }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = registerDate,
                    onValueChange = { viewModel.onRegisterChanged(it) },
                    placeholder = { Text("Fecha de Registro") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = !showDatePicker }) {
                            Icon(
                                painter = painterResource(Res.drawable.ic_date_range),
                                contentDescription = "Select date"
                            )
                        }
                    }
                )

                Spacer(modifier = modifier.height(24.dp))

                Button(
                    onClick = { viewModel.onNewClientClicked() },
                    enabled = newClientEnabled,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008954))
                ) {
                    Text(
                        text = if (clientId < 0) "Registrar" else "Actualizar",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }

                // Error message
                errorMessage?.let { error ->
                    Spacer(modifier = modifier.height(12.dp))
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}