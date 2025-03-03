plugins {
    alias(libs.plugins.currency.android.library)
    alias(libs.plugins.currency.hilt)
}

android {
    namespace = "com.developer.domain"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    api(projects.core.common)
    api(projects.core.datastore)

    implementation(libs.kotlinx.coroutines.core)
}