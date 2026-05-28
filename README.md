# KmpNativeDatePicker

[![Maven Central](https://img.shields.io/maven-central/v/io.github.ktsnippetbyshubham/kmp-native-datepicker)](https://central.sonatype.com/artifact/io.github.ktsnippetbyshubham/kmp-native-datepicker)
[![Kotlin](https://img.shields.io/badge/kotlin-2.1.0-blue.svg?logo=kotlin)](https://kotlinlang.org)
[![Platform](https://img.shields.io/badge/platform-android%20%7C%20ios-lightgrey.svg?style=flat)](#)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

A Kotlin Multiplatform library that provides a native Date Picker experience for both Android and iOS. This library allows you to trigger the platform's native date picker dialogs using a simple, coroutine-based API.

## Features

- 📱 **Native Experience**: Uses `DatePickerDialog` on Android and `UIDatePicker` (via `UIViewController`) on iOS.
- 🔄 **Coroutines Support**: Simple `suspend` function that returns the selected date in milliseconds.
- 🎨 **Compose Multiplatform Ready**: Includes a `rememberDatePicker()` helper for easy integration with Compose.
- 🛠 **Customizable**: Set initial, minimum, and maximum dates.

## Installation

Add the dependency to your `commonMain` source set in your `build.gradle.kts`:

```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("io.github.ktsnippetbyshubham:kmp-native-datepicker:1.0.0")
        }
    }
}
```

## Usage

### 1. Simple Usage in Compose Multiplatform

The easiest way to use the library is with the `rememberDatePicker()` composable function.

```kotlin
import com.shubh.kmpnativedatepicker.remember.rememberDatePicker

@Composable
fun MyScreen() {
    val datePicker = rememberDatePicker()
    val scope = rememberCoroutineScope()
    var selectedDate by remember { mutableStateOf("No date selected") }

    Button(onClick = {
        scope.launch {
            val result = datePicker.pickDate(
                initialDateMillis = System.currentTimeMillis(),
                minDateMillis = null,
                maxDateMillis = null
            )
            result?.let {
                selectedDate = "Selected: $it"
            }
        }
    }) {
        Text(selectedDate)
    }
}
```

### 2. Manual Instantiation (Non-Compose or Custom)

If you need to create the `DatePickerFactory` manually:

**Android:**
```kotlin
val datePicker = DatePickerFactory(context).createDatePicker()
```

**iOS:**
```kotlin
val datePicker = DatePickerFactory().createDatePicker()
```

### API Reference

#### `KmpDatePicker.pickDate`

```kotlin
suspend fun pickDate(
    initialDateMillis: Long? = null,
    minDateMillis: Long? = null,
    maxDateMillis: Long? = null
): Long?
```

- `initialDateMillis`: The date to show when the picker opens.
- `minDateMillis`: The minimum date allowed to be selected.
- `maxDateMillis`: The maximum date allowed to be selected.
- **Returns**: `Long?` representing the selected date in milliseconds since epoch, or `null` if the user cancelled the dialog.

## Platform Implementation Details

- **Android**: Uses `android.app.DatePickerDialog`.
- **iOS**: Uses `UIDatePicker` (Wheel style) inside a `UIAlertController`.

## Contributing

Contributions are welcome! If you find a bug or have a feature request, please open an issue or submit a pull request.

## License

```text
Copyright 2026 Shubham Gupta

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
