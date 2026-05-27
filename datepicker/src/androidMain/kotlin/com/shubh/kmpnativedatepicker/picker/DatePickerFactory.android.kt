package com.shubh.kmpnativedatepicker.picker

import android.content.Context
import com.shubh.kmpnativedatepicker.core.KmpDatePicker

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DatePickerFactory( private val context: Context) {
    actual fun createDatePicker(): KmpDatePicker {
        return AndroidDatePicker(context)
    }
}