package com.shubh.kmpnativedatepicker.core

data class Time(val hour: Int, val minute: Int)

interface KmpTimePicker {
    suspend fun pickTime(
        initialHour: Int? = null,
        initialMinute: Int? = null,
        is24Hour: Boolean = false,
        title: String? = null,
        doneButtonText: String? = null,
        cancelButtonText: String? = null
    ): Time?
}
