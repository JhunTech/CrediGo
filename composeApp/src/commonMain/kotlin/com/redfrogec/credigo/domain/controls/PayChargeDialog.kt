package com.redfrogec.credigo.domain.controls

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.redfrogec.credigo.data.model.Charge
import com.redfrogec.credigo.ui.viewModel.PayChargeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PayChargeDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    charge: Charge
) {

    val viewModel = koinViewModel<PayChargeViewModel>()
    viewModel.loadState(charge)
    val state by viewModel.uiState.collectAsState()
    val confirmPayment by viewModel.confirmPayment.collectAsState()

    if (confirmPayment) {
        onConfirm()
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 420.dp)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Title
                Text(
                    text = "Pagar Cuota",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "PREST #${state.loanNumber}",
                    color = Color(0xFF6A8F6A),
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(Modifier.height(24.dp))

                // Summary
                SummaryRow("Cuota Total", state.totalDue)
                SummaryRow("Valor Pagado", state.alreadyPaid)
                SummaryRow(
                    label = "Pendiente",
                    value = state.pending,
                    bold = true
                )

                Divider(Modifier.padding(vertical = 16.dp))

                // Payment Amount
                Text(
                    "Monto a Cobrar",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(Modifier.height(8.dp))

                PaymentAmountField(
                    value = state.paymentAmount.toString(),
                    onValueChange = viewModel::onPaymentAmountChange
                )

                Spacer(Modifier.height(24.dp))

                // Confirm Button
                Button(
                    onClick = viewModel::confirmPayment,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        "Confirmar Cobro",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.height(16.dp))

                TextButton(onClick = onDismiss) {
                    Text(
                        "Cancelar",
                        color = Color(0xFF6A8F6A),
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}
