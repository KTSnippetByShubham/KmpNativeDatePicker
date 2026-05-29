package com.shubh.kmpnativedatepicker.picker

import com.shubh.kmpnativedatepicker.core.DateRange
import com.shubh.kmpnativedatepicker.core.KmpDatePicker
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCAction
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSDate
import platform.Foundation.NSSelectorFromString
import platform.Foundation.dateWithTimeIntervalSince1970
import platform.Foundation.setValue
import platform.Foundation.timeIntervalSince1970
import platform.Foundation.timeIntervalSinceDate
import platform.UIKit.UIAlertAction
import platform.UIKit.UIAlertActionStyleCancel
import platform.UIKit.UIAlertActionStyleDefault
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UIApplication
import platform.UIKit.UIControlEventValueChanged
import platform.UIKit.UIDatePicker
import platform.UIKit.UIDatePickerMode
import platform.UIKit.UIDatePickerStyle
import platform.UIKit.UISegmentedControl
import platform.UIKit.UIView
import platform.UIKit.UIViewController
import platform.darwin.NSObject
import kotlin.coroutines.resume

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
class IOSDatePicker : KmpDatePicker {

    private suspend fun showPicker(
        initialDateMillis: Long?,
        minDateMillis: Long?,
        maxDateMillis: Long?,
        title: String?,
        doneButtonText: String?,
        cancelButtonText: String?,
        iosPickerStyle: UIDatePickerStyle
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
                    title = title ?: "Select Date",
                    message = null,
                    preferredStyle = UIAlertControllerStyleAlert
                )

            val datePicker = UIDatePicker()
            
            datePicker.preferredDatePickerStyle = iosPickerStyle
            datePicker.datePickerMode = UIDatePickerMode.UIDatePickerModeDate

            initialDateMillis?.let {
                datePicker.date = NSDate.dateWithTimeIntervalSince1970(it / 1000.0)
            }

            minDateMillis?.let {
                datePicker.minimumDate = NSDate.dateWithTimeIntervalSince1970(it / 1000.0)
            }

            maxDateMillis?.let {
                datePicker.maximumDate = NSDate.dateWithTimeIntervalSince1970(it / 1000.0)
            }

            val contentViewController = UIViewController()
            val pickerWidth = if (iosPickerStyle == UIDatePickerStyle.UIDatePickerStyleInline) 280.0 else 270.0
            val pickerHeight = if (iosPickerStyle == UIDatePickerStyle.UIDatePickerStyleInline) 320.0 else 180.0
            
            datePicker.setFrame(CGRectMake(0.0, 0.0, pickerWidth, pickerHeight))
            contentViewController.view.addSubview(datePicker)
            
            contentViewController.setPreferredContentSize(
                platform.CoreGraphics.CGSizeMake(pickerWidth, pickerHeight)
            )
            
            alertController.setValue(value = contentViewController, forKey = "contentViewController")

            val doneAction = UIAlertAction.actionWithTitle(
                title = doneButtonText ?: "Done",
                style = UIAlertActionStyleDefault
            ) {
                val selectedDate = (datePicker.date.timeIntervalSince1970 * 1000).toLong()
                continuation.resume(selectedDate)
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

    override suspend fun pickDate(
        initialDateMillis: Long?,
        minDateMillis: Long?,
        maxDateMillis: Long?,
        title: String?,
        doneButtonText: String?,
        cancelButtonText: String?
    ): Long? {
        return showPicker(
            initialDateMillis,
            minDateMillis,
            maxDateMillis,
            title,
            doneButtonText,
            cancelButtonText,
            UIDatePickerStyle.UIDatePickerStyleWheels
        )
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
                    title = title ?: "Select Range",
                    message = null,
                    preferredStyle = UIAlertControllerStyleAlert
                )

            val containerView = UIView()
            val pickerWidth = 280.0
            val segmentHeight = 40.0
            val pickerHeight = 320.0
            val totalHeight = segmentHeight + pickerHeight + 10.0
            
            containerView.setFrame(CGRectMake(0.0, 0.0, pickerWidth, totalHeight))

            val segmentedControl = UISegmentedControl(items = listOf("Start Date", "End Date"))
            segmentedControl.setFrame(CGRectMake(10.0, 0.0, pickerWidth - 20.0, 30.0))
            segmentedControl.selectedSegmentIndex = 0
            
            val datePicker = UIDatePicker()
            datePicker.preferredDatePickerStyle = UIDatePickerStyle.UIDatePickerStyleInline
            datePicker.datePickerMode = UIDatePickerMode.UIDatePickerModeDate
            datePicker.setFrame(CGRectMake(0.0, segmentHeight, pickerWidth, pickerHeight))

            val nowMillis = (NSDate().timeIntervalSince1970 * 1000).toLong()
            var startDate = NSDate.dateWithTimeIntervalSince1970((initialStartDateMillis ?: nowMillis) / 1000.0)
            var endDate = NSDate.dateWithTimeIntervalSince1970((initialEndDateMillis ?: ((initialStartDateMillis ?: nowMillis) + 86400000)) / 1000.0)
            
            val minDate = minDateMillis?.let { NSDate.dateWithTimeIntervalSince1970(it / 1000.0) }
            val maxDate = maxDateMillis?.let { NSDate.dateWithTimeIntervalSince1970(it / 1000.0) }

            datePicker.date = startDate
            datePicker.minimumDate = minDate
            datePicker.maximumDate = maxDate

            val coordinator = object : NSObject() {
                @ObjCAction
                fun valueChanged() {
                    if (segmentedControl.selectedSegmentIndex.toInt() == 0) {
                        startDate = datePicker.date
                        if (endDate.timeIntervalSinceDate(startDate) < 0.0) {
                            endDate = startDate
                        }
                    } else {
                        endDate = datePicker.date
                    }
                }

                @ObjCAction
                fun segmentChanged() {
                    if (segmentedControl.selectedSegmentIndex.toInt() == 0) {
                        datePicker.date = startDate
                        datePicker.minimumDate = minDate
                    } else {
                        datePicker.date = endDate
                        datePicker.minimumDate = startDate
                    }
                }
            }

            datePicker.addTarget(coordinator, NSSelectorFromString("valueChanged"), UIControlEventValueChanged)
            segmentedControl.addTarget(coordinator, NSSelectorFromString("segmentChanged"), UIControlEventValueChanged)

            containerView.addSubview(segmentedControl)
            containerView.addSubview(datePicker)

            val contentViewController = UIViewController()
            contentViewController.view.addSubview(containerView)
            contentViewController.setPreferredContentSize(platform.CoreGraphics.CGSizeMake(pickerWidth, totalHeight))
            
            alertController.setValue(value = contentViewController, forKey = "contentViewController")

            val doneAction = UIAlertAction.actionWithTitle(
                title = doneButtonText ?: "Done",
                style = UIAlertActionStyleDefault
            ) {
                val finalStart = (startDate.timeIntervalSince1970 * 1000).toLong()
                val finalEnd = (endDate.timeIntervalSince1970 * 1000).toLong()
                continuation.resume(DateRange(finalStart, finalEnd))
                coordinator.hashCode() // Keep reference to prevent GC
            }

            val cancelAction = UIAlertAction.actionWithTitle(
                title = cancelButtonText ?: "Cancel",
                style = UIAlertActionStyleCancel
            ) {
                continuation.resume(null)
                coordinator.hashCode() // Keep reference to prevent GC
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
