package com.redfrogec.credigo.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.IconButton
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.redfrogec.credigo.domain.controls.LoadingPopup
import com.redfrogec.credigo.ui.viewModel.SignUpViewModel
import credigo.composeapp.generated.resources.Res
import credigo.composeapp.generated.resources.ic_arrow_left
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SignUpScreen(navController: NavController) {
    val viewModel = viewModel { SignUpViewModel(navController) }
    val email by viewModel.email.collectAsState()
    val name by viewModel.name.collectAsState()
    val password by viewModel.password.collectAsState()
    val repeatPassword by viewModel.repeatPassword.collectAsState()
    val securityQuestion by viewModel.securityQuestion.collectAsState()
    val answer by viewModel.answer.collectAsState()
    val signUpEnabled by viewModel.signUpEnabled.collectAsState()
    val showLoading by viewModel.showLoading.collectAsState()

    val questions = viewModel.getQuestions()
    var expanded by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    //LoadingPopup(showDialog = showLoading)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState),
        contentAlignment = Alignment.TopStart
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                IconButton(
                    onClick = {viewModel.onSignInClicked()},
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_arrow_left),
                        contentDescription = "Atrás",
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
                Text(
                    text = "Crea tu usuario",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

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
                placeholder = { Text("Name") },
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
                placeholder = { Text("Repeat Password") },
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
                    placeholder = { Text("Security Question") },
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
                    questions.forEach {question ->
                        DropdownMenuItem(
                            text = { Text(question.Descripcion) },
                            onClick = {
                                viewModel.onSecurityQuestionChanged(question.Id, question.Descripcion)
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
                placeholder = { Text("Answer") },
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
        }
    }
}