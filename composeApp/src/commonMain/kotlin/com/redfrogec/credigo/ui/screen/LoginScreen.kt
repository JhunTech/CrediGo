package com.redfrogec.credigo.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.redfrogec.credigo.domain.controls.LoadingPopup
import com.redfrogec.credigo.domain.controls.SendAppToBackground
import com.redfrogec.credigo.domain.controls.simpleDialog
import com.redfrogec.credigo.ui.viewModel.LoginViewModel
import com.redfrogec.credigo.ui.viewModel.NotificationViewModel
import credigo.composeapp.generated.resources.Res
import credigo.composeapp.generated.resources.ic_credigologo
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(navController: NavController, modifier: Modifier) {
    val viewModel = koinViewModel<LoginViewModel>()
    val viewModelNotification = koinViewModel<NotificationViewModel>()
    val username by viewModel.username.collectAsState()
    val password by viewModel.password.collectAsState()
    val loginEnabled by viewModel.loginEnabled.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val loginFail by viewModel.loginFail.collectAsState()
    val showLoading by viewModel.showLoading.collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()

    viewModel.navigation = navController

    LaunchedEffect(Unit) {
        viewModelNotification.onEnableNotifications()
    }

    SendAppToBackground()

    if(loginFail) {
        if(simpleDialog("Alerta", "Usuario o contraseña incorrectos")){
            viewModel.clearControls()
        }
    }

    LoadingPopup(showLoading)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_credigologo),
                contentDescription = "CrediGo",
                modifier = modifier
                    .width(300.dp)
                    .height(80.dp)
            )

            Spacer(modifier = modifier.height(40.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { viewModel.onUsernameChanged(it) },
                placeholder = { Text("Email") },
                modifier = modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                singleLine = true
            )

            Spacer(modifier = modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { viewModel.onPasswordChanged(it) },
                placeholder = { Text("Contraseña") },
                modifier = modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = modifier.height(20.dp))

            Button(
                onClick = {
                    keyboardController?.hide()
                    viewModel.onLoginClicked()
                },
                enabled = loginEnabled,
                modifier = modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008954))
            ) {
                Text("Login", style = MaterialTheme.typography.labelLarge, color = Color.White)
            }

            Spacer(modifier = modifier.height(20.dp))

            Text(
                text = "Perdiste tu contraseña?",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.labelSmall,
                modifier = modifier.clickable {
                    keyboardController?.hide()
                    viewModel.onForgotPasswordClicked()
                }
            )

            Spacer(modifier = modifier.height(30.dp))

            OutlinedButton(
                onClick = {
                    keyboardController?.hide()
                    viewModel.onSignUpClicked()
                },
                modifier = modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Registrate", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary)
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
