package com.redfrogec.credigo.domain.controls

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.redfrogec.credigo.data.model.Charge
import com.redfrogec.credigo.domain.utils.roundBigDecimalToTwoDecimals
import kotlinx.datetime.LocalDate

@Composable
fun ChargeCard(item: Charge, totalQuote: Int = 0) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Cuota ${item.quotaNumber} de $totalQuote",
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    "Fecha: ${LocalDate.parse(item.chargeDate)}",
                    color = Color.Gray,
                    fontSize = 13.sp
                )
            }

            Spacer(Modifier.height(8.dp))

            Text("Valor: $${roundBigDecimalToTwoDecimals(item.quotaValue)}")
            Text("Pagado: $${roundBigDecimalToTwoDecimals(item.chargeValue)}")

            Text(
                "Pendiente: $${roundBigDecimalToTwoDecimals(item.remainingValue)}",
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )
        }
    }
}