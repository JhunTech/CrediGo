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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.redfrogec.credigo.domain.controls.OfficialIdField
import com.redfrogec.credigo.domain.controls.PasswordField
import com.redfrogec.credigo.domain.controls.ProfileField
import com.redfrogec.credigo.domain.controls.ProfileHeader
import com.redfrogec.credigo.ui.viewModel.LoginViewModel
import com.redfrogec.credigo.ui.viewModel.NewClientViewModel
import com.redfrogec.credigo.ui.viewModel.ProfileViewModel
import credigo.composeapp.generated.resources.Res
import credigo.composeapp.generated.resources.ic_arrow_left
import credigo.composeapp.generated.resources.ic_close
import credigo.composeapp.generated.resources.ic_contact
import credigo.composeapp.generated.resources.ic_help
import credigo.composeapp.generated.resources.ic_notifications
import credigo.composeapp.generated.resources.ic_security
import credigo.composeapp.generated.resources.ic_settings
import credigo.composeapp.generated.resources.ic_user
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileScreen(onLogout: () -> Unit, modifier: Modifier = Modifier) {

    val viewModel = koinViewModel<ProfileViewModel>()
    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
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
                    style = MaterialTheme.typography.titleSmall,
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

            PasswordField(
                onChangeClick = viewModel::onChangePassword
            )

            ProfileField("PREGUNTA DE SEGURIDAD", state.securityQuestion)
            ProfileField("TELÉFONO MÓVIL", state.phone)

            OfficialIdField(
                officialId = state.officialId,
                verified = state.isVerified
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { onLogout() },
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
                onClick = { },
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

@Composable fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}

@Composable fun ProfileItem(
    title: String,
    iconRes: DrawableResource,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, fontSize = 16.sp)
    }
}
