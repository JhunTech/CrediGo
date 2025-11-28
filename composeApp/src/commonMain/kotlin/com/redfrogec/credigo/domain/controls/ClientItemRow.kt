package com.redfrogec.credigo.domain.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.redfrogec.credigo.data.model.Client
import com.redfrogec.credigo.data.model.ClientStatus
import credigo.composeapp.generated.resources.Res
import credigo.composeapp.generated.resources.ic_user
import org.jetbrains.compose.resources.painterResource

@Composable
fun ClientItemRow(client: Client){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background)
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