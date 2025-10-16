package com.redfrogec.credigo.domain.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.redfrogec.credigo.data.model.Loan
import credigo.composeapp.generated.resources.Res
import credigo.composeapp.generated.resources.ic_credit_card
import credigo.composeapp.generated.resources.ic_home
import org.jetbrains.compose.resources.painterResource

@Composable
fun LoanItem(loan: Loan) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding( vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icono tarjeta
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer, shape = MaterialTheme.shapes.medium),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                    painter = painterResource(Res.drawable.ic_credit_card), contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.secondary
                )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(loan.id, fontSize = 16.sp, color = Color.Black)
            Text("${loan.value} · ${loan.creationDate}", fontSize = 14.sp, color = Color(0xFF3BA776))
        }

        Text("Cuotas: ${loan.quotaNumbers}", fontSize = 14.sp, color = Color.Black)
    }
}