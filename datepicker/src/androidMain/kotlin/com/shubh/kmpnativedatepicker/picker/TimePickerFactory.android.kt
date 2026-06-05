package com.shubh.kmpnativedatepicker.picker

import android.content.Context
import com.shubh.kmpnativedatepicker.core.KmpTimePicker

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class TimePickerFactory(private val context: Context) {
    actual fun createTimePicker(): KmpTimePicker {
        return AndroidTimePicker(context)
    }
}
