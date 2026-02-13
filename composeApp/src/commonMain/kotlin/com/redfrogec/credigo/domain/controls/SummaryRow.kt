package com.redfrogec.credigo.domain.controls

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.redfrogec.credigo.domain.utils.roundBigDecimalToTwoDecimals

@Composable
fun SummaryRow(
    label: String,
    value: Double,
    bold: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = if (bold) Color.Black else Color(0xFF6A8F6A),
            fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal
        )

        Text(
            text = "$${roundBigDecimalToTwoDecimals(value)}",
            fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal
        )
    }

    Spacer(Modifier.height(12.dp))
}
