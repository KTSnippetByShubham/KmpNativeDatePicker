import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("com.vanniktech.maven.publish") version "0.31.0"
    id("signing")
}

compose.resources {
    packageOfResClass = "com.shubh.kmpnativedatepicker.datepicker"
}

group = "io.github.ktsnippetbyshubham"
version = "1.0.2"

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
            implementation(libs.googleMaterial)
            implementation(libs.compose.uiToolingPreview)
        }

        iosMain.dependencies {
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

mavenPublishing {

    publishToMavenCentral(
        com.vanniktech.maven.publish.SonatypeHost.CENTRAL_PORTAL
    )

    signAllPublications()

    coordinates(
        "io.github.ktsnippetbyshubham",
        "kmp-native-datepicker",
        "1.0.2"
    )

    pom {

        name.set("KMP Native DatePicker")

        description.set(
            "Native Android and iOS Date Picker for Kotlin Multiplatform"
        )

        inceptionYear.set("2026")

        url.set(
            "https://github.com/KTSnippetByShubham/KmpNativeDatePicker"
        )

        licenses {

            license {

                name.set("Apache License 2.0")

                url.set(
                    "https://www.apache.org/licenses/LICENSE-2.0.txt"
                )
            }
        }

        developers {

            developer {

                id.set("KTSnippetByShubham")

                name.set("Shubham Gupta")

                email.set(
                    "Shubhamgupta7536@gmail.com"
                )
            }
        }

        scm {

            url.set(
                "https://github.com/KTSnippetByShubham/KmpNativeDatePicker"
            )

            connection.set(
                "scm:git:git://github.com/KTSnippetByShubham/KmpNativeDatePicker.git"
            )

            developerConnection.set(
                "scm:git:ssh://git@github.com/KTSnippetByShubham/KmpNativeDatePicker.git"
            )
        }
    }
}

signing {

    sign(
        publishing.publications
    )
}