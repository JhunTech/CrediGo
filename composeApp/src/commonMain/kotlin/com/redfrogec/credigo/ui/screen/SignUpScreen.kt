package com.redfrogec.credigo.ui.screen

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.redfrogec.credigo.domain.controls.LoadingPopup
import com.redfrogec.credigo.domain.controls.simpleDialog
import com.redfrogec.credigo.ui.viewModel.SignUpViewModel
import credigo.composeapp.generated.resources.Res
import credigo.composeapp.generated.resources.ic_arrow_left
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SignUpScreen(navController: NavController, modifier: Modifier) {
    val viewModel = koinViewModel<SignUpViewModel>()
    viewModel.navigation = navController

    val email by viewModel.email.collectAsState()
    val name by viewModel.name.collectAsState()
    val password by viewModel.password.collectAsState()
    val repeatPassword by viewModel.repeatPassword.collectAsState()
    val securityQuestion by viewModel.securityQuestion.collectAsState()
    val answer by viewModel.answer.collectAsState()
    val signUpEnabled by viewModel.signUpEnabled.collectAsState()
    val signUpOk by viewModel.signUpOk.collectAsState()
    val signUpFail by viewModel.signUpFail.collectAsState()
    val showLoading by viewModel.showLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val questions = viewModel.getQuestions()
    var expanded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    if(signUpOk)
    {
        if(simpleDialog("Alerta", "Registro exitoso"))
        {
            viewModel.goDashBoard()
        }
    }

    if(signUpFail) {
        simpleDialog("Error", "No se pudo registrar el usuario, revisa los datos ingresados.")
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
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(top = 5.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = modifier
                        .size(30.dp)
                        .padding(0.dp)
                        .clickable{ viewModel.onSignInClicked()},
                    painter = painterResource(Res.drawable.ic_arrow_left),
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Crea tu usuario",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(20.dp))

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
                    value = name,
                    onValueChange = { viewModel.onNameChanged(it) },
                    placeholder = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { viewModel.onPasswordChanged(it) },
                    placeholder = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = repeatPassword,
                    onValueChange = { viewModel.onRepeatPasswordChanged(it) },
                    placeholder = { Text("Repetir Password") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(12.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = securityQuestion,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text("Selecciona la pregunta de seguridad") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        }
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        questions.forEach { question ->
                            DropdownMenuItem(
                                text = { Text(question.description) },
                                onClick = {
                                    viewModel.onSecurityQuestionChanged(
                                        question.id,
                                        question.description
                                    )
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = answer,
                    onValueChange = { viewModel.onAnswerChanged(it) },
                    placeholder = { Text("Respuesta de seguridad") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { viewModel.onSignUpClicked() },
                    enabled = signUpEnabled,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008954))
                ) {
                    Text("Registrar", fontSize = 18.sp, color = Color.White)
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Ya tienes una cuenta? ir a Login",
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.clickable { viewModel.onSignInClicked() }
                )

                // Error message
                errorMessage?.let { error ->
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}