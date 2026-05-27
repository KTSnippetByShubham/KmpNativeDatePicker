package com.shubh.kmpnativedatepicker.core

interface KmpDatePicker {

    suspend fun pickDate(
        initialDateMillis: Long? = null,
        minDateMillis: Long? = null,
        maxDateMillis: Long? = null
    ): Long?
}