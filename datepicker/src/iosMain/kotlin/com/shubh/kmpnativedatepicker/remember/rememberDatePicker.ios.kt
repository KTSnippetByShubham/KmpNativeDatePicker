package com.shubh.kmpnativedatepicker.remember

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.shubh.kmpnativedatepicker.core.KmpDatePicker
import com.shubh.kmpnativedatepicker.picker.DatePickerFactory


@Composable
actual fun rememberDatePicker(): KmpDatePicker {

    return remember {

        DatePickerFactory()
            .createDatePicker()
    }
}