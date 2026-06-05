package com.shubh.kmpnativedatepicker.picker

import com.shubh.kmpnativedatepicker.core.KmpTimePicker

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class TimePickerFactory {
    actual fun createTimePicker(): KmpTimePicker {
        return IOSTimePicker()
    }
}
