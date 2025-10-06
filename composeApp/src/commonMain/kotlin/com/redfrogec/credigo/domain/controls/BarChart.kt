package com.redfrogec.credigo.domain.controls

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color

@Composable
fun BarChart(
    data: List<Float>,
    labels: List<String>,
    modifier: Modifier = Modifier,
    barColor: Color = MaterialTheme.colorScheme.secondary // Azul Material
) {
    val maxValue = (data.maxOrNull() ?: 0f).coerceAtLeast(1f)

    Column(modifier = modifier.padding(16.dp)) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            val barWidth = size.width / (data.size * 2)
            val chartHeight = size.height

            data.forEachIndexed { index, value ->
                val barHeight = (value / maxValue) * chartHeight
                drawRect(
                    color = barColor,
                    topLeft = Offset(
                        x = index * (barWidth * 2) + barWidth / 2,
                        y = chartHeight - barHeight
                    ),
                    size = Size(barWidth, barHeight)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Etiquetas debajo
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            labels.forEach { label ->
                Text(text = label, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
