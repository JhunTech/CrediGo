package com.redfrogec.credigo.ui.screen.tabs

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.redfrogec.credigo.data.model.Charge
import com.redfrogec.credigo.domain.controls.ChargeCard
import com.redfrogec.credigo.domain.controls.ClientSelectorDialog
import com.redfrogec.credigo.domain.controls.LoadingPopup
import com.redfrogec.credigo.domain.controls.confirmDialog
import com.redfrogec.credigo.domain.controls.dateTimeDialog
import com.redfrogec.credigo.domain.controls.simpleDialog
import com.redfrogec.credigo.domain.utils.isValid2Decimal
import com.redfrogec.credigo.domain.utils.roundBigDecimalToTwoDecimals
import com.redfrogec.credigo.ui.viewModel.ClientSelectorViewModel
import com.redfrogec.credigo.ui.viewModel.NewLoanViewModel
import com.redfrogec.credigo.ui.viewModel.SharedViewModel
import credigo.composeapp.generated.resources.Res
import credigo.composeapp.generated.resources.ic_arrow_left
import credigo.composeapp.generated.resources.ic_date_range
import credigo.composeapp.generated.resources.ic_user
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun NewLoanScreen(navController: NavController, modifier: Modifier) {

    val viewModel = koinViewModel<NewLoanViewModel>()
    val sharedViewModel = koinViewModel<SharedViewModel>()
    viewModel.navigation = navController

    val loanId by viewModel.loanId.collectAsState()
    val interests = viewModel.interestList
    val loanTypes = viewModel.loanTypes
    val paymentTypes = viewModel.paymentTypes
    val quoteNumberDisplay = viewModel.quoteNumbersDisplay
    var expandedInterest by remember { mutableStateOf(false) }
    var expandedLoanType by remember { mutableStateOf(false) }
    var expandedPaymentType by remember { mutableStateOf(false) }
    val quotesDescription by viewModel.quotesDescription.collectAsState()
    val loanType by viewModel.loanType.collectAsState()
    val loanValue by viewModel.loanValue.collectAsState()
    val interestId by viewModel.interestId.collectAsState()
    val paymentTypeDescription by viewModel.paymentTypeDescription.collectAsState()
    val generatePlanEnabled by viewModel.generatePlanEnabled.collectAsState()
    val registerLoanEnabled by viewModel.registerLoanEnabled.collectAsState()
    val plan by viewModel.generatedPlan.collectAsState()
    val showConfirmRegister by viewModel.showConfirmRegister.collectAsState()
    val registerDate by viewModel.registerDate.collectAsState()
    var showDatePicker by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val showLoading by viewModel.showLoading.collectAsState()
    val loanOK by viewModel.loanOk.collectAsState()
    val loanFail by viewModel.loanFail.collectAsState()

    val greenColor = Color(0xFF2ECC71)
    val lightGreen = Color(0xFFE8F9F0)

    if(showConfirmRegister)
    {
        val dialogResponse = confirmDialog("Confirmación", "Estás seguro(a) de registrar este préstamo?.\nRecuerda que una vez generado no puede modificarse los datos, ni tampoco los cobros.")
        if(dialogResponse == "OK" && !loanOK)
        {
            viewModel.onNewRegisterLoan()
        }
        else if(dialogResponse == "Cancel")
        {
            viewModel.onShowConfirmRegister(false)
        }
    }

    if(showDatePicker)
    {
        val newDate = dateTimeDialog()
        if(newDate.isNotBlank())
            viewModel.onRegisterDateChanged(newDate)
    }

    if(loanOK)
    {
        if(simpleDialog("Aviso", errorMessage))
        {
            viewModel.onBackClicked()
        }
    }

    if(loanFail) {
        simpleDialog("Error", errorMessage)
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
                        .clickable { viewModel.onBackClicked() },
                    painter = painterResource(Res.drawable.ic_arrow_left),
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary
                )

                // Espaciador flexible antes del título
                Spacer(modifier = Modifier.weight(1f))

                // Título centrado
                Text(
                    text = if (loanId < 0) "Nuevo préstamo" else "Actualizar préstamo",
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

                // Loan Value
                OutlinedTextField(
                    value = if (loanValue > 0) loanValue.toString() else "0",
                    onValueChange = {
                        it ->
                        if (!it.isEmpty() && isValid2Decimal(it)) {
                            viewModel.onLoanValueChange(it.toDouble())
                        }
                    },
                    placeholder = { Text("Valor del préstamo") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(Modifier.height(15.dp))

                // Client
                OutlinedTextField(
                    value =
                        if(sharedViewModel.clientSelected.value != null)
                            viewModel.onClientSelected(sharedViewModel.clientSelected.value!!.id , sharedViewModel.clientSelected.value!!.name)
                        else viewModel.onClientSelected(0, "")
                    ,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Selecciona un cliente") },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(Res.drawable.ic_user),
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                viewModel.showSelectClient()
                            }
                        )
                    },
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(Modifier.height(20.dp))

                Text("Interest Rate (%)", fontWeight = FontWeight.SemiBold)

                Spacer(Modifier.height(10.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    interests.forEach { selectedInterest ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 6.dp)
                                .background(
                                    if (selectedInterest.id == interestId)
                                        greenColor
                                        else Color(0xFFEFEFEF),
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable { viewModel.onInterestChange(selectedInterest) }
                                .padding(vertical = 14.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = selectedInterest.description,
                                color = if (selectedInterest.id == interestId) Color.White else Color.DarkGray,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))

                ExposedDropdownMenuBox(
                    expanded = expandedLoanType,
                    onExpandedChange = { expandedLoanType = !expandedLoanType }
                ) {
                    OutlinedTextField(
                        value = loanType,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text("T. de Prést..") },

                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedLoanType)
                        }
                    )

                    ExposedDropdownMenu(
                        expanded = expandedLoanType,
                        onDismissRequest = { expandedLoanType = false }
                    ) {
                        loanTypes.forEach { loanType ->
                            DropdownMenuItem(
                                text = { Text(loanType.description) },
                                onClick = {
                                    viewModel.onLoanTypeSelected(
                                        loanType
                                    )
                                    expandedLoanType = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = registerDate,
                    onValueChange = { viewModel.onRegisterDateChanged(it) },
                    placeholder = { Text("Fecha de Inicio") },
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

                Spacer(Modifier.height(20.dp))

                // Loan Type & Installments
                Row(Modifier.fillMaxWidth()) {

                    ExposedDropdownMenuBox(
                        expanded = expandedPaymentType,
                        onExpandedChange = { expandedPaymentType = !expandedPaymentType },
                        modifier = Modifier.weight(1f)
                    ) {
                        OutlinedTextField(
                            value = paymentTypeDescription,
                            onValueChange = {},
                            readOnly = true,
                            placeholder = { Text("T. de cobro") },

                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            shape = RoundedCornerShape(10.dp),
                            singleLine = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPaymentType)
                            }
                        )

                        ExposedDropdownMenu(
                            expanded = expandedPaymentType,
                            onDismissRequest = { expandedPaymentType = false }
                        ) {
                            paymentTypes.forEach { paymentType ->
                                DropdownMenuItem(
                                    text = { Text(paymentType.description) },
                                    onClick = {
                                        viewModel.onPaymentTypeSelected(
                                            paymentType
                                        )
                                        expandedPaymentType = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(Modifier.width(12.dp))

                    ExposedDropdownMenuBox(
                        expanded = expandedInterest,
                        onExpandedChange = { expandedInterest = !expandedInterest },
                        modifier = Modifier.weight(1f)
                    ) {
                        OutlinedTextField(
                            value = quotesDescription,
                            onValueChange = {},
                            readOnly = true,
                            placeholder = { Text("N. Cuota") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            shape = RoundedCornerShape(10.dp),
                            singleLine = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedInterest)
                            }
                        )

                        ExposedDropdownMenu(
                            expanded = expandedInterest,
                            onDismissRequest = { expandedInterest = false }
                        ) {
                            quoteNumberDisplay.forEach { quote ->
                                DropdownMenuItem(
                                    text = { Text(quote.description) },
                                    onClick = {
                                        viewModel.onNumberQuoteSelected(
                                            quote
                                        )
                                        expandedInterest = false
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = { viewModel.generatePlan() },
                    enabled = generatePlanEnabled,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Text(
                        text = "Generar Cuotas",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }

                Spacer(Modifier.height(25.dp))

                if (plan.isNotEmpty()) {
                    Text(
                        "Plan de cuotas",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(15.dp))

                    plan.forEach { item ->
                        ChargeCard(item, plan.size)
                        Spacer(Modifier.height(15.dp))
                    }
                }

                Spacer(Modifier.weight(1f))

                Button(
                    onClick = { viewModel.showConfirmQuestion()},
                    enabled = registerLoanEnabled,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = "Registrar Préstamo",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}



