plugins {
    alias(libs.plugins.currency.android.library)
    alias(libs.plugins.currency.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "com.developer.data"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    api(projects.core.common)
    api(projects.core.database)
    api(projects.core.network)
    api(projects.core.domain)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlinx.serialization.json)
}