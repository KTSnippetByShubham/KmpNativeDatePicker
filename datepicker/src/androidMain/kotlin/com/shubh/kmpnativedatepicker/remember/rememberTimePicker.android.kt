package com.shubh.kmpnativedatepicker.remember

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.shubh.kmpnativedatepicker.core.KmpTimePicker
import com.shubh.kmpnativedatepicker.picker.TimePickerFactory

@Composable
actual fun rememberTimePicker(): KmpTimePicker {
    val context = LocalContext.current

    return remember {
        TimePickerFactory(context)
            .createTimePicker()
    }
}
