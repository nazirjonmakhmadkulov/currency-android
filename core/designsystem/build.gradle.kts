plugins {
    alias(libs.plugins.currency.android.library)
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

    implementation(libs.androidx.fragment.ktx)
    implementation(libs.material)
    implementation(libs.viewbinding.noreflection)
    implementation(libs.kotlinx.coroutines.android)

    // Shimmer
    implementation(libs.shimmer)
}