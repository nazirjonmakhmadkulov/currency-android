plugins {
    alias(libs.plugins.currency.android.library)
    alias(libs.plugins.currency.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "com.developer.network"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    api(projects.core.common)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.simplexml)

    testImplementation(libs.kotlinx.coroutines.test)
}