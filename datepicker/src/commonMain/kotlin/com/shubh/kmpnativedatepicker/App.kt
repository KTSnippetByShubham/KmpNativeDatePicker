package com.shubh.kmpnativedatepicker

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shubh.kmpnativedatepicker.core.DateRange
import com.shubh.kmpnativedatepicker.remember.rememberDatePicker
import com.shubh.kmpnativedatepicker.remember.rememberTimePicker
import kotlinx.coroutines.launch
@Composable
@Preview
fun App() {
    MaterialTheme {

        val scope = rememberCoroutineScope()

        val datePicker = rememberDatePicker()

        val selectedDate = remember { mutableStateOf<Long?>(null) }

        val selectedDateRange = remember { mutableStateOf<DateRange?>(null) }

        val timePicker = rememberTimePicker()

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = {
                scope.launch {
                    selectedDate.value = datePicker.pickDate()
                }

            }) {
                Text("Single Date picker")
            }

            if (selectedDate.value != null)
                Text("Selected Date :${selectedDate.value}")

            Button(onClick = {
                scope.launch {
                    selectedDateRange.value = datePicker.pickDateRange()
                }
            }) {
                Text("Date Range Picker")
            }

            selectedDateRange.value?.let {
                Text("Selected Date Range : ${it.startDateMillis} - ${it.endDateMillis}")
            }

            Button(onClick = {
                scope.launch {
                    val time = timePicker.pickTime()
                    println(time)
                }
            }) {
                Text("Time Picker")
            }

            selectedDateRange.value?.let {
                Text("Selected Date Range : ${it.startDateMillis} - ${it.endDateMillis}")
            }

        }
    }


}