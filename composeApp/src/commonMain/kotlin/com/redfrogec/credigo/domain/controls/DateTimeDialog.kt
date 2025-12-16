package com.redfrogec.credigo.domain.controls

import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.Calendar
import androidx.compose.ui.unit.dp
import com.redfrogec.credigo.domain.utils.currentDateDisplay
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant


// Simple function to convert timestamp to readable format
fun formatDate(timestamp: Long?): String {
    if (timestamp == null) return "No date selected"

    // Calculate days since epoch (Jan 1, 1970)
    val daysSinceEpoch = timestamp / (1000 * 60 * 60 * 24)

    // Calculate year, month and day
    var remainingDays = daysSinceEpoch
    var year = 1970

    // Account for leap years
    while (true) {
        val daysInYear = if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) 366 else 365
        if (remainingDays < daysInYear) break
        remainingDays -= daysInYear
        year++
    }

    // Determine month and day
    val daysInMonth = arrayOf(31, if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) 29 else 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
    var month = 0

    while (month < 12) {
        if (remainingDays < daysInMonth[month]) break
        remainingDays -= daysInMonth[month]
        month++
    }

    val day = remainingDays.toInt() + 1
    month += 1  // Adjust month to be 1-based

    // Month names
    val monthNames = arrayOf("January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December")

    return "${monthNames[month-1]} $day, $year"
}

@OptIn(ExperimentalTime::class)
fun convertMillisToLocalDate(millis: Long): LocalDateTime {
    val instant = Instant.fromEpochMilliseconds(millis)
    return instant.toLocalDateTime(TimeZone.UTC)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun dateTimeDialog(): String {
    var showDialog by remember { mutableStateOf(true) }
    var returnDialog by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<Long?>(0) }

    val calendar = currentDateDisplay()
    val minDateMillis = LocalDate(calendar.year - 1, calendar.month, calendar.day)
        .atStartOfDayIn(TimeZone.currentSystemDefault())
        .toEpochMilliseconds()

    val maxDateMillis = LocalDate(calendar.year, calendar.month, calendar.day)
        .atStartOfDayIn(TimeZone.currentSystemDefault())
        .toEpochMilliseconds()

    selectedDate = maxDateMillis

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis in minDateMillis..maxDateMillis
            }
        }
    )

    // Show the DatePickerDialog when showDialog is true
    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        selectedDate =  datePickerState.selectedDateMillis
                        returnDialog = convertMillisToLocalDate(selectedDate!!).toString()
                        showDialog = false
                    }
                ) {
                    Text("OK", color = MaterialTheme.colorScheme.primary)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text("Cancel", color = MaterialTheme.colorScheme.primary)
                }
            },
        ) {
            DatePicker(
                state = datePickerState,
                // Make DatePicker smaller
                modifier = Modifier.sizeIn(maxWidth = 350.dp)
            )
        }
    }

    return returnDialog
}

