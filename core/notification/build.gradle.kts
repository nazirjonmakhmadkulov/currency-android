plugins {
    alias(libs.plugins.currency.android.library)
    alias(libs.plugins.currency.hilt)
}

android {
    namespace = "com.developer.notification"
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
}