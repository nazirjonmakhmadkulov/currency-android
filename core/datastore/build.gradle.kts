plugins {
    alias(libs.plugins.currency.android.library)
}

android {
    namespace = "com.developer.datastore"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
}