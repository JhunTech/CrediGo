package com.redfrogec.credigo.domain.controls

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import credigo.composeapp.generated.resources.Res
import credigo.composeapp.generated.resources.ic_badge
import org.jetbrains.compose.resources.painterResource

@Composable
fun OfficialIdField(
    officialId: String,
    verified: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Text("IDENTIFICACIÓN OFICIAL", fontSize = 12.sp, color = Color.Gray)

        Spacer(Modifier.height(6.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(Res.drawable.ic_badge),
                    contentDescription = null,
                    tint = Color(0xFF00C853)
                )
                Spacer(Modifier.width(8.dp))
                Text(officialId)
            }

            if (verified) {
                Surface(
                    shape = RoundedCornerShape(50),
                    color = Color(0xFFE8F5E9)
                ) {
                    Text(
                        "VERIFICADO",
                        modifier = Modifier.padding(
                            horizontal = 12.dp,
                            vertical = 6.dp
                        ),
                        color = Color(0xFF00C853),
                        fontSize = 12.sp
                    )
                }
            }
        }

        Divider(Modifier.padding(top = 12.dp))
    }
}