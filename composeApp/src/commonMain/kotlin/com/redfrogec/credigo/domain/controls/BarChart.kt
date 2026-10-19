package com.redfrogec.credigo.domain.controls

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Parámetros de la gráfica
private val BAR_COLOR = Color(0xFF4CAF50)
private val AXIS_COLOR = Color.Gray
private const val PADDING_BOTTOM = 60f // Espacio para las etiquetas de meses

data class MonthData(
    var month: String,
    var value: Float
)

@Composable
fun BarChart(
    data: List<MonthData>,
    modifier: Modifier = Modifier
) {
    if (data.isEmpty()) return

    val textMeasurer = rememberTextMeasurer()
    val maxValue = data.maxOfOrNull { it.value } ?: 0f
    val numBars = data.size

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        // 1. Calcular dimensiones
        val drawingHeight = height - PADDING_BOTTOM
        val spacePerBar = width / numBars.toFloat()
        val barWidthRatio = 0.6f
        val barWidth = spacePerBar * barWidthRatio

        // 2. Dibujar el Eje X
        drawLine(
            color = AXIS_COLOR,
            start = Offset(x = 0f, y = drawingHeight),
            end = Offset(x = width, y = drawingHeight),
            strokeWidth = 3f
        )

        // 3. Dibujar las Barras, Etiquetas y Valores
        data.forEachIndexed { index, item ->
            val value = item.value

            // Posición X central de la barra
            val centerX = spacePerBar * (index + 0.5f)

            // Altura de la barra (escalada)
            val barHeight = (value / maxValue) * drawingHeight

            // Coordenadas de la barra
            val topOfBar = drawingHeight - barHeight
            val left = centerX - (barWidth / 2)

            // Dibujar la barra
            drawRect(
                color = BAR_COLOR,
                topLeft = Offset(left, topOfBar),
                size = Size(barWidth, barHeight)
            )

            // --- INICIO: NUEVO CÓDIGO PARA MOSTRAR VALORES ---

            // Texto del valor a mostrar (puedes formatearlo como prefieras)
            val valueText = value.toInt().toString()
            val valueTextLayout = textMeasurer.measure(
                text = valueText,
                style = TextStyle(fontSize = 12.sp, color = Color.Black, textAlign = TextAlign.Center)
            )

            // Dibujar el texto del valor encima de la barra
            drawText(
                textLayoutResult = valueTextLayout,
                topLeft = Offset(
                    x = centerX - valueTextLayout.size.width / 2, // Centrar horizontalmente
                    y = topOfBar - valueTextLayout.size.height - 5f // Posicionar encima de la barra con un margen de 5f
                )
            )

            // Dibujar la etiqueta del mes debajo de la barra
            val monthText = item.month
            val textLayoutResult = textMeasurer.measure(
                text = monthText,
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            )

            drawText(
                textLayoutResult = textLayoutResult,
                // Centrar el texto en la posición X y colocarlo justo debajo del eje
                topLeft = Offset(
                    x = centerX - textLayoutResult.size.width / 2,
                    y = drawingHeight + 5f // 5f es un pequeño margen
                )
            )
        }
    }
}