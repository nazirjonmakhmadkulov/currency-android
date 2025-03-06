plugins {
    alias(libs.plugins.currency.android.library)
    alias(libs.plugins.currency.hilt)
}

android {
    namespace = "com.developer.designsystem"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    viewBinding.enable = true
}

dependencies {
    api(projects.core.common)
    api(projects.core.designsystem)

    implementation(libs.androidx.fragment.ktx)
    implementation(libs.material)

    // Shimmer
    implementation(libs.shimmer)
}