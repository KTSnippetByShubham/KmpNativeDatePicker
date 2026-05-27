rootProject.name = "KmpNativeDatePicker"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {

    repositories {

        google {

            mavenContent {

                includeGroupByRegex("androidx.*")
                includeGroupByRegex("com.android.*")
                includeGroupByRegex("com.google.*")
            }
        }

        mavenCentral()

        gradlePluginPortal()
    }
}

dependencyResolutionManagement {

    repositoriesMode.set(
        RepositoriesMode.FAIL_ON_PROJECT_REPOS
    )

    repositories {

        google {

            mavenContent {

                includeGroupByRegex("androidx.*")
                includeGroupByRegex("com.android.*")
                includeGroupByRegex("com.google.*")
            }
        }

        mavenCentral()
    }
}

include(":androidApp")

include(":datepicker")