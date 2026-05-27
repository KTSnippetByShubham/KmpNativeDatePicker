package com.shubh.kmpnativedatepicker.picker

import com.shubh.kmpnativedatepicker.core.KmpDatePicker
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSDate
import platform.Foundation.dateWithTimeIntervalSince1970
import platform.Foundation.timeIntervalSince1970
import platform.UIKit.UIAlertAction
import platform.UIKit.UIAlertActionStyleCancel
import platform.UIKit.UIAlertActionStyleDefault
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleActionSheet
import platform.UIKit.UIApplication
import platform.UIKit.UIDatePicker
import platform.UIKit.UIDatePickerMode
import platform.UIKit.UIDatePickerStyle
import kotlin.coroutines.resume

@OptIn(ExperimentalForeignApi::class)
class IOSDatePicker : KmpDatePicker {

    override suspend fun pickDate(
        initialDateMillis: Long?,
        minDateMillis: Long?,
        maxDateMillis: Long?
    ): Long? {

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
                    title = "Select Date",
                    message = "\n\n\n\n\n\n\n\n\n",
                    preferredStyle =
                        UIAlertControllerStyleActionSheet
                )

            val datePicker = UIDatePicker(
                frame = CGRectMake(
                    0.0,
                    20.0,
                    270.0,
                    180.0
                )
            )

            // IMPORTANT
            // Native iOS wheel spinner style

            datePicker.preferredDatePickerStyle =
                UIDatePickerStyle.UIDatePickerStyleWheels

            datePicker.datePickerMode =
                UIDatePickerMode.UIDatePickerModeDate

            initialDateMillis?.let {

                datePicker.date =
                    NSDate.dateWithTimeIntervalSince1970(
                        it / 1000.0
                    )
            }

            minDateMillis?.let {

                datePicker.minimumDate =
                    NSDate.dateWithTimeIntervalSince1970(
                        it / 1000.0
                    )
            }

            maxDateMillis?.let {

                datePicker.maximumDate =
                    NSDate.dateWithTimeIntervalSince1970(
                        it / 1000.0
                    )
            }

            alertController.view.addSubview(datePicker)

            val doneAction =
                UIAlertAction.actionWithTitle(
                    title = "Done",
                    style = UIAlertActionStyleDefault
                ) {

                    val selectedDate =
                        (datePicker.date.timeIntervalSince1970 * 1000)
                            .toLong()

                    continuation.resume(selectedDate)
                }

            val cancelAction =
                UIAlertAction.actionWithTitle(
                    title = "Cancel",
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