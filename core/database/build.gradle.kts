plugins {
    alias(libs.plugins.currency.android.library)
    alias(libs.plugins.currency.android.room)
    alias(libs.plugins.currency.hilt)
}

android {
    namespace = "com.developer.database"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    api(projects.core.common)
}