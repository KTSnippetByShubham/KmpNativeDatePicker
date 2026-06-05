package com.shubh.kmpnativedatepicker.picker

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.shubh.kmpnativedatepicker.core.KmpTimePicker
import com.shubh.kmpnativedatepicker.core.Time
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class AndroidTimePicker(
    private val context: Context
) : KmpTimePicker {

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

    override suspend fun pickTime(
        initialHour: Int?,
        initialMinute: Int?,
        is24Hour: Boolean,
        title: String?,
        doneButtonText: String?,
        cancelButtonText: String?
    ): Time? {
        val activity = findActivity(context) ?: return null
        val fragmentManager = activity.supportFragmentManager

        return suspendCancellableCoroutine { continuation ->
            val builder = MaterialTimePicker.Builder()
                .setTimeFormat(if (is24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H)

            initialHour?.let { builder.setHour(it) }
            initialMinute?.let { builder.setMinute(it) }
            title?.let { builder.setTitleText(it) }

            val picker = builder.build()

            picker.addOnPositiveButtonClickListener {
                continuation.resume(Time(picker.hour, picker.minute))
            }

            picker.addOnCancelListener {
                continuation.resume(null)
            }

            picker.addOnNegativeButtonClickListener {
                continuation.resume(null)
            }

            picker.show(fragmentManager, "TIME_PICKER")
        }
    }
}
