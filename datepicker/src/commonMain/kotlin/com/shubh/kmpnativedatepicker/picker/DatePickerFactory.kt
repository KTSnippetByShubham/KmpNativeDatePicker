package com.shubh.kmpnativedatepicker.picker

import com.shubh.kmpnativedatepicker.core.KmpDatePicker

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class DatePickerFactory {

    fun createDatePicker(): KmpDatePicker
}