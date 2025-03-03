plugins {
    alias(libs.plugins.currency.android.feature)
    alias(libs.plugins.currency.android.library.compose)
}

android {
    namespace = "com.developer.setting"
    viewBinding.enable = true
}

dependencies {
    implementation(projects.sync)
    implementation(libs.androidx.preference.ktx)
    // Jetpack WorkManager
    ksp(libs.hilt.ext.compiler)
    implementation(libs.androidx.work.ktx)
    implementation(libs.hilt.ext.work)
    implementation(libs.koin.androidx.workmanager)
}