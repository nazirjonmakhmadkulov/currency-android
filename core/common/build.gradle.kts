plugins {
    alias(libs.plugins.currency.android.library)
    alias(libs.plugins.currency.hilt)
}

android {
    namespace = "com.developer.common"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.kotlinx.coroutines.test)
}