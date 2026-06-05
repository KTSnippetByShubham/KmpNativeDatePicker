# 📅 KmpNativeDatePicker

[![Maven Central](https://img.shields.io/maven-central/v/io.github.ktsnippetbyshubham/kmp-native-datepicker?style=for-the-badge)](https://central.sonatype.com/artifact/io.github.ktsnippetbyshubham/kmp-native-datepicker)
[![Kotlin](https://img.shields.io/badge/kotlin-2.1.0-blue.svg?logo=kotlin&style=for-the-badge)](https://kotlinlang.org)
[![Platform](https://img.shields.io/badge/platform-android%20%7C%20ios-lightgrey.svg?style=for-the-badge)](#)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?style=for-the-badge)](https://opensource.org/licenses/Apache-2.0)

A Kotlin Multiplatform library that provides a **truly native** Date and Time Picker experience for both Android and iOS. This library uses platform-specific components that automatically inherit your application's theme and branding.

---

## ✨ Features

*   🎯 **100% Native UI**: Uses Google's `MaterialDatePicker`/`MaterialTimePicker` on Android and Apple's `UIDatePicker` on iOS.
*   🎨 **Zero-Config Theming**: Automatically adopts the host app's colors (e.g., if your app is Orange, the picker turns Orange).
*   🚀 **Coroutines Powered**: Simple `suspend` functions that return selected values or `null` if cancelled.
*   🏗 **Compose Multiplatform Ready**: Includes `rememberDatePicker()` and `rememberTimePicker()` helpers for seamless integration.
*   📅 **Range & Time Support**: Built-in support for selecting date ranges and specific times with a native experience.

---

## 📺 Demo

 Android | iOS |
 :---: | :---: |
 ![Android Demo](assets/android.gif) | ![iOS Demo](assets/iOS.gif) |

---

## 📦 Installation

Add the dependency to your `commonMain` source set in your `build.gradle.kts`:

```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("io.github.ktsnippetbyshubham:kmp-native-datepicker:1.0.1")
        }
    }
}
```

---

## 🚀 Usage

### 1️⃣ In Compose Multiplatform (Recommended)

#### 📅 Date Picker
```kotlin
val datePicker = rememberDatePicker()
val scope = rememberCoroutineScope()

Button(onClick = {
    scope.launch {
        val selectedMillis = datePicker.pickDate(
            title = "Select Birthday"
        )
        // returns Long? (null if cancelled)
    }
}) {
    Text("Open Date Picker")
}
```

#### 🕒 Time Picker
```kotlin
val timePicker = rememberTimePicker()

Button(onClick = {
    scope.launch {
        val time = timePicker.pickTime(
            title = "Select Time",
            is24Hour = true
        )
        // returns Time? { hour, minute }
    }
}) {
    Text("Open Time Picker")
}
```

#### 🗓 Date Range Picker
```kotlin
Button(onClick = {
    scope.launch {
        val range = datePicker.pickDateRange(
            title = "Select Vacation Dates"
        )
        // returns DateRange? { startDateMillis, endDateMillis }
    }
}) {
    Text("Open Range Picker")
}
```

### 2️⃣ Manual Initialization

If you are not using Compose:

- **Android**: 
    - `val datePicker = DatePickerFactory(context).createDatePicker()`
    - `val timePicker = TimePickerFactory(context).createTimePicker()`
- **iOS**: 
    - `val datePicker = DatePickerFactory().createDatePicker()`
    - `val timePicker = TimePickerFactory().createTimePicker()`

---

## 🎨 Theming & Customization

The library is designed to be **"Brand-Aware"**. You don't need to pass color codes manually.

### 🤖 Android
The pickers follow your `MaterialTheme`. To change the color, simply update your `colorPrimary` in your app's theme:
```xml
<item name="colorPrimary">#FF5722</item> <!-- Your brand color -->
```

### 🍎 iOS
The pickers automatically use the **System Global Tint**. If you have set a custom tint color for your app's window, the picker buttons and selection highlights will match it automatically.

---

## 📖 API Reference

### `pickDate`
 Parameter | Type | Description |
 :--- | :--- | :--- |
 `initialDateMillis` | `Long?` | Initial date to show (Default: Now) |
 `minDateMillis` | `Long?` | Minimum selectable date |
 `maxDateMillis` | `Long?` | Maximum selectable date |
 `title` | `String?` | Custom title for the dialog |

### `pickTime`
 Parameter | Type | Description |
 :--- | :--- | :--- |
 `initialHour` | `Int?` | Initial hour (0-23) |
 `initialMinute` | `Int?` | Initial minute (0-59) |
 `is24Hour` | `Boolean` | Whether to use 24-hour format |
 `title` | `String?` | Custom title for the dialog |

---

## 🛠 Platform Details

- **Android**: Uses `MaterialDatePicker` and `MaterialTimePicker` from Google's Material Components.
- **iOS**: Uses `UIDatePicker` inside a `UIAlertController`.
    - **Date**: Uses `UIDatePickerStyleWheels`.
    - **Time**: Uses `UIDatePickerStyleWheels`.
    - **Range**: Uses a custom `UIAlertController` with a segmented control and `UIDatePickerStyleInline` for a smooth sequential selection.

---

## 📄 License
Apache License 2.0. See [LICENSE](LICENSE) for details.
