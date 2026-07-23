package com.redfrogec.credigo.domain.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import credigo.composeapp.generated.resources.Res
import credigo.composeapp.generated.resources.ic_camera
import credigo.composeapp.generated.resources.ic_user
import org.jetbrains.compose.resources.painterResource

@Composable
fun ProfileHeader(
    name: String,
    clientId: String,
    onPhotoClick: () -> Unit
) {
    Box(contentAlignment = Alignment.BottomEnd) {

        Box(
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
                .background(Color(0xFFE8F5E9)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_user),
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = Color.Gray
            )
        }

        FloatingActionButton(
            onClick = onPhotoClick,
            modifier = Modifier.size(40.dp),
            containerColor = Color(0xFF00E676)
        ) {
            Icon(painter = painterResource(Res.drawable.ic_camera), contentDescription = null)
        }
    }

    Spacer(Modifier.height(16.dp))

    Text(
        name,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )

    Text(
        "ID Usuario: $clientId",
        color = Color.Gray
    )
}
