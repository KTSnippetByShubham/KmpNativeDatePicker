package com.shubh.kmpnativedatepicker.picker

import android.app.DatePickerDialog
import android.content.Context
import com.shubh.kmpnativedatepicker.core.KmpDatePicker
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Calendar
import kotlin.coroutines.resume

class AndroidDatePicker(
    private val context: Context
) : KmpDatePicker {
    override suspend fun pickDate(
        initialDateMillis: Long?,
        minDateMillis: Long?,
        maxDateMillis: Long?
    ): Long? {
        return suspendCancellableCoroutine { continuation ->

            val calendar = Calendar.getInstance()

            initialDateMillis?.let {
                calendar.timeInMillis = it
            }

            val dialog = DatePickerDialog(
                context,
                { _, year, month, day ->

                    val result = Calendar.getInstance().apply {
                        set(year, month, day)
                    }

                    continuation.resume(result.timeInMillis)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            minDateMillis?.let {
                dialog.datePicker.minDate = it
            }

            maxDateMillis?.let {
                dialog.datePicker.maxDate = it
            }

            dialog.setOnCancelListener {
                continuation.resume(null)
            }

            dialog.show()
        }
    }
}