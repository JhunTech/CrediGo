package com.redfrogec.credigo.ui.screen.tabs

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.redfrogec.credigo.ui.viewModel.LoginViewModel
import com.redfrogec.credigo.ui.viewModel.ProfileViewModel
import credigo.composeapp.generated.resources.Res
import credigo.composeapp.generated.resources.ic_contact
import credigo.composeapp.generated.resources.ic_help
import credigo.composeapp.generated.resources.ic_notifications
import credigo.composeapp.generated.resources.ic_security
import credigo.composeapp.generated.resources.ic_settings
import credigo.composeapp.generated.resources.ic_user
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun ProfileScreen(navController: NavController, modifier: Modifier = Modifier) {

    val viewModel = viewModel { ProfileViewModel(navController) }
    val state by viewModel.uiState
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
            Text(
                text = "Profile",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Imagen de perfil
            Image(
                painter = painterResource(Res.drawable.ic_user), // Imagen local
                contentDescription = "Profile Picture", modifier = Modifier.size(100.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(12.dp))
            // Nombre
            Text(
                text = state.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            // Miembro desde
            Text(
                text = state.memberSince,
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Sección Account
            SectionTitle("Account")
            ProfileItem("Personal Information", Res.drawable.ic_user)
            ProfileItem("Settings", Res.drawable.ic_settings)
            ProfileItem("Notifications", Res.drawable.ic_notifications)
            ProfileItem("Security", Res.drawable.ic_security)

            Spacer(modifier = Modifier.height(24.dp))

            // Sección Support
            SectionTitle("Support")
            ProfileItem("Help Center", Res.drawable.ic_help)
            ProfileItem("Contact Us", Res.drawable.ic_contact)
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

@Composable fun ProfileItem(title: String, iconRes: DrawableResource) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically )
    {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, fontSize = 16.sp) }
}
