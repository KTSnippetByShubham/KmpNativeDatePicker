import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("maven-publish")
}

compose.resources {
    packageOfResClass = "com.shubh.kmpnativedatepicker.datepicker"
}

group = "com.shubh"
version = "1.0.0"

val xcf = XCFramework()

kotlin {

    androidLibrary {
        namespace = "com.shubh.kmpnativedatepicker.datepicker"

        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
        
        // Removed broken publishing block from here. 
        // Kotlin Multiplatform plugin handles publishing of targets.
    }

    iosArm64().binaries.framework {
        baseName = "datepicker"
        isStatic = true
        xcf.add(this)
    }

    iosSimulatorArm64().binaries.framework {
        baseName = "datepicker"
        isStatic = true
        xcf.add(this)
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
        }

        androidMain.dependencies {
            implementation(libs.androidx.appcompat)
            implementation(libs.compose.uiToolingPreview)
        }

        iosMain.dependencies {
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.shubh"
            artifactId = "kmp-native-datepicker"
            version = "1.0.0"
            
            // To publish the KMP library, you usually reference the generated components.
            // For now, this is a placeholder to fix the syntax error.
        }
    }
}
