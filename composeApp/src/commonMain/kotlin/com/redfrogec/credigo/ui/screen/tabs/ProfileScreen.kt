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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.redfrogec.credigo.data.model.Constants
import com.redfrogec.credigo.domain.controls.OfficialIdField
import com.redfrogec.credigo.domain.controls.PasswordField
import com.redfrogec.credigo.domain.controls.ProfileField
import com.redfrogec.credigo.domain.controls.ProfileHeader
import com.redfrogec.credigo.domain.controls.simpleDialog
import com.redfrogec.credigo.ui.viewModel.ProfileViewModel
import org.koin.compose.viewmodel.koinViewModel
import com.russhwolf.settings.Settings

@Composable
fun ProfileScreen(onLogout: () -> Unit, modifier: Modifier = Modifier) {

    val viewModel = koinViewModel<ProfileViewModel>()
    val state by viewModel.uiState.collectAsState()
    val showConfirmDelete by viewModel.showConfirmDelete.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val userFail by viewModel.userFail.collectAsState()
    val deleteUserOk by viewModel.deleteUserOk.collectAsState()
    val scrollState = rememberScrollState()

    val settings: Settings = Settings()

    if(showConfirmDelete)
    {
        if(simpleDialog("Alerta", "Estas seguro(a) que deseas eliminar tu usuario, no podrás recuperar la data de tus préstamos."))
        {
            viewModel.onDeleteUser()
        }
    }

    if(deleteUserOk)
    {
        if(simpleDialog("Aviso", "Tus datos han sido borrados."))
        {
            onLogout()
        }
    }

    if(userFail) {
        simpleDialog("Error", errorMessage)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp, 0.dp, 16.dp, 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Título
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))
                // Título centrado
                Text(
                    text = "Usuario",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(2f).wrapContentWidth(Alignment.CenterHorizontally)
                )
                // Espaciador flexible después del título (mantiene el centro visual)
                Spacer(modifier = Modifier.weight(1f))
            }


            Spacer(Modifier.height(24.dp))

            ProfileHeader(
                name = state.displayName,
                clientId = state.clientId,
                onPhotoClick = viewModel::onChangePhoto
            )

            Spacer(Modifier.height(32.dp))

            ProfileField("NOMBRE COMPLETO", state.fullName)
            ProfileField("CORREO ELECTRÓNICO", state.email)

            ProfileField("PASSWORD", "••••••••")

            ProfileField(state.securityQuestion, state.responseQuestion)
            ProfileField("TELÉFONO MÓVIL", state.phone)

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    settings.remove(Constants.USER_ID)
                    onLogout()
                    },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "Cerrar sesión",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = { viewModel.showConfirmDelete() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
            ) {
                Text(
                    text = "Borrar usuario",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
        }
    }
}