package com.shubh.kmpnativedatepicker.remember

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.shubh.kmpnativedatepicker.core.KmpTimePicker
import com.shubh.kmpnativedatepicker.picker.TimePickerFactory

@Composable
actual fun rememberTimePicker(): KmpTimePicker {

    return remember {

        TimePickerFactory()
            .createTimePicker()
    }
}
