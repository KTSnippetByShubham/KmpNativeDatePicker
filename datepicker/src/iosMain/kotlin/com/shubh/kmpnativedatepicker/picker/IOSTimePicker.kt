package com.shubh.kmpnativedatepicker.picker

import com.shubh.kmpnativedatepicker.core.KmpTimePicker
import com.shubh.kmpnativedatepicker.core.Time
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarUnitHour
import platform.Foundation.NSCalendarUnitMinute
import platform.Foundation.NSDate
import platform.Foundation.setValue
import platform.UIKit.UIAlertAction
import platform.UIKit.UIAlertActionStyleCancel
import platform.UIKit.UIAlertActionStyleDefault
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UIApplication
import platform.UIKit.UIDatePicker
import platform.UIKit.UIDatePickerMode
import platform.UIKit.UIDatePickerStyle
import platform.UIKit.UIViewController
import kotlin.coroutines.resume

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
class IOSTimePicker : KmpTimePicker {

    override suspend fun pickTime(
        initialHour: Int?,
        initialMinute: Int?,
        is24Hour: Boolean,
        title: String?,
        doneButtonText: String?,
        cancelButtonText: String?
    ): Time? {
        return suspendCancellableCoroutine { continuation ->
            val rootController =
                UIApplication.sharedApplication
                    .keyWindow
                    ?.rootViewController

            if (rootController == null) {
                continuation.resume(null)
                return@suspendCancellableCoroutine
            }

            val alertController =
                UIAlertController.alertControllerWithTitle(
                    title = title ?: "Select Time",
                    message = null,
                    preferredStyle = UIAlertControllerStyleAlert
                )

            val timePicker = UIDatePicker()
            timePicker.datePickerMode = UIDatePickerMode.UIDatePickerModeTime
            timePicker.preferredDatePickerStyle = UIDatePickerStyle.UIDatePickerStyleWheels

            val calendar = NSCalendar.currentCalendar
            val now = NSDate()
            val components = calendar.components(NSCalendarUnitHour or NSCalendarUnitMinute, fromDate = now)
            
            initialHour?.let { components.setHour(it.toLong()) }
            initialMinute?.let { components.setMinute(it.toLong()) }
            
            calendar.dateFromComponents(components)?.let {
                timePicker.date = it
            }

            val contentViewController = UIViewController()
            val pickerWidth = 270.0
            val pickerHeight = 180.0
            
            timePicker.setFrame(CGRectMake(0.0, 0.0, pickerWidth, pickerHeight))
            contentViewController.view.addSubview(timePicker)
            
            contentViewController.setPreferredContentSize(
                platform.CoreGraphics.CGSizeMake(pickerWidth, pickerHeight)
            )
            
            alertController.setValue(value = contentViewController, forKey = "contentViewController")

            val doneAction = UIAlertAction.actionWithTitle(
                title = doneButtonText ?: "Done",
                style = UIAlertActionStyleDefault
            ) {
                val selectedDate = timePicker.date
                val selectedComponents = NSCalendar.currentCalendar.components(
                    NSCalendarUnitHour or NSCalendarUnitMinute,
                    fromDate = selectedDate
                )
                continuation.resume(Time(selectedComponents.hour.toInt(), selectedComponents.minute.toInt()))
            }

            val cancelAction = UIAlertAction.actionWithTitle(
                title = cancelButtonText ?: "Cancel",
                style = UIAlertActionStyleCancel
            ) {
                continuation.resume(null)
            }

            alertController.addAction(doneAction)
            alertController.addAction(cancelAction)

            rootController.presentViewController(
                viewControllerToPresent = alertController,
                animated = true,
                completion = null
            )
        }
    }
}
