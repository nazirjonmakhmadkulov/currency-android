pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        maven("https://jitpack.io")
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")

include(":core:common")
include(":core:data")
include(":core:domain")
include(":core:database")
include(":core:datastore")
include(":core:network")
include(":core:designsystem")
include(":core:navigation")
include(":core:notification")

include(":feature:home")
include(":feature:favorite")
include(":feature:converter")
include(":feature:currencies")
include(":feature:chart")
include(":feature:setting")
include(":sync")
