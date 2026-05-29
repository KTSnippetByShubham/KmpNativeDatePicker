package com.shubh.kmpnativedatepicker.core

data class DateRange(val startDateMillis: Long, val endDateMillis: Long)

interface KmpDatePicker {

    suspend fun pickDate(
        initialDateMillis: Long? = null,
        minDateMillis: Long? = null,
        maxDateMillis: Long? = null,
        title: String? = null,
        doneButtonText: String? = null,
        cancelButtonText: String? = null
    ): Long?

    suspend fun pickDateRange(
        initialStartDateMillis: Long? = null,
        initialEndDateMillis: Long? = null,
        minDateMillis: Long? = null,
        maxDateMillis: Long? = null,
        title: String? = null,
        doneButtonText: String? = null,
        cancelButtonText: String? = null
    ): DateRange?
}
