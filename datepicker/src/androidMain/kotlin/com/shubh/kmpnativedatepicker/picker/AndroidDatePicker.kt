package com.shubh.kmpnativedatepicker.picker

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.shubh.kmpnativedatepicker.core.DateRange
import com.shubh.kmpnativedatepicker.core.KmpDatePicker
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Calendar
import kotlin.coroutines.resume

class AndroidDatePicker(
    private val context: Context
) : KmpDatePicker {

    private fun findActivity(context: Context): AppCompatActivity? {
        var currentContext = context
        while (currentContext is ContextWrapper) {
            if (currentContext is AppCompatActivity) {
                return currentContext
            }
            currentContext = currentContext.baseContext
        }
        return null
    }

    override suspend fun pickDate(
        initialDateMillis: Long?,
        minDateMillis: Long?,
        maxDateMillis: Long?,
        title: String?,
        doneButtonText: String?,
        cancelButtonText: String?
    ): Long? {
        val activity = findActivity(context) ?: return null
        val fragmentManager = activity.supportFragmentManager

        return suspendCancellableCoroutine { continuation ->
            val builder = MaterialDatePicker.Builder.datePicker()

            title?.let { builder.setTitleText(it) }
            doneButtonText?.let { builder.setPositiveButtonText(it) }
            cancelButtonText?.let { builder.setNegativeButtonText(it) }

            val constraintsBuilder = CalendarConstraints.Builder()
            minDateMillis?.let { constraintsBuilder.setStart(it) }
            maxDateMillis?.let { constraintsBuilder.setEnd(it) }

            builder.setCalendarConstraints(constraintsBuilder.build())
            builder.setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)

            initialDateMillis?.let {
                builder.setSelection(it)
            }

            val picker = builder.build()

            picker.addOnPositiveButtonClickListener { selection ->
                continuation.resume(selection)
            }

            picker.addOnCancelListener {
                continuation.resume(null)
            }

            picker.addOnNegativeButtonClickListener {
                continuation.resume(null)
            }

            picker.show(fragmentManager, "DATE_PICKER")
        }
    }

    override suspend fun pickDateRange(
        initialStartDateMillis: Long?,
        initialEndDateMillis: Long?,
        minDateMillis: Long?,
        maxDateMillis: Long?,
        title: String?,
        doneButtonText: String?,
        cancelButtonText: String?
    ): DateRange? {
        val activity = findActivity(context) ?: return null
        val fragmentManager = activity.supportFragmentManager

        return suspendCancellableCoroutine { continuation ->
            val builder = MaterialDatePicker.Builder.dateRangePicker()

            title?.let { builder.setTitleText(it) }
            doneButtonText?.let { builder.setPositiveButtonText(it) }
            cancelButtonText?.let { builder.setNegativeButtonText(it) }

            val constraintsBuilder = CalendarConstraints.Builder()
            minDateMillis?.let { constraintsBuilder.setStart(it) }
            maxDateMillis?.let { constraintsBuilder.setEnd(it) }

            builder.setCalendarConstraints(constraintsBuilder.build())
            builder.setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)

            if (initialStartDateMillis != null && initialEndDateMillis != null) {
                builder.setSelection(
                    androidx.core.util.Pair(
                        initialStartDateMillis,
                        initialEndDateMillis
                    )
                )
            }

            val picker = builder.build()

            picker.addOnPositiveButtonClickListener { selection ->
                val start = selection?.first ?: 0L
                val end = selection?.second ?: 0L
                continuation.resume(DateRange(start, end))
            }

            picker.addOnCancelListener {
                continuation.resume(null)
            }

            picker.addOnNegativeButtonClickListener {
                continuation.resume(null)
            }

            picker.show(fragmentManager, "DATE_RANGE_PICKER")
        }
    }
}