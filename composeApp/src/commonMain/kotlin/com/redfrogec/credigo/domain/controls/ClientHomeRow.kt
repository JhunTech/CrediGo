package com.redfrogec.credigo.domain.controls

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.redfrogec.credigo.data.model.TopClient
import credigo.composeapp.generated.resources.Res
import credigo.composeapp.generated.resources.ic_client_3
import org.jetbrains.compose.resources.painterResource

@Composable
fun ClientHomeRow(client: TopClient, id: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar placeholder
        Surface(
            modifier = Modifier.size(48.dp),
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.secondaryContainer
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_client_3),
                contentDescription = "Client Image",
                modifier = Modifier.fillMaxSize().padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text("Client ${id}: ${client.clientName}", style = MaterialTheme.typography.bodyLarge)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "P: ${client.totalLoans}",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f).wrapContentWidth(Alignment.Start)
                )
                Text(
                    text = "C: ${client.totalCharges}",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f).wrapContentWidth(Alignment.Start)
                )
            }
        }
    }
}