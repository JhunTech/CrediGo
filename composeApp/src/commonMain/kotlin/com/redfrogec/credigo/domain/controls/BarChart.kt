package com.redfrogec.credigo.domain.controls

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Bar(val value: Float, val color: Color, val label: String)

@Composable
fun BarChart(
    bars: List<Bar>, modifier: Modifier) {
    val maxValue = bars.maxOfOrNull { it.value } ?: 0f

    Column(modifier = modifier, verticalArrangement = Arrangement.Bottom) {
        Canvas(modifier = Modifier.weight(1f).fillMaxWidth()) {
            val barWidth = size.width / bars.size

            bars.forEachIndexed { index, bar ->
                val barHeight = (bar.value / maxValue) * size.height
                drawRect(
                    color = bar.color,
                    topLeft = Offset(x = index * barWidth + barWidth * 0.1f, y = size.height - barHeight),
                    size = Size(barWidth * 0.8f, barHeight)
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            bars.forEach { bar ->
                Text(
                    text = bar.label,
                    fontSize = 12.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(30.dp)
                )
            }
        }
    }
}