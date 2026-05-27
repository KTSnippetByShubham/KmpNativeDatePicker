package com.shubh.kmpnativedatepicker.remember

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.shubh.kmpnativedatepicker.core.KmpDatePicker
import com.shubh.kmpnativedatepicker.picker.DatePickerFactory

@Composable
actual fun rememberDatePicker(): KmpDatePicker {
    val context = LocalContext.current

    return remember {
        DatePickerFactory(context)
            .createDatePicker()
    }
}